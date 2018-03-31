package ru.aizen.domain.character.attribute;

public class Attribute {
    public static final int ID_OFFSET = 9;

    private long id;
    private String name;
    private int length;
    private int divide;

    public Attribute(String id, String name, String length, String divide) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.length = Integer.parseInt(length);
        this.divide = Integer.parseInt(divide);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getDivide() {
        return divide;
    }
}
