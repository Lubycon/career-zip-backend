package com.careerzip.domain.project.entity;

import com.careerzip.domain.account.entity.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "aim")
    private String aim;

    @Column(name = "role")
    private String role;

    @Column(name = "main_business")
    private String mainBusiness;

    @Column(name = "participants_count")
    private Integer participantsCount;

    @Column(name = "contribution")
    private Double contribution;

    @Column(name = "team_members")
    private String teamMembers;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Builder
    private Project(Long id, String title, String description, String aim, String role, String mainBusiness,
                    Integer participantsCount, Double contribution, String teamMembers,
                    LocalDate startDate, LocalDate endDate, Account account) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.aim = aim;
        this.role = role;
        this.mainBusiness = mainBusiness;
        this.participantsCount = participantsCount;
        this.contribution = contribution;
        this.teamMembers = teamMembers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.account = account;
    }
}
