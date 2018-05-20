package ru.aizen.domain.character.entity;

import ru.aizen.domain.data.binary.Binary;

public class Quest {
    private Act act;
    private String name;
    private int position;
    private boolean isComplete;
    private Binary binary;

    public Quest(int act, String name, String position) {
        this.act = Act.valueOf("ACT" + act);
        this.name = name;
        this.position = Integer.parseInt(position);
    }

    public Act getAct() {
        return act;
    }

    public String getName() {
        return name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getPosition() {
        return position;
    }

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(Binary binary) {
        this.binary = binary;
        this.isComplete = binary.get(0);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "act=" + act +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", isComplete=" + isComplete +
                '}';
    }
}
