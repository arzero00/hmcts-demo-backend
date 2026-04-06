package uk.gov.hmcts.reform.dev;



import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.controllers.TaskController;
import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.services.TaskManagementService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskManagementService service;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskResponse mockResponse;
    private final String USER_ID = "user-123";

    @BeforeEach
    void setUp() {
        Task task = new Task();
            task.setId(1L);
            task.setStatus(TaskStatus.COMPLETED);
            task.setDescription("Test Description");
            task.setTitle("Test Task");
            task.setDueDate(LocalDateTime.now());

        mockResponse = new TaskResponse(task);
    }

    @Test
    void createTask_ShouldReturnCreated() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("Test Task");
            request.setDescription("Test Description");
            request.setDueDate(LocalDateTime.now());

        mockResponse.setDueDate(request.getDueDate());

        when(service.createTask(eq(USER_ID), any(CreateTaskRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/tasks")
                            .header("X-USER-ID", USER_ID)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Test Task"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.title").value("Test Task"));

    }

    @Test
    void createTask_MissingHeader_ShouldThrowUnauthorized() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Title");

        // Note: Spring throws 400 MissingRequestHeaderException by default if @RequestHeader is missing
        mockMvc.perform(post("/api/v1/tasks")
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getTask_ShouldReturnTask() throws Exception {
        when(service.getTask(USER_ID, 1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/tasks/1")
                            .header("X-USER-ID", USER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllTasks_ShouldReturnList() throws Exception {
        when(service.getAllTasks(USER_ID)).thenReturn(Collections.singletonList(mockResponse));

        mockMvc.perform(get("/api/v1/tasks")
                            .header("X-USER-ID", USER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void updateStatus_ShouldReturnUpdatedTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.COMPLETED);
        task.setDescription("Test Description");
        task.setTitle("Test Task");
        task.setDueDate(LocalDateTime.now());

        TaskResponse taskResponse = new TaskResponse(task);

        UpdateStatusRequest request = new UpdateStatusRequest();
            request.setStatus(TaskStatus.COMPLETED);

        when(service.updateStatus(any(), any(), any())).thenReturn(taskResponse);


        mockMvc.perform(patch("/api/v1/tasks/1/status")
                            .header("X-USER-ID", USER_ID)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        //doNothing().when(service.deleteTask(USER_ID, 1L));

        mockMvc.perform(delete("/api/v1/tasks/1")
                            .header("X-USER-ID", USER_ID))
            .andExpect(status().isNoContent());
    }

    @Test
    void createTask_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Create request with empty title to trigger @NotBlank validation
        CreateTaskRequest invalidRequest = new CreateTaskRequest();
        invalidRequest.setTitle("");

        mockMvc.perform(post("/api/v1/tasks")
                            .header("X-USER-ID", USER_ID)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }
}
