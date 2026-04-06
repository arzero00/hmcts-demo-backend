package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.controllers.CaseWorkerController;
import uk.gov.hmcts.reform.dev.dtos.CaseWorkerResponse;
import uk.gov.hmcts.reform.dev.models.CaseWorker;
import uk.gov.hmcts.reform.dev.services.CaseWorkerService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CaseWorkerController.class)
class CaseWorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CaseWorkerService caseWorkerService;

    private List<CaseWorkerResponse> mockWorkers;

    @BeforeEach
    void setUp() {
        // 1. Create Mock Entity Data
        CaseWorker worker1 = new CaseWorker();
            worker1.setCaseWorkerId("CW001");
            worker1.setName("John Doe");
            worker1.setSex("M");
            worker1.setDob(LocalDate.of(1990, 1, 1));

        CaseWorker worker2 = new CaseWorker();
            worker2.setCaseWorkerId("CW002");
            worker2.setName("Jane Smith");
            worker2.setSex("F");
            worker2.setDob(LocalDate.of(1992, 5, 15));

        CaseWorkerResponse worker1Response = new CaseWorkerResponse(worker1);


        CaseWorkerResponse worker2Response = new CaseWorkerResponse(worker2);


        mockWorkers = Arrays.asList(worker1Response, worker2Response);
    }

    @Test
    void getAllCaseWorkers_ShouldReturnListOfWorkers() throws Exception {
        // Arrange
        when(caseWorkerService.getAllCaseWorkers()).thenReturn(mockWorkers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/case-workers")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2)).andDo(print())
            .andExpect(jsonPath("$[0].caseWorkerId").value("CW001"))
            .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getAllCaseWorkers_EmptyList_ShouldReturnOk() throws Exception {
        // Arrange
        when(caseWorkerService.getAllCaseWorkers()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/v1/case-workers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }
}
