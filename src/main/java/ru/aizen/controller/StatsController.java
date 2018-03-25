package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.aizen.domain.attribute.Attributes;

public class StatsController {
    private Attributes attributes;

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

    public void initialize() {
        strLabel.setText(Attributes.STRENGTH);
        dexLabel.setText(Attributes.DEXTERITY);
        vitLabel.setText(Attributes.VITALITY);
        intLabel.setText(Attributes.ENERGY);
    }

    public void loadCharacterStats(Attributes attributes) {
        this.attributes = attributes;
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
