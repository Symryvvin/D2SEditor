package ru.aizen.app;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public enum InputCombination {
    OPEN(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)),
    SAVE(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)),
    REVERT(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)),
    EXIT(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)),
    BAK_MANAGER(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN)),
    OPTIONS(new KeyCodeCombination(KeyCode.ESCAPE)),
    HEX(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN)),
    STATS(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN)),
    SKILLS(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN)),
    INVENTORY(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN)),
    WAYPOINTS(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN)),
    QUESTS(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

    private KeyCodeCombination combination;

    InputCombination(KeyCodeCombination combination) {
        this.combination = combination;
    }

    public KeyCodeCombination get() {
        return combination;
    }
}
