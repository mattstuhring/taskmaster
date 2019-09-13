package com.mattstuhring.taskmaster.controllers;

import com.mattstuhring.taskmaster.repository.S3Client;
import com.mattstuhring.taskmaster.models.History;
import com.mattstuhring.taskmaster.models.Task;
import com.mattstuhring.taskmaster.repository.TaskmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController {

    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Autowired
    TaskmasterRepository taskmasterRepository;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskmasterRepository.findAll();
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksForUser(@PathVariable String name) {
        return (List) taskmasterRepository.findByAssignee(name);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable String id) {
        Task task = taskmasterRepository.findById(id).get();
        return task;
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        Task newTask = new Task();

        newTask.setTitle( task.getTitle() );
        newTask.setDescription( task.getDescription() );

        if (task.getAssignee() == null) {
            newTask.setStatus("Available");
        } else {
            newTask.setAssignee(task.getAssignee());
            newTask.setStatus("Assigned");
        }

        History newHistory = new History(newTask.getStatus());
        newTask.addHistory(newHistory);

        taskmasterRepository.save(newTask);
        return newTask;
    }

    @PostMapping("/tasks/{id}/images")
    public Task uploadFile(@PathVariable String id, @RequestPart(value = "file") MultipartFile file){
        Task newTask = taskmasterRepository.findById(id).get();
        String image = this.s3Client.uploadFile(file);

        newTask.setImage(image);
        taskmasterRepository.save(newTask);

        return newTask;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTask(@PathVariable String id) {
        Task newTask = taskmasterRepository.findById(id).get();

        if (newTask.getStatus().equals("Available")) {
            newTask.setStatus("Assigned");

            History newHistory = new History("Available --> Assigned");
            newTask.addHistory(newHistory);
        } else if (newTask.getStatus().equals("Assigned")) {
            newTask.setStatus("Accepted");

            History newHistory = new History("Assigned --> Accepted");
            newTask.addHistory(newHistory);
        } else if (newTask.getStatus().equals("Accepted")) {
            newTask.setStatus("Finished");

            History newHistory = new History("Accepted --> Finished");
            newTask.addHistory(newHistory);
        }

        taskmasterRepository.save(newTask);
        return newTask;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task updateTasAssignee(@PathVariable String id, @PathVariable String assignee) {
        Task newTask = taskmasterRepository.findById(id).get();
        newTask.setAssignee(assignee);
        newTask.setStatus("Assigned");

        History newHistory = new History("Assigned to new assignee");
        newTask.addHistory(newHistory);

        taskmasterRepository.save(newTask);
        return newTask;
    }

    @DeleteMapping("/tasks/{id}")
    public Task deleteTaskStatus(@PathVariable String id) {
        Task newTask = taskmasterRepository.findById(id).get();
        taskmasterRepository.delete(newTask);
        return newTask;
    }
}


