package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aizen.app.stage.Alerts;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class MainController {
    @Value("${save.path}")
    private String folder;

    @FXML private Label file;
    @FXML private Button backup;
    @FXML private Button open;
    @FXML private Button restore;
    @FXML private Button save;
    @FXML private TabPane editorTabs;

    private BackupController backupController;
    private Stage backupStage;

    private Path path;
    private boolean isBackup = true;

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
        initializeButtons();
        if (folder.equals("default"))
            folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
    }

    private void initializeButtons() {
        open.setGraphic(new ImageView(new Image("/icons/open.png")));
        save.setGraphic(new ImageView(new Image("/icons/save.png")));
        restore.setGraphic(new ImageView(new Image("/icons/restore.png")));
        backup.setGraphic(new ImageView(new Image("/icons/backup.png")));
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
        } catch (IOException | ValidatorException e) {
            Alerts.showError(e).show();
        }
    }

    private void openFile() throws IOException, ValidatorException {
        hexEditorController.clearAll();
        loadCharacter();
        hexEditorController.loadCharacter();
        editorController.loadCharacter();
        file.setText(character.getCharacterData().getInput().toString());
    }

    private void loadCharacter() throws IOException, ValidatorException {
        character.load(path);
        editorTabs.getTabs().forEach(tab -> tab.setDisable(false));
        save.setDisable(false);
        backup.setDisable(false);
        restore.setDisable(false);
    }

    @FXML
    private void onSaveClick() {
        try {
            if (isBackup) {
                character.backup();
                if (backupController != null)
                    backupController.setData(character);
            }
            editorController.saveCharacter();
            character.save();
            hexEditorController.setOutputData(character.getCharacterData().getBytes());
        } catch (IOException | ValidatorException e) {
            Alerts.showError(e).show();
        }
    }

    @FXML
    private void onRestoreClick() {
        try {
            character.restore();
            hexEditorController.clearAll();
            isBackup = false;
            openFile();
            isBackup = true;
        } catch (ValidatorException e) {
            Alerts.showError(e).show();
        } catch (IOException e) {
            Alerts.showMessage("Backup file " + character.getCharacterData().getLastBackup() + " not exist")
                    .show();
        }

    }

    public void onBackupClick() {
        try {
            if (backupStage == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/backup.fxml"));
                Parent root = loader.load();
                backupStage = new Stage(StageStyle.UTILITY);
                backupStage.setTitle("Backup Manager");
                backupStage.setAlwaysOnTop(true);
                backupStage.setScene(new Scene(root));
                backupController = loader.getController();
                backupController.setData(character);
                backupStage.show();
            } else {
                backupController.setData(character);
                backupStage.show();
            }
        } catch (IOException e) {
            Alerts.showError(e).show();
        }
    }
}
