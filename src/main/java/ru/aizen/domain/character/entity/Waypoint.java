package ru.aizen.domain.character.entity;

import ru.aizen.domain.character.Difficult;

public class Waypoint {
    private Act act;
    private String name;
    private Difficult difficult;
    private boolean isActive;

    public Waypoint(int act, String name) {
        this.act = Act.valueOf("ACT" + act);
        this.name = name;
    }

    public Difficult getDifficult() {
        return difficult;
    }

    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "act=" + act +
                ", name='" + name + '\'' +
                ", difficult=" + difficult +
                ", isActive=" + isActive +
                '}';
    }
}
