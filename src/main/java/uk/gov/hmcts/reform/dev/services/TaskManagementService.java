package uk.gov.hmcts.reform.dev.services;

import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.models.TaskStatus;

import java.util.List;

public interface TaskManagementService {
    TaskResponse createTask(String caseWorkerId, CreateTaskRequest request);

    TaskResponse getTask(String caseWorkerId, Long taskId);

    List<TaskResponse> getAllTasks(String caseWorkerId);

    TaskResponse updateStatus(String caseWorkerId, Long taskId, TaskStatus status);

    void deleteTask(String caseWorkerId, Long taskId);
}
