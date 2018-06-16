package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.*;
import ru.aizen.domain.character.block.MetaBlock;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaInfoController extends BaseController {
    @FXML private Label title;
    @FXML private Label name2;
    @FXML private Label level;
    @FXML private Label characterClass;
    @FXML private Label expansion;

    @FXML private RadioButton noTitle;
    @FXML private RadioButton normalComplete;
    @FXML private RadioButton nightmareComplete;
    @FXML private RadioButton hellComplete;
    @FXML private CheckBox isExpansion;
    @FXML private CheckBox isHardcore;
    @FXML private CheckBox isDead;
    @FXML private TextField name;

    private SkillsController skillsController;
    @FXML private StatsController statsController;

    private Difficult difficult;
    private Status status;
    private Map<Difficult, RadioButton> titlesToButtonLink;
    private List<Title> titles;

    private MetaBlock metaBlock;

    public void setSkillController(SkillsController skillsController) {
        this.skillsController = skillsController;
    }

    public void initialize() {
/*        title.textProperty().bind(character.titleProperty());
        name2.textProperty().bind(character.nameProperty());
        level.textProperty().bind(character.levelProperty());
        characterClass.textProperty().bind(character.classProperty());
        expansion.textProperty().bind(character.expansionProperty());*/
        setRulesForName();
        name.textProperty()
                .addListener((observable, oldValue, newValue) -> character.setNameValue(newValue));
        isExpansion.selectedProperty()
                .addListener((observable, oldValue, newValue) -> character.setExpansion(isExpansion.isSelected()));
        titlesToButtonLink = new HashMap<>();
        titlesToButtonLink.put(Difficult.NORMAL, noTitle);
        titlesToButtonLink.put(Difficult.NIGHTMARE, normalComplete);
        titlesToButtonLink.put(Difficult.HELL, nightmareComplete);
        titlesToButtonLink.put(Difficult.COMPLETE, hellComplete);
    }

    @Override
    public void setCharacter(Character character) {
        super.setCharacter(character);
        statsController.setCharacter(character);
    }

    @Override
    public void loadCharacter() throws ValidatorException {
        statsController.loadCharacter();
        metaBlock = character.getMetaBlock();
        difficult = null;
        changeTitleList();
        status = metaBlock.getStatus();
        Validator.checkName(metaBlock.getName());
        name.setText(metaBlock.getName());
        changeTitleList();
        isExpansion.setSelected(status.isExpansion());
        isHardcore.setSelected(status.isHardcore());
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(status.isDead());
    }

    @Override
    public void saveCharacter() throws ValidatorException {
        statsController.saveCharacter();
        metaBlock.setName(name.getText());
        Validator.checkName(metaBlock.getName());
        setStatus();
        metaBlock.setTitle(getSelectedTitle());
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
        character.getMetaBlock().setCharacterClass(characterClass);
        character.setCharacterClass(StringUtils.capitalize(characterClass.name().toLowerCase()));
        changeTitleList();
        //TODO not use load character use "updateSkills" method
        skillsController.loadCharacter();
    }

    private void setRulesForName() {
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("^[a-zA-Z_\\-]+$") || newValue.equals("")) {
                name.setText(newValue);
            } else {
                name.setText(oldValue);
            }
            if (newValue.length() > 15)
                name.setText(newValue.substring(0, 15));

        });
    }

    private void uncheckAllTitles() {
        titlesToButtonLink.values().forEach(t -> t.setSelected(false));
    }

    public void changeTitleList() {
        titles = Title.getTitlesForCharacter(character);
        normalComplete.setText(titles.get(1).getName());
        nightmareComplete.setText(titles.get(2).getName());
        hellComplete.setText(titles.get(3).getName());
        setTitle();
    }

    public void pickNoTitle() {
        difficult = Difficult.NORMAL;
        setTitle();
    }

    public void pickNormalTitle() {
        difficult = Difficult.NIGHTMARE;
        setTitle();
    }

    public void pickNightmareTitle() {
        difficult = Difficult.HELL;
        setTitle();
    }

    public void pickHellTitle() {
        difficult = Difficult.COMPLETE;
        setTitle();
    }

    private void setTitle() {
        uncheckAllTitles();
        if (difficult == null) {
            difficult = character.getMetaBlock().getTitle().getDifficult();
        }
        titlesToButtonLink.get(difficult).setSelected(true);
        character.setTitleValue(getSelectedTitle().getName());
    }


    private Title getSelectedTitle() {
        return titles.stream()
                .filter(t -> t.getDifficult() == difficult)
                .findFirst()
                .orElse(titles.get(0));
    }

    private void setStatus() {
        status.setExpansion(isExpansion.isSelected());
        status.setHardcore(isHardcore.isSelected());
        status.setDead(isDead.isSelected());
        metaBlock.setStatus(status);
    }

    public void onChangeExpansion() {
        setStatus();
        changeTitleList();
    }

    public void onChangeMode() {
        setStatus();
        changeTitleList();
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(false);
        changeTitleList();
    }

    public void onChangeDead() {

    }
}
