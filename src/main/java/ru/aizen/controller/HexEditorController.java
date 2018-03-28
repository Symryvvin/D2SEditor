package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.aizen.domain.util.BinHexUtils;

public class HexEditorController extends AbstractController{
    @FXML private TextArea hexCodeInput;
    @FXML private TextArea hexCodeOutput;
    
    private void loadHexData(){
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(character.getCharacterData().getBytes()));
    }

    public void setOutputData(byte[] output) {
        hexCodeOutput.setText(BinHexUtils.getFormattedHexString(output));
    }

    public void clearAll() {
        hexCodeInput.clear();
        hexCodeOutput.clear();
    }

    public TextArea getHexCodeInput() {
        return hexCodeInput;
    }

    @Override
    protected void loadCharacter() {
        loadHexData();
    }
}
