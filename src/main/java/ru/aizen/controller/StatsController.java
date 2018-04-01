package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import ru.aizen.control.NumericField;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.character.block.AttributesBlock;

import java.util.List;
import java.util.stream.Collectors;

public class StatsController extends AbstractController {
    @FXML private SkillsController skillsController;

    @FXML private CheckBox isExpansion;
    @FXML private CheckBox isHardcore;
    @FXML private CheckBox isDead;
    private Title.Difficult difficult;
    @FXML private ComboBox<Title> title;
    @FXML private TextField name;
    @FXML private ComboBox<CharacterClass> characterClass;
    @FXML private NumericField hp;
    @FXML private NumericField mp;
    @FXML private NumericField sp;
    @FXML private NumericField statPoints;
    @FXML private NumericField skillPoints;
    @FXML private NumericField gold;
    @FXML private NumericField goldInStash;
    @FXML private NumericField strength;
    @FXML private NumericField dexterity;
    @FXML private NumericField vitality;
    @FXML private NumericField energy;
    @FXML private NumericField level;
    @FXML private NumericField experience;

    private AttributesBlock attributesBlock;

    public void initialize() {
        initTitle();
        initCharacterClass();
    }

    public void setCharacter(Character character) {
        super.setCharacter(character);
        skillsController.setCharacter(character);
    }

    private void initCharacterClass() {
        characterClass.getItems().addAll(
                new ObservableListWrapper<>(
                        CharacterClass.getCharacterClassList()));
        characterClass.setConverter(new StringConverter<CharacterClass>() {
            @Override
            public String toString(CharacterClass object) {
                return object.getName();
            }

            @Override
            public CharacterClass fromString(String string) {
                return null;
            }
        });
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

    @Override
    protected void loadCharacter() {
        loadCharacterStats();
        skillsController.loadCharacter();
    }

    @Override
    public void saveCharacter() {
        skillsController.saveCharacter();
        attributesBlock.put(AttributesBlock.STRENGTH, Long.parseLong(strength.getText()));
        attributesBlock.put(AttributesBlock.DEXTERITY, Long.parseLong(dexterity.getText()));
        attributesBlock.put(AttributesBlock.VITALITY, Long.parseLong(vitality.getText()));
        attributesBlock.put(AttributesBlock.ENERGY, Long.parseLong(energy.getText()));
        attributesBlock.put(AttributesBlock.LEVEL, Long.parseLong(level.getText()));
        attributesBlock.put(AttributesBlock.EXPERIENCE, Long.parseLong(experience.getText()));
        attributesBlock.put(AttributesBlock.MAX_HP, Long.parseLong(hp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MAX_MP, Long.parseLong(mp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MAX_SP, Long.parseLong(sp.getText()) * 256);
        attributesBlock.put(AttributesBlock.HP, Long.parseLong(hp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MP, Long.parseLong(mp.getText()) * 256);
        attributesBlock.put(AttributesBlock.SP, Long.parseLong(sp.getText()) * 256);
        attributesBlock.put(AttributesBlock.LEVEL, Long.parseLong(level.getText()));
        attributesBlock.put(AttributesBlock.SKILL_POINTS, Long.parseLong(skillPoints.getText()));
        attributesBlock.put(AttributesBlock.STAT_POINTS, Long.parseLong(statPoints.getText()));
        attributesBlock.put(AttributesBlock.GOLD, Long.parseLong(gold.getText()));
        attributesBlock.put(AttributesBlock.GOLD_IN_STASH, Long.parseLong(goldInStash.getText()));
        character.setAttributesBlock(attributesBlock);
        character.setName(name.getText());
        character.setTitle(title.getValue());
        character.setCharacterClass(characterClass.getValue());
        Status status = new Status();
        status.setExpansion(isExpansion.isSelected());
        status.setHardcore(isHardcore.isSelected());
        status.setDead(isDead.isSelected());
        character.setStatus(status);
    }

    private void loadCharacterStats() {
        name.setText(character.getName());
        title.setItems(
                new ObservableListWrapper<>(
                        Title.getTitleListFromStatus(character.getStatus())));
        title.getSelectionModel().select(character.getTitle());
        characterClass.getSelectionModel().select(character.getCharacterClass());
        attributesBlock = character.getAttributesBlock();
        strength.setText(getAttributeValue(AttributesBlock.STRENGTH));
        dexterity.setText(getAttributeValue(AttributesBlock.DEXTERITY));
        vitality.setText(getAttributeValue(AttributesBlock.VITALITY));
        energy.setText(getAttributeValue(AttributesBlock.ENERGY));
        level.setText(getAttributeValue(AttributesBlock.LEVEL));
        experience.setText(getAttributeValue(AttributesBlock.EXPERIENCE));
        hp.setText(getAttributeValue(AttributesBlock.MAX_HP, true));
        mp.setText(getAttributeValue(AttributesBlock.MAX_MP, true));
        sp.setText(getAttributeValue(AttributesBlock.MAX_SP, true));
        statPoints.setText(getAttributeValue(AttributesBlock.STAT_POINTS));
        skillPoints.setText(getAttributeValue(AttributesBlock.SKILL_POINTS));
        //max value of gold depends of level temporary for 99 lvl
        gold.setMaxValue(990000);
        gold.setText(getAttributeValue(AttributesBlock.GOLD));
        //max value of gold in stash depends of level temporary for 99 lvl
        goldInStash.setMaxValue(2500000);
        goldInStash.setText(getAttributeValue(AttributesBlock.GOLD_IN_STASH));
        isExpansion.setSelected(character.getStatus().isExpansion());
        isHardcore.setSelected(character.getStatus().isHardcore());
        isDead.setDisable(!isHardcore.isSelected());
        isDead.setSelected(character.getStatus().isDead());
        difficult = character.getTitle().getDifficult();
    }

    private String getAttributeValue(String name) {
        return getAttributeValue(name, false);
    }

    private String getAttributeValue(String name, boolean divide) {
        if (attributesBlock.containsKey(name)) {
            if (divide)
                return String.valueOf(attributesBlock.get(name) / 256);
            else
                return String.valueOf(attributesBlock.get(name));
        }
        return "0";
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

    public void onChangeDead() {
    }


}
