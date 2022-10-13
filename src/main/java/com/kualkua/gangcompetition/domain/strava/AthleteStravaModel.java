package com.kualkua.gangcompetition.domain.strava;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AthleteStravaModel {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("resource_state")
    public Integer resourceState;
}
