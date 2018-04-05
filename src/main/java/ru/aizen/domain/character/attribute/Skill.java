package ru.aizen.domain.character.attribute;

import javafx.scene.image.Image;
import ru.aizen.domain.character.CharacterClass;

public class Skill implements Comparable<Skill> {
    private int value;
    private int order;

    private String name;
    private CharacterClass characterClass;
    private Image image;
    private int page;
    private int row;
    private int column;

    public Skill(int value, int order) {
        this.value = value;
        this.order = order;
    }

    public Skill(String name, CharacterClass characterClass, Image image, int page, int row, int column) {
        this.name = name;
        this.characterClass = characterClass;
        this.image = image;
        this.page = page;
        this.row = row;
        this.column = column;
    }

    public void mergeValue(Skill skill) {
        this.value = skill.value;
        this.order = skill.order;
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

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getPage() {
        return page;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "value=" + value +
                ", name='" + name + '\'' +
                ", characterClass=" + characterClass +
                ", image=" + image +
                ", page=" + page +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public int compareTo(Skill o) {
        return Integer.compare(order, o.order);
    }
}
