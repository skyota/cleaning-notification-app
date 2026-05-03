package com.example.cleaning_notification_app.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("タスク一覧を全権取得できること")
    void getAllTasks_Success() {
        Task task1 = new Task();
        task1.setPlace("場所1");
        task1.setTarget("対象1");
        task1.setIntervalDays(7);
        task1.setMethod("方法1");
        task1.setNextDueDate(LocalDate.now());
        Task task2 = new Task();
        task2.setPlace("場所2");
        task2.setTarget("対象2");
        task2.setIntervalDays(3);
        task2.setMethod("方法2");
        task2.setNextDueDate(LocalDate.now());
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskResponse> responses = taskService.getAllTasks();

        assertAll(
            () -> assertEquals(2, responses.size()),
            () -> assertEquals("場所1", responses.get(0).getPlace()),
            () -> assertEquals("場所2", responses.get(1).getPlace())
        );
    }

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

    @Test
    @DisplayName("掃除タスクを更新できること")
    void updateTask_Success() {
        Long taskId = 1l;

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setPlace("お風呂");
        existingTask.setTarget("浴槽");
        existingTask.setIntervalDays(7);
        existingTask.setMethod("洗剤で洗う");
        existingTask.setNextDueDate(LocalDate.now());

        TaskRequest updateRequest = new TaskRequest("キッチン", "換気扇", 30, "重曹で洗う");

        Task updateTask = new Task();
        updateTask.setId(taskId);
        updateTask.setPlace("キッチン");
        updateTask.setTarget("換気扇");
        updateTask.setIntervalDays(30);
        updateTask.setMethod("重曹で洗う");
        updateTask.setNextDueDate(LocalDate.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updateTask);

        TaskResponse response = taskService.updateTask(updateRequest, taskId);

        assertAll("更新レスポンスの検証",
            () -> assertEquals(taskId, response.getId()),
            () -> assertEquals("キッチン", response.getPlace()),
            () -> assertEquals("換気扇", response.getTarget()),
            () -> assertEquals(30, response.getIntervalDays()),
            () -> assertEquals("重曹で洗う", response.getMethod())
        );

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("掃除タスクを削除できること")
    void deleteTask_Success() {
        Long taskId = 1L;

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setPlace("お風呂");
        existingTask.setTarget("浴槽");
        existingTask.setIntervalDays(7);
        existingTask.setMethod("洗剤で洗う");
        existingTask.setNextDueDate(LocalDate.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(existingTask);
    }
}
