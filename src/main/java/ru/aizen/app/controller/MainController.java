package ru.aizen.app.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aizen.app.InputCombination;
import ru.aizen.app.stage.Alerts;
import ru.aizen.app.stage.BackupManagerModalStage;
import ru.aizen.app.stage.HexEditorModalStage;
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

    @FXML private Button options;
    @FXML private Button backup;
    @FXML private Button open;
    @FXML private Button revert;
    @FXML private Button save;

    // Menu buttons
    @FXML private MenuItem saveMenu;
    @FXML private MenuItem openMenu;
    @FXML private MenuItem revertMenu;
    @FXML private MenuItem exitMenu;
    @FXML private MenuItem optionsMenu;
    @FXML private MenuItem backupManagerMenu;
    @FXML private MenuItem hexEditorMenu;
    @FXML private MenuItem statsTabMenu;
    @FXML private MenuItem skillsTabMenu;
    @FXML private MenuItem inventoryTabMenu;
    @FXML private MenuItem waypointsTabMenu;
    @FXML private MenuItem questsTabMenu;

    private Path path;
    private boolean isBackup = true;

    private final Character character;
    private final EditorController editorController;

    private HexEditorModalStage hexStage;
    private HexEditorController hexEditorController;
    private BackupManagerModalStage backupStage;
    private BackupController backupController;

    @Autowired
    public MainController(Character character,
                          EditorController editorController) {
        this.character = character;
        this.editorController = editorController;
    }

    public void initialize() throws IOException {
        backupStage = new BackupManagerModalStage();
        backupController = backupStage.getController();
        hexStage = new HexEditorModalStage();
        hexEditorController = hexStage.getController();
        initializeMenuKeyCodes();
        initializeButtonGraphics();
        if (folder.equals("default"))
            folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
    }

    private void initializeMenuKeyCodes() {
        openMenu.setAccelerator(InputCombination.OPEN.get());
        saveMenu.setAccelerator(InputCombination.SAVE.get());
        revertMenu.setAccelerator(InputCombination.REVERT.get());
        exitMenu.setAccelerator(InputCombination.EXIT.get());
        optionsMenu.setAccelerator(InputCombination.OPTIONS.get());
        hexEditorMenu.setAccelerator(InputCombination.HEX.get());
        backupManagerMenu.setAccelerator(InputCombination.BAK_MANAGER.get());
        statsTabMenu.setAccelerator(InputCombination.STATS.get());
        skillsTabMenu.setAccelerator(InputCombination.SKILLS.get());
        inventoryTabMenu.setAccelerator(InputCombination.INVENTORY.get());
        waypointsTabMenu.setAccelerator(InputCombination.WAYPOINTS.get());
        questsTabMenu.setAccelerator(InputCombination.QUESTS.get());
    }

    private void initializeButtonGraphics() {
        open.setGraphic(new ImageView(new Image("/icons/app/open.png")));
        save.setGraphic(new ImageView(new Image("/icons/app/save.png")));
        revert.setGraphic(new ImageView(new Image("/icons/app/revert.png")));
        backup.setGraphic(new ImageView(new Image("/icons/app/backup.png")));
        options.setGraphic(new ImageView(new Image("/icons/app/options.png")));
    }

    @FXML
    private void onOpenClick() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Resource File");
            chooser.setInitialDirectory(new File(folder));
            chooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Diablo 2 Save", "*.d2s"));
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
        loadCharacter();
        editorController.loadCharacter();
        file.setText(path.toString());
    }

    private void loadCharacter() throws IOException, ValidatorException {
        character.load(path);
        if (isBackup) {
            backupController.createBackup(path);
        }
        save.setDisable(false);
        backup.setDisable(false);
        revert.setDisable(false);
        saveMenu.setDisable(false);
        revertMenu.setDisable(false);
        backupManagerMenu.setDisable(false);
    }

    @FXML
    private void onSaveClick() {
        try {
            editorController.saveCharacter();
            byte[] save = character.save(path);
            changeName();
            hexEditorController.setOutputData(save);
        } catch (IOException | ValidatorException e) {
            Alerts.showError(e).show();
        }
    }

    private void changeName() throws IOException, ValidatorException {
        String fileName = path.getFileName().toString();
        if (character.isNameChanged(fileName)) {
            path = backupController.renameFiles(character.getMetaBlock().getName());
            openFile();
        }
    }

    @FXML
    private void onRevertClick() {
        try {
            backupController.revert();
            hexEditorController.loadHex();
            isBackup = false;
            openFile();
            isBackup = true;
        } catch (ValidatorException e) {
            Alerts.showError(e).show();
        } catch (IOException e) {
            Alerts.showMessage("Backup file not exist").show();
        }

    }

    @FXML
    private void onBackupClick() {
        backupStage.showBackup();
    }

    @FXML
    private void onOptionsClick() {
        //TODO add as closeable tab?
    }

    @FXML
    private void onHexEditorClick() {
        hexEditorController.load(character);
        hexEditorController.loadHex();
        hexStage.showHex();
    }

    @FXML
    private void OnSelectStats() {
        editorController.selectTab(0);
    }

    @FXML
    private void OnSelectSkills() {
        editorController.selectTab(1);
    }

    @FXML
    private void OnSelectWaypoints() {
        editorController.selectTab(2);
    }

    @FXML
    private void OnSelectQuests() {
        editorController.selectTab(3);
    }


    @FXML
    private void OnSelectInventory() {
        editorController.selectTab(4);
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }


}
