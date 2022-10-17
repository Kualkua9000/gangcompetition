package com.kualkua.gangcompetition.domain.strava;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StravaActivityModel {

    @JsonProperty("athlete")
    public StravaAthleteModel athlete;
    @JsonProperty("name")
    public String name;
    @JsonProperty("distance")
    public Double distance;
    @JsonProperty("moving_time")
    public Integer movingTime;
    @JsonProperty("elapsed_time")
    public Integer elapsedTime;
    @JsonProperty("type")
    public String type;
    @JsonProperty("sport_type")
    public String sportType;
    @JsonProperty("id")
    public Long id;
    @JsonProperty("start_date")
    public LocalDateTime startDate;
    @JsonProperty("start_date_local")
    public LocalDateTime startDateLocal;
    @JsonProperty("timezone")
    public String timezone;
    @JsonProperty("utc_offset")
    public Double utcOffset;
    @JsonProperty("location_city")
    public String locationCity;
    @JsonProperty("location_state")
    public String locationState;
    @JsonProperty("location_country")
    public String locationCountry;
    @JsonProperty("average_speed")
    public Double averageSpeed;
    @JsonProperty("max_speed")
    public Double maxSpeed;
    @JsonProperty("average_cadence")
    public Double averageCadence;
    @JsonProperty("average_watts")
    public Double averageWatts;
}
