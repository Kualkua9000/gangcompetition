package com.kualkua.gangcompetition.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity(name = "member_activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double activityDistance;
    private String activityTime;
    private String activityPace;
    private Date date;
    @ElementCollection(targetClass = ActivityType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_activity_type", joinColumns = @JoinColumn(name = "activity_id"))
    @Enumerated(EnumType.STRING)
    private Set<ActivityType> activityType;

    public Activity(){}

    public Activity(
            double activityDistance,
            String activityTime,
            String activityPace,
            Date date,
            Set<ActivityType> activityType) {
        this.activityDistance = activityDistance;
        this.activityTime = activityTime;
        this.activityPace = activityPace;
        this.date = date;
        this.activityType = activityType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getActivityDistance() {
        return activityDistance;
    }

    public void setActivityDistance(double activityDistance) {
        this.activityDistance = activityDistance;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityPace() {
        return activityPace;
    }

    public void setActivityPace(String activityPace) {
        this.activityPace = activityPace;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<ActivityType> getActivityType() {
        return activityType;
    }

    public void setActivityType(Set<ActivityType> activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityDistance=" + activityDistance +
                ", activityTime='" + activityTime + '\'' +
                ", activityPace='" + activityPace + '\'' +
                ", date=" + date +
                ", activityType=" + activityType +
                '}';
    }
}
