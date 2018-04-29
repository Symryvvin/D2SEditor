package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.util.BinHexUtils;

@Component
public class HexEditorController extends AbstractController{
    @FXML private TextArea hexCodeInput;
    @FXML private TextArea hexCodeOutput;

    public HexEditorController(Character character) {
        super(character);
    }

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

    @Override
    public void saveCharacter() {
        //not for implementing now
    }
}