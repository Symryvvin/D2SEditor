package ru.aizen.domain.character.entity;

public class Skill {
    private int value;
    private int order;
    private String name;
    private String image;
    private int page;
    private int row;
    private int column;

    public Skill(int order, String name, String image, int page, int row, int column) {
        this.order = order;
        this.name = name;
        this.image = image;
        this.page = page;
        this.row = row;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return image;
    }

    public int getPage() {
        return page;
    }

    public int getRow() {
        return row - 1;
    }

    public int getColumn() {
        return column - 1;
    }

    public int getOrder() {
        return order;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "value=" + value +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", page=" + page +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
