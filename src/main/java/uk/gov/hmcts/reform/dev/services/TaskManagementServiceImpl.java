package uk.gov.hmcts.reform.dev.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dtos.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.exceptions.CaseWorkerNotFoundException;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.util.List;


    @Service
    public class TaskManagementServiceImpl implements TaskManagementService {

        private final TaskRepository taskRepository;
        private final CaseWorkerRepository caseWorkerRepository;

        public TaskManagementServiceImpl(TaskRepository taskRepository,
                                         CaseWorkerRepository caseWorkerRepository) {
            this.taskRepository = taskRepository;
            this.caseWorkerRepository = caseWorkerRepository;
        }

        @Override
        public TaskResponse createTask(String caseWorkerId, CreateTaskRequest request) {

            CaseWorker worker = caseWorkerRepository.findByCaseWorkerId(caseWorkerId)
                .orElseThrow(() -> new CaseWorkerNotFoundException(caseWorkerId));

            Task task = new Task();
                task.setTitle(request.getTitle());
                task.setDescription(request.getDescription());
                task.setDueDate(request.getDueDate());
                task.setStatus(TaskStatus.TODO);
                task.setCaseWorker(worker);

            return new TaskResponse(taskRepository.save(task));
        }

        @Override
        public TaskResponse getTask(String caseWorkerId, Long taskId) {

            Task task = taskRepository
                .findByIdAndCaseWorker_CaseWorkerId(taskId, caseWorkerId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

            return new TaskResponse(task);
        }

        @Override
        public List<TaskResponse> getAllTasks(String caseWorkerId) {

            return taskRepository.findAllByCaseWorker_CaseWorkerId(caseWorkerId)
                .stream()
                .map(TaskResponse::new)
                .toList();
        }

        @Override
        public TaskResponse updateStatus(String caseWorkerId, Long taskId, TaskStatus status) {

            Task task = taskRepository
                .findByIdAndCaseWorker_CaseWorkerId(taskId, caseWorkerId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

            task.setStatus(status);

            return new TaskResponse(taskRepository.save(task));
        }

        @Override
        public void deleteTask(String caseWorkerId, Long taskId) {

            Task task = taskRepository
                .findByIdAndCaseWorker_CaseWorkerId(taskId, caseWorkerId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

            taskRepository.delete(task);
        }
}
