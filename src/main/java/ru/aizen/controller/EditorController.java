package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EditorController extends AbstractController{
    @FXML private CheckBox isExpansion;
    @FXML private CheckBox isHardcore;
    @FXML private CheckBox isDead;
    private Title.Difficult difficult;
    @FXML private ComboBox<Title> title;
    @FXML private TextField name;

    private final StatsController statsController;
    private final SkillsController skillsController;

    @Autowired
    public EditorController(Character character,
                            StatsController statsController,
                            SkillsController skillsController) {
        super(character);
        this.statsController = statsController;
        this.skillsController = skillsController;
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

    public void loadCharacter() {
        statsController.loadCharacter();
        name.setText(character.getName());
        title.setItems(
                new ObservableListWrapper<>(
                        Title.getTitleListFromStatus(character.getStatus())));
        title.getSelectionModel().select(character.getTitle());
        isExpansion.setSelected(character.getStatus().isExpansion());
        isHardcore.setSelected(character.getStatus().isHardcore());
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(character.getStatus().isDead());
        difficult = character.getTitle().getDifficult();
    }

    @Override
    public void saveCharacter() {
        statsController.saveCharacter();
        character.setName(name.getText());
        character.setTitle(title.getValue());
        Status status = new Status();
        status.setExpansion(isExpansion.isSelected());
        status.setHardcore(isHardcore.isSelected());
        status.setDead(isDead.isSelected());
        character.setStatus(status);
    }

    public void onChangeTitle() {
        if (title.getSelectionModel().getSelectedItem() != null)
            difficult = title.getSelectionModel().getSelectedItem().getDifficult();
    }

    public void onChangeExpansion() {
        changeTitleList();
        title.getSelectionModel().select(Title.parse(difficult, title.getItems()));
    }

    public void onChangeMode() {
        changeTitleList();
        title.getSelectionModel().select(Title.parse(difficult, title.getItems()));
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(false);
        changeTitleList();
    }

    public void onChangeDead() {
    }

    private void changeTitleList() {
        List<Title> titles;
        if (isExpansion.isSelected()) {
            if (isHardcore.isSelected())
                titles = Title.expansionHardCoreSet().collect(Collectors.toList());
            else
                titles = Title.expansionSet().collect(Collectors.toList());
        } else {
            if (isHardcore.isSelected())
                titles = Title.vanillaHardcoreSet().collect(Collectors.toList());
            else
                titles = Title.vanillaSet().collect(Collectors.toList());
        }
        title.setItems(new ObservableListWrapper<>(titles));
    }

    public void pickAssassin() {
        character.setCharacterClass(CharacterClass.ASSASSIN);
        skillsController.loadCharacter();
    }

    public void pickAmazon() {
        character.setCharacterClass(CharacterClass.AMAZON);
        skillsController.loadCharacter();
    }

    public void pickNecromancer() {
        character.setCharacterClass(CharacterClass.NECROMANCER);
        skillsController.loadCharacter();
    }

    public void pickBarbarian() {
        character.setCharacterClass(CharacterClass.BARBARIAN);
        skillsController.loadCharacter();
    }

    public void pickPaladin() {
        character.setCharacterClass(CharacterClass.PALADIN);
        skillsController.loadCharacter();
    }

    public void pickSorceress() {
        character.setCharacterClass(CharacterClass.SORCERESS);
        skillsController.loadCharacter();
    }

    public void pickDruid() {
        character.setCharacterClass(CharacterClass.DRUID);
        skillsController.loadCharacter();
    }
}
