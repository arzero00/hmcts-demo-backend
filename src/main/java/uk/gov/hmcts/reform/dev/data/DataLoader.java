package uk.gov.hmcts.reform.dev.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CaseWorkerRepository workerRepo;
    private final TaskRepository taskRepo;

    @Override
    public void run(String... args) {

//        for (int i = 1; i <= 5; i++) {
//
//            CaseWorker worker = new CaseWorker();
//            worker.setCaseWorkerId("worker-" + i);
//            worker.setName("Peter " + i);
//            worker.setSex(i % 2 == 0 ? "F" : "M");
//            worker.setDob(LocalDate.of(1990, 1, i));
//
//            workerRepo.save(worker);
//
//            for (int j = 1; j <= 20; j++) {
//
//                Task task = new Task();
//                task.setTitle("Task Title" + j);
//                task.setDescription("Description " + j);
//                task.setStatus(TaskStatus.TODO);
//                task.setDueDate(LocalDateTime.now().plusDays(j));
//                task.setCaseWorker(worker);
//
//                taskRepo.save(task);
//            }
//        }
    }
}
