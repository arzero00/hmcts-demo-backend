package uk.gov.hmcts.reform.dev.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.exceptions.UnauthorizedException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskManagementService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Getter
@Setter
@RestController
@RequestMapping("/api/v1/tasks")

@Tag(name = "Tasks", description = "Task Management API")
//@Operation(summary = "Create task")
public class TaskController {

    private final TaskManagementService service;

    public TaskController(TaskManagementService service) {
        this.service = service;
    }

    private String validateUser(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new UnauthorizedException();
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
        @RequestHeader("X-USER-ID") String userId,
        @Valid @RequestBody CreateTaskRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.createTask(validateUser(userId), request));
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id) {

        return service.getTask(validateUser(userId), id);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(
        @RequestHeader("X-USER-ID") String userId) {

        return service.getAllTasks(validateUser(userId));
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id,
        @Valid @RequestBody UpdateStatusRequest request) {

        return service.updateStatus(validateUser(userId), id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id) {

        service.deleteTask(validateUser(userId), id);
        return ResponseEntity.noContent().build();
    }
}
