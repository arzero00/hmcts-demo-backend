package uk.gov.hmcts.reform.dev.dtos;

import lombok.Data;
import uk.gov.hmcts.reform.dev.models.CaseWorker;

import java.time.LocalDate;

@Data
public class CaseWorkerResponse {

    private String caseWorkerId;
    private String name;
    private String sex;
    private LocalDate dob;

    public CaseWorkerResponse(CaseWorker worker) {
        this.caseWorkerId = worker.getCaseWorkerId();
        this.name = worker.getName();
        this.sex = worker.getSex();
        this.dob = worker.getDob();
    }
}
