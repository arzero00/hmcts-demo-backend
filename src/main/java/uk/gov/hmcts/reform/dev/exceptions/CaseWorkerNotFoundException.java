package uk.gov.hmcts.reform.dev.exceptions;

public class CaseWorkerNotFoundException extends RuntimeException {
    public CaseWorkerNotFoundException(String id) {
        super("CaseWorker not found: " + id);
    }
}
