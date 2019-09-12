package com.mattstuhring.taskmaster.repository;

import com.mattstuhring.taskmaster.models.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface TaskmasterRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
    List<Task> findByAssignee(String name);
}