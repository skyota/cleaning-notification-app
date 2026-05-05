package com.example.cleaning_notification_app.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
    private Map<String, String> errors; // key: フィールド名、value: エラーメッセージ

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
