package ru.aizen.domain.character.attribute;

import javafx.scene.image.Image;

public class Skill {
    private String name;
    private int value;
    private Image image;
    private int order;

    public Skill(byte value, int order) {
        this.value = value;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", image=" + image +
                ", order=" + order +
                '}';
    }


}
