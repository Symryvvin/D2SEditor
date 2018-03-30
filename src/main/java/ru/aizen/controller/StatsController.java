package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Title;

public class StatsController extends AbstractController {
    @FXML private ComboBox<Title> title;
    @FXML private TextField name;
    @FXML private ComboBox<CharacterClass> characterClass;
    @FXML private Label strLabel;
    @FXML private Label dexLabel;
    @FXML private Label vitLabel;
    @FXML private Label intLabel;

    @FXML private TextField strength;
    @FXML private TextField dexterity;
    @FXML private TextField vitality;
    @FXML private TextField energy;
    @FXML private TextField level;
    @FXML private TextField experience;

    private AttributesBlock attributesBlock;

    public void initialize() {
        initTitle();
        initCharacterClass();
        strLabel.setText(AttributesBlock.STRENGTH);
        dexLabel.setText(AttributesBlock.DEXTERITY);
        vitLabel.setText(AttributesBlock.VITALITY);
        intLabel.setText(AttributesBlock.ENERGY);
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
    }

    private String getAttributeValue(String name) {
        if (attributesBlock.containsKey(name))
            return attributesBlock.get(name).toString();
        return "0";
    }


}
