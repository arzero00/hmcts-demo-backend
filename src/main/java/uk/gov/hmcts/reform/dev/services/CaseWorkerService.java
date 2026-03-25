package uk.gov.hmcts.reform.dev.services;

import uk.gov.hmcts.reform.dev.dtos.CaseWorkerResponse;

import java.util.List;

public interface CaseWorkerService {

    List<CaseWorkerResponse> getAllCaseWorkers();
}
