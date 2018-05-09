package ru.aizen.domain.character.entity;

public class Waypoint {
    private Act act;
    private String name;
    private boolean isActive;

    public Waypoint(int act, String name) {
        this.act = Act.valueOf("ACT" + act);
        this.name = name;
    }

    public Act getAct() {
        return act;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
