package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

public class EditorController extends BaseController {
    @FXML private MetaInfoController metaInfoController;
    @FXML private SkillsController skillsController;
    @FXML private WaypointsController waypointsController;
    @FXML private QuestsController questsController;
    @FXML private TabPane editorTabs;

    public void setCharacter(Character character) {
        super.setCharacter(character);
        metaInfoController.setCharacter(character);
        metaInfoController.setSkillController(skillsController);
        skillsController.setCharacter(character);
        waypointsController.setCharacter(character);
        questsController.setCharacter(character);
    }

    public void loadCharacter() throws ValidatorException {
        editorTabs.getTabs().forEach(tab -> tab.setDisable(false));
        metaInfoController.loadCharacter();
        skillsController.loadCharacter();
        waypointsController.loadCharacter();
        questsController.loadCharacter();
    }

    @Override
    public void saveCharacter() throws ValidatorException {
        metaInfoController.saveCharacter();
        skillsController.saveCharacter();
        waypointsController.saveCharacter();
        questsController.saveCharacter();
    }

    public void selectTab(int index) {
        Tab tab = editorTabs.getTabs().get(index);
        if (!tab.isDisable())
            editorTabs.getSelectionModel().select(index);
    }
}
