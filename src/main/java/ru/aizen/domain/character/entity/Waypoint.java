package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVRecord;

public class Waypoint {
    private int order;
    private Act act;
    private String name;
    private boolean isActive;

    public Waypoint(int act, String name) {
        this.act = Act.getByNumber(act);
        this.name = name;
    }

    public Waypoint(CSVRecord record) {
        this.act = Act.getByNumber(Integer.parseInt(record.get("act")));
        this.name = record.get("name");
        this.order = Integer.parseInt(record.get("order"));
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

    public int getOrder() {
        return order;
    }
}
