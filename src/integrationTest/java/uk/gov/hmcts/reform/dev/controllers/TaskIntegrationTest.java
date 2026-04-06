package uk.gov.hmcts.reform.dev.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rolls back database changes after each test
public class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseWorkerRepository caseWorkerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testCaseWorkerId = "CW-INT-001";

    @BeforeEach
    void setup() {
        // Prepare database with a real CaseWorker entity
        CaseWorker worker = new CaseWorker();
        worker.setCaseWorkerId(testCaseWorkerId);
        worker.setName("Integration Test Worker");
        caseWorkerRepository.save(worker);
    }

    @Test
    void shouldCreateAndRetrieveTask() throws Exception {
        // 1. Create a Task via API
        CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("Integration Task");
            request.setDescription("Testing the full stack");
            request.setDueDate(LocalDateTime.now().plusDays(2));

        String jsonResponse = mockMvc.perform(post("/api/v1/tasks")
                                                  .header("X-USER-ID", testCaseWorkerId)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Integration Task"))
            .andExpect(jsonPath("$.description").value("Testing the full stack"))
            .andExpect(status().is(201))
            .andReturn().getResponse().getContentAsString();

        // Extract ID from created task
        Integer taskId = com.jayway.jsonpath.JsonPath.read(jsonResponse, "$.id");

        // 2. Retrieve that Task to verify it's actually in the Database
        Optional<Task> task = taskRepository.findById(Long.valueOf(taskId));
        assertEquals(task.get().getTitle(), "Integration Task");
        assertEquals(task.get().getDescription(), "Testing the full stack");
    }
}
