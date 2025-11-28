package com.example.TodoWeb1.controller;


import com.example.TodoWeb1.dto.TaskRequest;
import com.example.TodoWeb1.dto.TaskResponse;
import com.example.TodoWeb1.model.Task;
import com.example.TodoWeb1.model.TaskStatus;
import com.example.TodoWeb1.repository.TaskRepository;
import com.example.TodoWeb1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponse save(@RequestBody TaskRequest task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<TaskResponse> findAll(@RequestParam(value = "status", required = false) TaskStatus status) {
        if(status == null) return taskService.getAllTasks();
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody TaskRequest task) {
        return taskService.updateTask(id, task);
    }

    @PutMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
