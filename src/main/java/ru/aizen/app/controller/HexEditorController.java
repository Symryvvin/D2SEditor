package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.util.BinHexUtils;

public class HexEditorController {
    private Character character;

    @FXML private TextArea hexCodeInput;
    @FXML private TextArea hexCodeOutput;

    private void loadHexData() {
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(character.getBlockReader().getBytes()));
    }

    public void setOutputData(byte[] output) {
        hexCodeOutput.setText(BinHexUtils.getFormattedHexString(output));
    }

    private void clearAll() {
        hexCodeInput.clear();
        hexCodeOutput.clear();
    }

    public TextArea getHexCodeInput() {
        return hexCodeInput;
    }

    public void load(Character character) {
        this.character = character;
    }

    public void loadHex() {
        clearAll();
        loadHexData();
    }
}
