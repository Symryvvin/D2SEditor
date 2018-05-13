package ru.aizen.app.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.block.MapBlock;
import ru.aizen.domain.character.block.WaypointsBlock;
import ru.aizen.domain.character.entity.Act;
import ru.aizen.domain.character.entity.Waypoint;
import ru.aizen.domain.dao.WaypointDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WaypointsController extends BaseController {
    @FXML private ComboBox<Difficult> inDifficult;
    @FXML private ComboBox<String> inTown;
    @FXML private ComboBox<Difficult> difficulties;
    @FXML private VBox act1;
    @FXML private VBox act2;
    @FXML private VBox act3;
    @FXML private VBox act4;
    @FXML private VBox act5;

    private Difficult difficult;
    private WaypointsBlock waypointsBlock;
    private MapBlock mapBlock;

    private WaypointDao waypointDao;

    public WaypointsController(Character character, WaypointDao waypointDao) {
        super(character);
        this.waypointDao = waypointDao;
    }

    public void initialize() {
        difficulties.setItems(new ObservableListWrapper<>(
                Arrays.asList(Difficult.NORMAL, Difficult.NIGHTMARE, Difficult.HELL)));
        inDifficult.setItems(new ObservableListWrapper<>(
                Arrays.asList(Difficult.NORMAL, Difficult.NIGHTMARE, Difficult.HELL)));
        inTown.setItems(new ObservableListWrapper<>(waypointDao.getTowns()));
    }

    @Override
    protected void loadCharacter() {
        waypointsBlock = character.getWaypointsBlock();
        mapBlock = character.getMapBlock();
        difficult = character.getMetaBlock().getTitle().getDifficult();
        inDifficult.getSelectionModel().select(mapBlock.getActiveDifficult());
        inTown.getSelectionModel().select(mapBlock.getActiveTown());
        if (difficult == Difficult.COMPLETE)
            difficult = Difficult.HELL;
        loadWaypoints();
        difficulties.getSelectionModel().select(difficult);
    }

    private void loadWaypoints() {
        mergeDataToLoad(getWaypointCheckBoxes(act1), getWaypointsForAct(Act.ACT1));
        mergeDataToLoad(getWaypointCheckBoxes(act2), getWaypointsForAct(Act.ACT2));
        mergeDataToLoad(getWaypointCheckBoxes(act3), getWaypointsForAct(Act.ACT3));
        mergeDataToLoad(getWaypointCheckBoxes(act4), getWaypointsForAct(Act.ACT4));
        mergeDataToLoad(getWaypointCheckBoxes(act5), getWaypointsForAct(Act.ACT5));
    }

    private List<CheckBox> getWaypointCheckBoxes(VBox act) {
        List<CheckBox> result = new ArrayList<>();
        List<Node> nodes = act.getChildren();
        for (Node node : nodes) {
            if (node instanceof CheckBox) {
                result.add((CheckBox) node);
            }
        }
        return result;
    }

    private List<Waypoint> getWaypointsForAct(Act act) {
        return waypointsBlock.getWaypoints(difficult)
                .stream()
                .filter(waypoint -> waypoint.getAct() == act)
                .collect(Collectors.toList());
    }

    private void mergeDataToLoad(List<CheckBox> checkBoxes, List<Waypoint> waypoints) {
        for (int i = 0; i < waypoints.size(); i++) {
            checkBoxes.get(i).setText(waypoints.get(i).getName());
            checkBoxes.get(i).setSelected(waypoints.get(i).isActive());
        }
    }

    @Override
    public void saveCharacter() {
        List<Waypoint> toSave = new ArrayList<>();
        toSave.addAll(mergeDataToSave(getWaypointCheckBoxes(act1), getWaypointsForAct(Act.ACT1)));
        toSave.addAll(mergeDataToSave(getWaypointCheckBoxes(act2), getWaypointsForAct(Act.ACT2)));
        toSave.addAll(mergeDataToSave(getWaypointCheckBoxes(act3), getWaypointsForAct(Act.ACT3)));
        toSave.addAll(mergeDataToSave(getWaypointCheckBoxes(act4), getWaypointsForAct(Act.ACT4)));
        toSave.addAll(mergeDataToSave(getWaypointCheckBoxes(act5), getWaypointsForAct(Act.ACT5)));
        waypointsBlock.setWaypoints(toSave, difficult);
    }

    private List<Waypoint> mergeDataToSave(List<CheckBox> checkBoxes, List<Waypoint> waypoints) {
        for (int i = 0; i < waypoints.size(); i++) {
            waypoints.get(i).setActive(checkBoxes.get(i).isSelected());
        }
        return waypoints;
    }

    @FXML
    private void onCheckAll() {
        getWaypointCheckBoxes(act1).forEach(c -> c.setSelected(true));
        getWaypointCheckBoxes(act2).forEach(c -> c.setSelected(true));
        getWaypointCheckBoxes(act3).forEach(c -> c.setSelected(true));
        getWaypointCheckBoxes(act4).forEach(c -> c.setSelected(true));
        getWaypointCheckBoxes(act5).forEach(c -> c.setSelected(true));
    }

    @FXML
    private void onChangeDifficult() {
        saveCharacter();
        difficult = difficulties.getSelectionModel().getSelectedItem();
        loadWaypoints();
    }

    public void onChangeInDifficult() {
        mapBlock.setActiveDifficult(inDifficult.getValue());
    }

    public void onChangeInTown() {
        mapBlock.setActiveTown(inTown.getValue());
    }
}
