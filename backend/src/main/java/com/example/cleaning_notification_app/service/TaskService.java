package com.example.cleaning_notification_app.service;

import java.util.List;

import com.example.cleaning_notification_app.request.TaskRequest;
import com.example.cleaning_notification_app.response.TaskResponse;

public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse createTask(TaskRequest taskRequest);
    TaskResponse updateTask(TaskRequest taskRequest, Long id);
    void deleteTask(Long id);
}
