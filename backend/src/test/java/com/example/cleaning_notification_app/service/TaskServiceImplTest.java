package com.example.cleaning_notification_app.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cleaning_notification_app.entity.Task;
import com.example.cleaning_notification_app.repository.TaskRepository;
import com.example.cleaning_notification_app.request.TaskRequest;
import com.example.cleaning_notification_app.response.TaskResponse;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("新しい掃除タスクを正常に保存できること")
    void createTask_Success() {
        // テスト用のリクエストデータを作成
        TaskRequest request = new TaskRequest("お風呂", "浴槽", 7, "洗剤で洗う");

        // リポジトリが保存した後に返してくる「完成したデータ」を準備
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setPlace("お風呂");
        savedTask.setTarget("浴槽");
        savedTask.setIntervalDays(7);
        savedTask.setMethod("洗剤で洗う");
        savedTask.setNextDueDate(LocalDate.now());

        // repository.save()が呼ばれたら、準備したsavedTaskを返せと命令
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // 実際にサービス層のメソッドを呼び出す
        TaskResponse response = taskService.createTask(request);

        // 期待した通りの結果が返ってきているかチェック（アサーション）
        assertAll(
            () -> assertEquals(1L, response.getId()),
            () -> assertEquals("お風呂", response.getPlace()),
            () -> assertEquals("浴槽", response.getTarget()),
            () -> assertEquals(7, response.getIntervalDays()),
            () -> assertEquals("洗剤で洗う", response.getMethod()),
            () -> assertNotNull(response.getNextDueDate())
        );

        // リポジトリのsaveメソッドが本当に1回だけ呼ばれたか確認
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
