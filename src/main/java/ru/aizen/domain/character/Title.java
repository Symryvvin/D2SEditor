package ru.aizen.domain.character;

public class Title {
    private String name;
    private Difficult difficult;
    private int value;

    public Title(String name, String difficult, int value) {
        this.name = name;
        this.difficult = Difficult.valueOf(difficult.toUpperCase());
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Difficult getDifficult() {
        return difficult;
    }

    public int getValue() {
        return value;
    }
}
