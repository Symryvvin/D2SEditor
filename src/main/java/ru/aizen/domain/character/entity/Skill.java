package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVRecord;

public class Skill {
    private int value;
    private int order;
    private String name;
    private String image;
    private String page;
    private int row;
    private int column;

    public Skill(CSVRecord record) {
        this.order = Integer.parseInt(record.get(Skills.ORDER_HEADER));
        this.name = record.get(Skills.NAME_HEADER);
        this.image = record.get(Skills.ICON_HEADER);
        this.page = record.get(Skills.PAGE_HEADER);
        this.row = Integer.parseInt(record.get(Skills.ROW_HEADER));
        this.column = Integer.parseInt(record.get(Skills.COLUMN_HEADER));
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

    public String getPage() {
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
