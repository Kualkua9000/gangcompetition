package com.kualkua.gangcompetition.domain;

public enum ActivityType {

    RUNNING("Run"),
    WALKING("Walk"),
    RIDING("Ride");

    private final String name;

    private ActivityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
