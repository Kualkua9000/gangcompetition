package com.kualkua.gangcompetition.mapper;

import com.kualkua.gangcompetition.domain.Activity;
import com.kualkua.gangcompetition.domain.ActivityType;
import com.kualkua.gangcompetition.domain.strava.StravaActivityModel;

import java.util.Arrays;
import java.util.HashSet;

public class StravaActivityMapper {
    public Activity convertModelToStravaActivity(StravaActivityModel model, long memberId) {
        HashSet<ActivityType> set = new HashSet<>();
        // TODO: 18.10.2022 to do correct ActivityType mapping
        //set.add(ActivityType.valueOf(model.getType()));
        return new Activity(
                memberId,
                set,
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
