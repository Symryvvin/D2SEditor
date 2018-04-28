package ru.aizen.app.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aizen.app.InputCombination;
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

    @FXML private TabPane editorTabs;
    // Closeable tabs
    @FXML private Tab hexEditor;

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
        initializeCloseableTab();
        initializeMenuKeyCodes();
        initializeButtonGraphics();
        if (folder.equals("default"))
            folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
    }

    private void initializeCloseableTab() {
        //TODO implement hide closeable tabs on start
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
        revert.setDisable(false);
        saveMenu.setDisable(false);
        revertMenu.setDisable(false);
        if (isBackup) {
            character.backup();
            if (backupController != null)
                backupController.setData(character);
        }
    }

    @FXML
    private void onSaveClick() {
        try {
            editorController.saveCharacter();
            character.save();
            hexEditorController.setOutputData(character.getCharacterData().getBytes());
        } catch (IOException | ValidatorException e) {
            Alerts.showError(e).show();
        }
    }

    @FXML
    private void onRevertClick() {
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

    @FXML
    private void onBackupClick() {
        //TODO add as closeable tab?
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

    @FXML
    private void onOptionsClick() {
        //TODO add as closeable tab?
    }

    @FXML
    private void onHexEditorClick() {
        addAndSelect(hexEditor);
    }

    private void addAndSelect(Tab tab) {
        if (tab != null && !tab.isDisable()) {
            editorTabs.getTabs().add(tab);
            editorTabs.getSelectionModel().select(tab);
        }
    }

    @FXML
    private void OnSelectStats() {
        selectTab(0);
    }

    @FXML
    private void OnSelectSkills() {
        selectTab(1);
    }

    @FXML
    private void OnSelectWaypoints() {
        selectTab(2);
    }

    @FXML
    private void OnSelectQuests() {
        selectTab(3);
    }


    public void OnSelectInventory() {
        selectTab(4);
    }

    private void selectTab(int index) {
        Tab tab = editorTabs.getTabs().get(index);
        if (!tab.isDisable())
            editorTabs.getSelectionModel().select(index);
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }


}
