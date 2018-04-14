package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.dao.CharacterDao;

@Component
public class EditorController extends AbstractController {
    @FXML private CheckBox isExpansion;
    @FXML private CheckBox isHardcore;
    @FXML private CheckBox isDead;
    @FXML private ComboBox<Title> title;
    @FXML private TextField name;

    private final StatsController statsController;
    private final SkillsController skillsController;
    private final CharacterDao characterDao;

    private Difficult difficult;
    private Status status;

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
        initTitle();
    }


    private void initTitle() {
        title.setConverter(new StringConverter<Title>() {
            @Override
            public String toString(Title object) {
                return object.getName();
            }

            @Override
            public Title fromString(String string) {
                return null;
            }
        });
    }

    public void changeTitleList() {
        title.setItems(characterDao.getTitleListByCharacterClassAndStatus(character));
        setTitle();
    }

    private void setTitle() {
        if (difficult == null) {
            difficult = characterDao.getCurrentDifficult(character);
        }
        for (Title title : this.title.getItems()) {
            if (title.getDifficult() == difficult) {
                this.title.getSelectionModel().select(title);
            }
        }
    }

    public void loadCharacter() {
        difficult = null;
        status = character.getStatus();
        statsController.loadCharacter();
        skillsController.loadCharacter();
        name.setText(character.getName());
        changeTitleList();
        isExpansion.setSelected(status.isExpansion());
        isHardcore.setSelected(status.isHardcore());
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(status.isDead());
    }

    @Override
    public void saveCharacter() {
        statsController.saveCharacter();
        character.setName(name.getText());
        setStatus();
        character.setTitle(characterDao.getTitleValue(status, difficult));

    }

    private void setStatus() {
        status.setExpansion(isExpansion.isSelected());
        status.setHardcore(isHardcore.isSelected());
        status.setDead(isDead.isSelected());
        character.setStatus(status);
    }

    public void onChangeTitle() {
        Title selected = title.getSelectionModel().getSelectedItem();
        if (selected != null) {
            difficult = selected.getDifficult();
            setTitle();
        }
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
