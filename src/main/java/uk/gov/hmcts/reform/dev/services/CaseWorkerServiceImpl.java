package uk.gov.hmcts.reform.dev.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.dtos.CaseWorkerResponse;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaseWorkerServiceImpl implements CaseWorkerService {

    private final CaseWorkerRepository repository;

    @Override
    public List<CaseWorkerResponse> getAllCaseWorkers() {

        return repository.findAll()
            .stream()
            .map(CaseWorkerResponse::new)
            .toList();
    }
}
