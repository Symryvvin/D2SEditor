package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Title;

import java.util.stream.Collectors;

public class StatsController extends AbstractController{
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

    private Attributes attributes;

    public void initialize() {
        initTitle();
        initCharacterClass();
        strLabel.setText(Attributes.STRENGTH);
        dexLabel.setText(Attributes.DEXTERITY);
        vitLabel.setText(Attributes.VITALITY);
        intLabel.setText(Attributes.ENERGY);
    }

    private void initCharacterClass(){
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

    private void initTitle(){
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
        attributes = character.getAttributes();
        strength.setText(getAttributeValue(Attributes.STRENGTH));
        dexterity.setText(getAttributeValue(Attributes.DEXTERITY));
        vitality.setText(getAttributeValue(Attributes.VITALITY));
        energy.setText(getAttributeValue(Attributes.ENERGY));
        level.setText(getAttributeValue(Attributes.LEVEL));
        experience.setText(getAttributeValue(Attributes.EXPERIENCE));
    }

    private String getAttributeValue(String name) {
        return attributes.get(name).toString();
    }


}
