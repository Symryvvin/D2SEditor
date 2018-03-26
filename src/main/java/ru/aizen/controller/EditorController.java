package ru.aizen.controller;

import javafx.fxml.FXML;
import ru.aizen.domain.Character;

public class EditorController extends AbstractController{
    @FXML private StatsController statsController;

    public void setCharacter(Character character) {
        super.setCharacter(character);
        statsController.setCharacter(character);
    }

    public void loadCharacter() {
        statsController.loadCharacter();
    }
}
