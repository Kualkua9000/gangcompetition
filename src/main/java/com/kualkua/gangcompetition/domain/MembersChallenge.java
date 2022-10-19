package com.kualkua.gangcompetition.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "members_challenge")
@NoArgsConstructor
@Data
public class MembersChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    Challenge challenge;

    LocalDateTime creationDate;
}
