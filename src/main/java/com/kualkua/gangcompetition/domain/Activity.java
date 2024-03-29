package com.kualkua.gangcompetition.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "activity")
@NoArgsConstructor
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activityId;
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "member_id"))
    private Long memberId;
    private Long stravaActivityId;
    private String name;
    private Double distance;
    private Integer movingTime;
    private Integer elapsedTime;
    private String type;
    private String sportType;
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

    public Activity(Long memberId,
                    Long stravaActivityId,
                    String name,
                    Double distance,
                    Integer movingTime,
                    Integer elapsedTime,
                    String type,
                    String sportType,
                    LocalDateTime startDate,
                    LocalDateTime startDateLocal,
                    String timezone,
                    Double utcOffset,
                    String locationCity,
                    String locationState,
                    String locationCountry,
                    Double averageSpeed,
                    Double maxSpeed,
                    Double averageCadence,
                    Double averageWatts) {
        this.memberId = memberId;
        this.stravaActivityId = stravaActivityId;
        this.name = name;
        this.distance = distance;
        this.movingTime = movingTime;
        this.elapsedTime = elapsedTime;
        this.type = type;
        this.sportType = sportType;
        this.startDate = startDate;
        this.startDateLocal = startDateLocal;
        this.timezone = timezone;
        this.utcOffset = utcOffset;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.locationCountry = locationCountry;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
        this.averageCadence = averageCadence;
        this.averageWatts = averageWatts;
    }
}
