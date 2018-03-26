package ru.aizen.controller;

import javafx.fxml.FXML;
import ru.aizen.domain.Character;

public class EditorController {
    @FXML private StatsController statsController;

    private Character character;

    public void setCharacter(Character character) {
        this.character = character;
        statsController.setCharacter(character);
    }

    public void loadCharacter() {
        statsController.loadCharacterStats();
    }
}
