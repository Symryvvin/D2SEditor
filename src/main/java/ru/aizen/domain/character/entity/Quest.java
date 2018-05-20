package ru.aizen.domain.character.entity;

import ru.aizen.domain.data.binary.Binary;

public class Quest implements Comparable<Quest> {
    private Act act;
    private String name;
    private int position;
    private int order;
    private boolean isComplete;
    private Binary binary;

    private String activeImage;
    private String completeImage;

    public Quest(int act, String name, String position, String order, String activeImage, String completeImage) {
        this.act = Act.valueOf("ACT" + act);
        this.name = name;
        this.position = Integer.parseInt(position);
        this.order = Integer.parseInt(order);
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
        this.isComplete = complete;
        this.binary.set(0, complete);
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

    public String getImagePath() {
        if (isComplete)
            return completeImage;
        else
            return activeImage;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "act=" + act +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", order=" + order +
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
