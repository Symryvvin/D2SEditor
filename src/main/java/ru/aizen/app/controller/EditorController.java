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

    public void selectTab(int index) {
        Tab tab = editorTabs.getTabs().get(index);
        if (!tab.isDisable())
            editorTabs.getSelectionModel().select(index);
    }
}
