package com.example.cleaning_notification_app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.cleaning_notification_app.entity.Task;
import com.example.cleaning_notification_app.repository.TaskRepository;
import com.example.cleaning_notification_app.request.TaskRequest;
import com.example.cleaning_notification_app.response.TaskResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToTaskResponse).toList();
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setPlace(taskRequest.getPlace());
        task.setTarget(taskRequest.getTarget());
        task.setIntervalDays(taskRequest.getIntervalDays());
        task.setMethod(taskRequest.getMethod());

        // とりあえず今日の日付をセットしておく→ロジックを作成した後は置き換える
        task.setNextDueDate(java.time.LocalDate.now());

        Task savedTask = taskRepository.save(task);
        return convertToTaskResponse(savedTask);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(TaskRequest taskRequest, Long id) {
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "掃除タスクが見つかりませんでした"));

        task.setPlace(taskRequest.getPlace());
        task.setTarget(taskRequest.getTarget());
        task.setIntervalDays(taskRequest.getIntervalDays());
        task.setMethod(taskRequest.getMethod());

        // とりあえず今日の日付をセットしておく→ロジックを作成した後は置き換える
        task.setNextDueDate(java.time.LocalDate.now());

        Task savedTask = taskRepository.save(task);
        return convertToTaskResponse(savedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "掃除タスクが見つかりませんでした"));

        taskRepository.delete(task);
    }

    private TaskResponse convertToTaskResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getPlace(),
            task.getTarget(),
            task.getIntervalDays(),
            task.getMethod(),
            task.getNextDueDate()
        );
    }
}
