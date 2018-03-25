package ru.aizen.domain;

public class Attribute {
    public static final int ID_OFFSET = 9;

    private int id;
    private String name;
    private int length;
    private int divide;

    public Attribute(String id, String name, String length, String divide) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.length = Integer.parseInt(length);
        this.divide = Integer.parseInt(divide);
    }

    public int getId() {
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
