package ru.aizen.domain.character.entity;

import ru.aizen.domain.character.Difficult;

public class Waypoint {
    private Act act;
    private String name;
    private Difficult difficult;
    private boolean isActive;

    public Waypoint(Act act, String name, Difficult difficult) {
        this.act = act;
        this.name = name;
        this.difficult = difficult;
    }

    public boolean isActive() {
        return isActive;
    }
}
