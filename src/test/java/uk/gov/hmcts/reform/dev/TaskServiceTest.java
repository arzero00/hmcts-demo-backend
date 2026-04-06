package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.services.TaskManagementServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    CaseWorkerRepository caseWorkerRepository;

    @InjectMocks
    TaskManagementServiceImpl service;

    @Test
    void shouldCreateTask() {

        CaseWorker worker = new CaseWorker();
        worker.setCaseWorkerId("user1");

        when(caseWorkerRepository.findByCaseWorkerId("user1"))
            .thenReturn(Optional.of(worker));

        when(taskRepository.save(any())).thenReturn(new Task());

        CreateTaskRequest req = new CreateTaskRequest();
        req.setTitle("Test");

        TaskResponse result = service.createTask("user1", req);

        assertNotNull(result);
    }

    @Test
    void shouldNotAllowAccessToOtherUsersTask() {

        when(taskRepository.findByIdAndCaseWorker_CaseWorkerId(1L, "user1"))
            .thenReturn(Optional.empty());

        assertThrows(
            TaskNotFoundException.class,
            () -> service.getTask("user1", 1L)
        );
    }
}
