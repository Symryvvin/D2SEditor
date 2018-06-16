package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

@Component
public class EditorController extends BaseController {
    private final MetaInfoController metaInfoController;
    private final StatsController statsController;
    private final SkillsController skillsController;
    private final WaypointsController waypointsController;
    private final QuestsController questsController;
    @FXML private TabPane editorTabs;
    @FXML private Tab hexEditor;
    @FXML private Tab backupManager;

    @Autowired
    public EditorController(Character character,
                            MetaInfoController metaInfoController,
                            StatsController statsController,
                            SkillsController skillsController,
                            WaypointsController waypointsController,
                            QuestsController questsController) {
        super(character);
        this.metaInfoController = metaInfoController;
        this.statsController = statsController;
        this.skillsController = skillsController;
        this.waypointsController = waypointsController;
        this.questsController = questsController;
    }

    public void initialize() {
        initializeCloseableTab();
    }

    private void initializeCloseableTab() {
        editorTabs.getTabs().remove(hexEditor);
        editorTabs.getTabs().remove(backupManager);
        hexEditor.setOnClosed(event -> {
            hexEditor.setDisable(true);
            editorTabs.getTabs().remove(hexEditor);
        });
        backupManager.setOnClosed(event -> {
            backupManager.setDisable(true);
            editorTabs.getTabs().remove(backupManager);
        });
    }

    public void loadCharacter() throws ValidatorException {
        editorTabs.getTabs().forEach(tab -> tab.setDisable(false));
        metaInfoController.loadCharacter();
        statsController.loadCharacter();
        skillsController.loadCharacter();
        waypointsController.loadCharacter();
        questsController.loadCharacter();
    }

    @Override
    public void saveCharacter() throws ValidatorException {
        metaInfoController.saveCharacter();
        statsController.saveCharacter();
        skillsController.saveCharacter();
        waypointsController.saveCharacter();
        questsController.saveCharacter();
    }

    public void addAndSelectBackup() {
        addAndSelect(backupManager);
    }

    public void addAndSelectHex() {
        addAndSelect(hexEditor);
    }

    private void addAndSelect(Tab tab) {
        if (tab != null && tab.isDisable()) {
            editorTabs.getTabs().add(tab);
            tab.setDisable(false);
        }
        editorTabs.getSelectionModel().select(tab);
    }

    public void selectTab(int index) {
        Tab tab = editorTabs.getTabs().get(index);
        if (!tab.isDisable())
            editorTabs.getSelectionModel().select(index);
    }
}
