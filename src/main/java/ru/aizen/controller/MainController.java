package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class MainController {
    @FXML private Button restore;
    @FXML private Button save;
    @FXML private TabPane editorTabs;
    private String folder;
    private Path path;

    private Character character;
    private EditorController editorController;
    private HexEditorController hexEditorController;

    @Autowired
    public void initialize(Character character, EditorController editorController, HexEditorController hexEditorController) {
        folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
        this.character = character;
        this.editorController = editorController;
        this.hexEditorController = hexEditorController;
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
        editorTabs.getTabs().forEach(tab -> tab.setDisable(false));
        save.setDisable(false);
        restore.setDisable(false);
        character.backup();
    }

    @FXML
    private void onSaveClick() {
        try {
            editorController.saveCharacter();
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
