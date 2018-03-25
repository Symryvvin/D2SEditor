package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.aizen.domain.attribute.Attributes;

public class StatsController {
    @FXML private Label strLabel;
    @FXML private  Label dexLabel;
    @FXML private  Label vitLabel;
    @FXML private  Label intLabel;

    public void initialize(){
        strLabel.setText(Attributes.STRENGTH);
        dexLabel.setText(Attributes.DEXTERITY);
        vitLabel.setText(Attributes.VITALITY);
        intLabel.setText(Attributes.ENERGY);
    }
}
