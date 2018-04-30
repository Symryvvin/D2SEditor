package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.dao.CharacterDao;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EditorController extends AbstractController {
    @FXML private RadioButton start;
    @FXML private RadioButton normal;
    @FXML private RadioButton nightmare;
    @FXML private RadioButton hell;
    private List<RadioButton> titles;
    @FXML private CheckBox isExpansion;
    @FXML private CheckBox isHardcore;
    @FXML private CheckBox isDead;
    @FXML private TextField name;

    private final StatsController statsController;
    private final SkillsController skillsController;
    private final CharacterDao characterDao;

    private Difficult difficult;
    private Status status;
    private List<Title> titleList;

    @Autowired
    public EditorController(Character character,
                            StatsController statsController,
                            SkillsController skillsController,
                            CharacterDao characterDao) {
        super(character);
        this.statsController = statsController;
        this.skillsController = skillsController;
        this.characterDao = characterDao;
    }

    public void initialize() {
        setRulesForName();
        name.textProperty()
                .addListener((observable, oldValue, newValue) -> character.setNameValue(newValue));
        isExpansion.selectedProperty()
                .addListener((observable, oldValue, newValue) -> character.setExpansion(isExpansion.isSelected()));
        titles = new ArrayList<>();
        titles.addAll(Arrays.asList(start, normal, nightmare, hell));
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
        titles.forEach(t -> t.setSelected(false));
    }

    public void changeTitleList() {
        titleList = characterDao.getTitleListByCharacterClassAndStatus(character);
        normal.setText(titleList.get(1).getName());
        nightmare.setText(titleList.get(2).getName());
        hell.setText(titleList.get(3).getName());
        setTitle();
    }

    public void pickNoTitle() {
        difficult = Difficult.START;
        setTitle();
    }

    public void pickNormalTitle() {
        difficult = Difficult.NORMAL;
        setTitle();
    }

    public void pickNightmareTitle() {
        difficult = Difficult.NIGHTMARE;
        setTitle();
    }

    public void pickHellTitle() {
        difficult = Difficult.HELL;
        setTitle();
    }

    private void setTitle() {
        uncheckAllTitles();
        if (difficult == null) {
            difficult = characterDao.getCurrentDifficult(character);
        }
        titles.forEach(t -> {
            if (t.getId().equalsIgnoreCase(difficult.name())) {
                t.setSelected(true);
            }
        });
        String title = titles.stream()
                .filter(RadioButton::isSelected)
                .findFirst()
                .map(RadioButton::getText)
                .orElse("unknown");
        if (title.equals("No title"))
            character.setTitleValue("");
        else
            character.setTitleValue(title);
    }

    public void loadCharacter() throws ValidatorException {
        difficult = null;
        changeTitleList();
        status = character.getStatus();
        statsController.loadCharacter();
        skillsController.loadCharacter();
        Validator.checkName(character.getName());
        name.setText(character.getName());
        changeTitleList();
        isExpansion.setSelected(status.isExpansion());
        isHardcore.setSelected(status.isHardcore());
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(status.isDead());

    }

    @Override
    public void saveCharacter() throws ValidatorException {
        statsController.saveCharacter();
        character.setName(name.getText());
        Validator.checkName(character.getName());
        setStatus();
        character.setTitle(getSelectedTitle());
    }

    private Title getSelectedTitle() {
        return titleList.stream()
                .filter(t -> t.getDifficult() == difficult)
                .findFirst()
                .orElse(titleList.get(0));
    }

    private void setStatus() {
        status.setExpansion(isExpansion.isSelected());
        status.setHardcore(isHardcore.isSelected());
        status.setDead(isDead.isSelected());
        character.setStatus(status);
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
