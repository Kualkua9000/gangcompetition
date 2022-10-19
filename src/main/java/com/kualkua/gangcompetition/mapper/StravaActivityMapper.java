package com.kualkua.gangcompetition.mapper;

import com.kualkua.gangcompetition.domain.Activity;
import com.kualkua.gangcompetition.domain.strava.StravaActivityModel;

import java.util.Arrays;

public class StravaActivityMapper {
    public Activity convertModelToStravaActivity(StravaActivityModel model, long memberId) {
        return new Activity(
                memberId,
                model.getStravaActivityId(),
                model.getName(),
                model.getDistance(),
                model.getMovingTime(),
                model.getElapsedTime(),
                model.getType(),
                model.getSportType(),
                model.getStartDate(),
                model.getStartDateLocal(),
                model.getTimezone(),
                model.getUtcOffset(),
                model.getLocationCity(),
                model.getLocationState(),
                model.getLocationCountry(),
                model.getAverageSpeed(),
                model.getMaxSpeed(),
                model.getAverageCadence(),
                model.getAverageWatts());
    }

    public Activity[] convertModelArrayToStravaActivityArray(StravaActivityModel[] models, long memberId) {
        return Arrays.stream(models)
                .map(v -> convertModelToStravaActivity(v, memberId))
                .toArray(Activity[]::new);
    }
}
