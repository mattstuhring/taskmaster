package com.mattstuhring.taskmaster.controllers;

import com.mattstuhring.taskmaster.models.Task;
import com.mattstuhring.taskmaster.repository.TaskmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController {
    @Autowired
    TaskmasterRepository taskmasterRepository;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskmasterRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        Task t = new Task();
        t.setTitle( task.getTitle() );
        t.setDescription( task.getDescription() );
        t.setStatus( "Available" );
        taskmasterRepository.save(t);
        return t;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTask(@PathVariable String id) {
        Task t = taskmasterRepository.findById(id).get();

        if (t.getStatus().equals("Available")) {
            t.setStatus("Assigned");
        } else if (t.getStatus().equals("Assigned")) {
            t.setStatus("Accepted");
        } else if (t.getStatus().equals("Accepted")) {
            t.setStatus("Finished");
        }

        taskmasterRepository.save(t);
        return t;
    }
}


