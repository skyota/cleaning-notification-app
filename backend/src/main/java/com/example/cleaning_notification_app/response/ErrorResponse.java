package com.example.cleaning_notification_app.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Map<String , String> errors; // key: フィールド名、value: エラーメッセージ
}
