package ru.aizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;

@Component
public class EditorController extends AbstractController{
    private StatsController statsController;

    public EditorController(Character character) {
        super(character);
    }

    @Autowired
    public void initialize(StatsController statsController) {
        this.statsController = statsController;
    }

    public void loadCharacter() {
        statsController.loadCharacter();
    }

    @Override
    public void saveCharacter() {
        statsController.saveCharacter();
    }
}
