package uk.gov.hmcts.reform.dev.controllers;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.repository.CaseWorkerRepository;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CaseWorkerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseWorkerRepository repository;

    @Test
    void shouldReturnAllSavedCaseWorkers() throws Exception {
        // Manually save workers to the in-memory DB
        CaseWorker cw = new CaseWorker();
        cw.setCaseWorkerId("ID123");
        cw.setName("Alice Smith");
        cw.setSex("F");
        cw.setDob(LocalDate.of(1985, 10, 20));
        repository.save(cw);

        // Verify API returns the saved data
        mockMvc.perform(get("/api/v1/case-workers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));

    }
}
