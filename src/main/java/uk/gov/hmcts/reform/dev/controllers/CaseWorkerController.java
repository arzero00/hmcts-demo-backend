package uk.gov.hmcts.reform.dev.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.dtos.CaseWorkerResponse;
import uk.gov.hmcts.reform.dev.services.CaseWorkerService;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/api/v1/case-workers")
@RequiredArgsConstructor
public class CaseWorkerController {

    private final CaseWorkerService service;

    @GetMapping
    public ResponseEntity<List<CaseWorkerResponse>> getAllCaseWorkers() {

        List<CaseWorkerResponse> workers = service.getAllCaseWorkers();

        return ResponseEntity.ok(workers);
    }
}
