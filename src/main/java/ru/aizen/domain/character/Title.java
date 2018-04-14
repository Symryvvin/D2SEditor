package ru.aizen.domain.character;

public class Title {
    private String name;
    private Difficult difficult;

    public Title(String name, String difficult) {
        this.name = name;
        this.difficult = Difficult.valueOf(difficult.toUpperCase());
    }

    public String getName() {
        return name;
    }

    public Difficult getDifficult() {
        return difficult;
    }
}
