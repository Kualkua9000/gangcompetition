package com.kualkua.gangcompetition.dto;

import com.kualkua.gangcompetition.domain.strava.StravaAthleteModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StravaActivityDto {

    private StravaAthleteModel athlete;
    private String name;
    private Double distance;
    private Integer movingTime;
    private Integer elapsedTime;
    private String type;
    private String sportType;
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime startDateLocal;
    private String timezone;
    private Double utcOffset;
    private String locationCity;
    private String locationState;
    private String locationCountry;
    private Double averageSpeed;
    private Double maxSpeed;
    private Double averageCadence;
    private Double averageWatts;
}
