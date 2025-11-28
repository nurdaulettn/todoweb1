package com.example.TodoWeb1.service;

import com.example.TodoWeb1.dto.TaskRequest;
import com.example.TodoWeb1.dto.TaskResponse;
import com.example.TodoWeb1.model.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(Long id);
    TaskResponse updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
    List<TaskResponse> getTasksByStatus(TaskStatus status);
    TaskResponse updateTaskStatus(Long id, TaskStatus status);
}
