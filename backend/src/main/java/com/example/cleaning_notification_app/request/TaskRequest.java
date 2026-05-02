package com.example.cleaning_notification_app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {
    @NotEmpty(message = "場所は必須です")
    private String place;

    @NotEmpty(message = "対象は必須です")
    private String target;

    @NotNull(message = "頻度は必須です")
    @Min(value = 1, message = "頻度は1日以上で指定してください")
    private Integer intervalDays;

    private String method;
}
