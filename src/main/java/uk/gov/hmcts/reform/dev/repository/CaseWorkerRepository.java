package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.CaseWorker;

import java.util.Optional;

public interface CaseWorkerRepository extends JpaRepository<CaseWorker, Long> {

    Optional<CaseWorker> findByCaseWorkerId(String caseWorkerId);

    boolean existsByCaseWorkerId(String caseWorkerId);
}
