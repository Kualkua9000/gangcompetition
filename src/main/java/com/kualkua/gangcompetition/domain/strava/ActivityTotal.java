package com.kualkua.gangcompetition.domain.strava;

import lombok.Data;

@Data
public class ActivityTotal {

    // TODO: 25.02.2022 to change serializble names of field
    public int count;
    public float distance;
    public int movingTime;
    public int elapsedTime;
    public float elevationGain;
    public int achievementCount;
}
