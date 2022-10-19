package com.kualkua.gangcompetition.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "challenge")
@NoArgsConstructor
@Data
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long challengeId;
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "member_id"))
    private Long creatorId;
    private String activityType;
    private Double distance;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    @OneToMany(mappedBy = "challenge")
    private Set<MembersChallenge> membersChallenges;
}
