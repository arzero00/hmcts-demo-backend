package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "case_workers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_case_worker_id", columnNames = "case_worker_id")
    })
public class CaseWorker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "case_worker_id", nullable = false, unique = true)
    private String caseWorkerId;

    @Column(nullable = false)
    private String name;


    private String sex;

    private LocalDate dob;

    // getters/setters
}
