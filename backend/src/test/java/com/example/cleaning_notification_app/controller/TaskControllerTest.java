package com.example.cleaning_notification_app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cleaning_notification_app.request.TaskRequest;
import com.example.cleaning_notification_app.service.TaskServiceImpl;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskServiceImpl taskServiceImpl;

    @Test
    @DisplayName("バリテーションエラー：不正な入力値の場合、404エラーが返ること")
    void createTask_ValidationError_Returns404() throws Exception {
        TaskRequest invalidRequest = new TaskRequest("", "浴槽", -1, "洗剤で洗う");

        String requestBody = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest()) // HTTPステータスが400であることを検証
                .andExpect(jsonPath("$.message").value("入力内容に不備があります")) // エラーメッセージの検証
                .andExpect(jsonPath("$.errors.place").exists()) // place のエラー詳細が存在するか検証
                .andExpect(jsonPath("$.errors.intervalDays").exists()); // intervalDays のエラー詳細が存在するか検証
    }
}
