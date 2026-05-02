package com.example.cleaning_notification_app.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String place;
    private String target;
    private Integer intervalDays;
    private String method;
    private LocalDate nextDueDate;
}
