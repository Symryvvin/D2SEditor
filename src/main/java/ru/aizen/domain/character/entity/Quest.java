package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVRecord;
import ru.aizen.domain.data.Extensions;
import ru.aizen.domain.data.binary.Binary;

public class Quest implements Comparable<Quest> {
    private static final String COMPLETED = "_complete";
    private static final String ACTIVE = "_active";

    private Act act;
    private String name;
    private int position;
    private int order;
    private boolean isComplete;
    private Binary binary;
    private String activeImage;
    private String completeImage;

    public Quest(CSVRecord record) {
        this.name = record.get("name");
        this.act = Act.getByNumber(Integer.parseInt(record.get("act")));
        this.position = Integer.parseInt(record.get("position"));
        this.order = Integer.parseInt(record.get("order"));
        this.activeImage = record.get("icon") + ACTIVE + Extensions.PNG;
        this.completeImage = record.get("icon") + COMPLETED + Extensions.PNG;
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

    public int getPosition() {
        return position;
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
