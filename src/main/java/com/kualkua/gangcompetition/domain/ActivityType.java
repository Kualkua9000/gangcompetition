package com.kualkua.gangcompetition.domain;

public enum ActivityType {

    RUNNING("running"),
    WALKING("walking"),
    BICYCLE("bicycle");

    private final String name;

    private ActivityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
