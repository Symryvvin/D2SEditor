package ru.aizen.domain.character.entity;

import ru.aizen.domain.data.binary.Binary;

public class Quest implements Comparable<Quest> {
    private Act act;
    private String name;
    private int position;
    private boolean isComplete;
    private Binary binary;

    private String activeImage;
    private String completeImage;

    public Quest(int act, String name, String position, String activeImage, String completeImage) {
        this.act = Act.valueOf("ACT" + act);
        this.name = name;
        this.position = Integer.parseInt(position);
        this.activeImage = activeImage;
        this.completeImage = completeImage;
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

    public String getActiveImage() {
        return activeImage;
    }

    public String getCompleteImage() {
        return completeImage;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "act=" + act +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", isComplete=" + isComplete +
                ", binary=" + binary +
                ", activeImage='" + activeImage + '\'' +
                ", completeImage='" + completeImage + '\'' +
                '}';
    }

    @Override
    public int compareTo(Quest o) {
        return Integer.compare(position, o.position);
    }
}
