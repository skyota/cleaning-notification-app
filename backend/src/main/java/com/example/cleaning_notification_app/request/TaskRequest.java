package com.example.cleaning_notification_app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {
    @NotBlank(message = "場所は必須です")
    @Size(max = 100, message = "場所は100字以内で入力してください")
    private String place;

    @NotBlank(message = "対象は必須です")
    @Size(max = 100, message = "対象は100字以内で入力してください")
    private String target;

    @NotNull(message = "頻度は必須です")
    @Min(value = 1, message = "頻度は1日以上で指定してください")
    private Integer intervalDays;

    private String method;
}
