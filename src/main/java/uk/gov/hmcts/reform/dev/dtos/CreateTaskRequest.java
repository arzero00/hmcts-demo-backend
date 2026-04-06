package uk.gov.hmcts.reform.dev.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 900, message = "Description cannot exceed 900 characters")
    private String description;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    // getters and setters
}
