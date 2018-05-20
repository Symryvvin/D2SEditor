package ru.aizen.domain.character.entity;

public class Attribute {
    public static final int ID_OFFSET = 9;

    private long id;
    private String name;
    private int length;

    public Attribute(int id, String name, int length) {
        this.id = id;
        this.name = name;
        this.length = length;
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
}
