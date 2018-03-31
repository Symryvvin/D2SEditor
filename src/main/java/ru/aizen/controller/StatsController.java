package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.character.block.AttributesBlock;

public class StatsController extends AbstractController {
    @FXML private ComboBox<Title> title;
    @FXML private TextField name;
    @FXML private ComboBox<CharacterClass> characterClass;
    @FXML private TextField hp;
    @FXML private TextField mp;
    @FXML private TextField sp;

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

    @Override
    public void saveCharacter() {
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


}
