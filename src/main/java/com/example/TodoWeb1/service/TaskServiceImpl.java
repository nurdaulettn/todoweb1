package com.example.TodoWeb1.service;

import com.example.TodoWeb1.dto.TaskRequest;
import com.example.TodoWeb1.dto.TaskResponse;
import com.example.TodoWeb1.exception.TaskNotFoundException;
import com.example.TodoWeb1.model.Task;
import com.example.TodoWeb1.model.TaskStatus;
import com.example.TodoWeb1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService  {
    private final TaskRepository taskRepository;

    private TaskResponse taskToResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );

    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task newTask = new Task();
        newTask.setTitle(taskRequest.getTitle());
        newTask.setDescription(taskRequest.getDescription());
        newTask.setStatus(TaskStatus.TODO);
        newTask.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(newTask);
        TaskResponse taskResponse = taskToResponse(savedTask);
        return taskResponse;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::taskToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("There is no task with id " + id));
        TaskResponse taskResponse = taskToResponse(task);
        return taskResponse;

    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("There is no task with id " + id));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setUpdatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        return taskToResponse(savedTask);
    }

    @Override
    public TaskResponse updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("There is no task with id " + id));
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        return taskToResponse(savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("There is no task with id " + id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::taskToResponse)
                .collect(Collectors.toList());
    }
}
