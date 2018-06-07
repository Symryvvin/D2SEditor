package ru.aizen.domain.character.entity;

public class Game {

    public enum Mode {
        HARDCORE("Hardcore", true),
        SOFTCORE("Softcore", false);

        private String name;
        private boolean value;

        Mode(String name, boolean value) {
            this.name = name;
            this.value = value;
        }

        public static Mode getGameMode(boolean value) {
            if (value)
                return HARDCORE;
            return SOFTCORE;
        }
    }

    public enum Version {
        CLASSIC("Classic", false),
        EXPANSION("Expansion", true);

        private String name;
        private boolean value;

        Version(String name, boolean value) {
            this.name = name;
            this.value = value;
        }
    }
}
