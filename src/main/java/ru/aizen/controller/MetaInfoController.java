package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;

@Component
public class MetaInfoController extends AbstractController {
    @FXML private Label title;
    @FXML private Label name;
    @FXML private Label level;
    @FXML private Label characterClass;
    @FXML private Label expansion;

    private final SkillsController skillsController;
    private final EditorController editorController;

    @Autowired
    public MetaInfoController(Character character,
                              SkillsController skillsController,
                              EditorController editorController) {
        super(character);
        this.skillsController = skillsController;
        this.editorController = editorController;

    }

    public void initialize() {
        title.textProperty().bind(character.titleProperty());
        name.textProperty().bind(character.nameProperty());
        level.textProperty().bind(character.levelProperty());
        characterClass.textProperty().bind(character.classProperty());
        expansion.textProperty().bind(character.expansionProperty());
    }

    @Override
    protected void loadCharacter() {

    }

    @Override
    public void saveCharacter() {

    }

    public void pickAssassin() {
        pick(CharacterClass.ASSASSIN);
    }

    public void pickAmazon() {
        pick(CharacterClass.AMAZON);
    }

    public void pickNecromancer() {
        pick(CharacterClass.NECROMANCER);
    }

    public void pickBarbarian() {
        pick(CharacterClass.BARBARIAN);
    }

    public void pickPaladin() {
        pick(CharacterClass.PALADIN);
    }

    public void pickSorceress() {
        pick(CharacterClass.SORCERESS);
    }

    public void pickDruid() {
        pick(CharacterClass.DRUID);
    }

    private void pick(CharacterClass characterClass) {
        character.setCharacterClass(characterClass);
        editorController.changeTitleList();
        skillsController.loadCharacter();
    }
}
