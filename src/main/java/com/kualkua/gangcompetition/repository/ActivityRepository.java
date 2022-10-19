package com.kualkua.gangcompetition.repository;

import com.kualkua.gangcompetition.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT stravaActivityId FROM activity WHERE memberId = :memberId")
    List<Long> getAllStravaActivitiesId(@Param("memberId") Long memberId);
}
