package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.character.Character;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MainController {

    private String folder;
    private Path path;
    private Character character;

    @FXML private EditorController editorController;
    @FXML private HexEditorController hexEditorController;

    public void initialize() {
        folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
        character = new Character();
        editorController.setCharacter(character);
        hexEditorController.setCharacter(character);
    }

    @FXML
    private void onOpenClick() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Resource File");
            chooser.setInitialDirectory(new File(folder));
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                path = file.toPath();
                openFile();
            }
        } catch (DecoderException | IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile() throws DecoderException, IOException {
        hexEditorController.clearAll();
        loadCharacter();
        hexEditorController.loadCharacter();
        editorController.loadCharacter();
    }

    private void loadCharacter() throws DecoderException, IOException {
        character.load(path);
        character.backup();
    }

    @FXML
    private void onSaveClick() {
        try {
            character.save();
            hexEditorController.setOutputData(character.getCharacterData().getBytes());
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRestoreClick() throws DecoderException, IOException {
        character.restore();
        hexEditorController.clearAll();
        openFile();
    }
}
