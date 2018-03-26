package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.aizen.domain.Character;
import ru.aizen.domain.util.BinHexUtils;

public class HexEditorController {
    @FXML private Label checkSumInput;
    @FXML private TextArea hexCodeInput;
    @FXML private Label checkSumOutput;
    @FXML private TextArea hexCodeOutput;
    
    private Character character;

    public void loadHexData(){
        checkSumInput.setText("Checksum: " + character.getData().getCheckSum());
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(character.getData().getData()));
    }

    public void setOutputData(byte[] output) {
        checkSumOutput.setText("Checksum: " + character.getData().getCheckSum());
        hexCodeOutput.setText(BinHexUtils.getFormattedHexString(output));
    }

    public void clearAll() {
        hexCodeInput.clear();
        hexCodeOutput.clear();
        checkSumInput.setText("");
        checkSumOutput.setText("");
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public TextArea getHexCodeInput() {
        return hexCodeInput;
    }
}
