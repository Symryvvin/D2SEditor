package ru.aizen.domain;

public enum GameVersion {
    VERSION_1_10("1.10"),
    UNDEFINED("UNDEFINED");

    private String value;

    GameVersion(String value) {
        this.value = value;
    }

    public static GameVersion parse(String hex) {
        switch (hex) {
            case "60":
                return VERSION_1_10;
            default:
                return UNDEFINED;
        }
    }

    public String getValue() {
        return value;
    }
}