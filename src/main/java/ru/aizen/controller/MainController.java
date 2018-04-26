package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class MainController {
    @Value("${save.path}")
    private String folder;

    @FXML private Button restore;
    @FXML private Button save;
    @FXML private TabPane editorTabs;

    private Path path;

    private final Character character;
    private final EditorController editorController;
    private final HexEditorController hexEditorController;

    @Autowired
    public MainController(Character character,
                          EditorController editorController,
                          HexEditorController hexEditorController) {
        this.character = character;
        this.editorController = editorController;
        this.hexEditorController = hexEditorController;
    }

    public void initialize() {
        if (folder.equals("default"))
            folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFile() throws Exception {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRestoreClick() throws Exception {
        character.restore();
        hexEditorController.clearAll();
        openFile();
    }
}
