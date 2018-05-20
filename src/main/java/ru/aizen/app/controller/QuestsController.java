package ru.aizen.app.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import ru.aizen.app.control.QuestControl;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.block.QuestsBlock;
import ru.aizen.domain.character.entity.Act;
import ru.aizen.domain.character.entity.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestsController extends BaseController {
    @FXML private ComboBox<Difficult> difficulties;
    @FXML private VBox act1;
    @FXML private VBox act2;
    @FXML private VBox act3;
    @FXML private VBox act4;
    @FXML private VBox act5;

    private Difficult difficult;
    private QuestsBlock questsBlock;

    public QuestsController(Character character) {
        super(character);
    }

    public void initialize() {
        difficulties.setItems(new ObservableListWrapper<>(
                Arrays.asList(Difficult.NORMAL, Difficult.NIGHTMARE, Difficult.HELL)));
    }

    @Override
    protected void loadCharacter() {
        questsBlock = character.getQuestBlock();
        difficult = character.getMetaBlock().getTitle().getDifficult();
        if (difficult == Difficult.COMPLETE)
            difficult = Difficult.HELL;
        loadQuests();
        difficulties.getSelectionModel().select(difficult);
    }

    private void loadQuests() {
        mergeDataToLoad(getQuestControls(act1), getQuestsForAct(Act.ACT1));
        mergeDataToLoad(getQuestControls(act2), getQuestsForAct(Act.ACT2));
        mergeDataToLoad(getQuestControls(act3), getQuestsForAct(Act.ACT3));
        mergeDataToLoad(getQuestControls(act4), getQuestsForAct(Act.ACT4));
        mergeDataToLoad(getQuestControls(act5), getQuestsForAct(Act.ACT5));
    }

    private void mergeDataToLoad(List<QuestControl> controls, List<Quest> quests) {
        Collections.sort(quests);
        for (int i = 0; i < quests.size(); i++) {
            QuestControl control = controls.get(i);
            Quest quest = quests.get(i);
            control.setName(quest.getName());
            control.setImage(quest.getImagePath());
            control.setIsCompleted(quest.isComplete());
            control.setOrder(quest.getOrder());
        }
    }

    private List<QuestControl> getQuestControls(VBox act) {
        List<QuestControl> result = new ArrayList<>();
        List<Node> nodes = act.getChildren();
        for (Node node : nodes) {
            if (node instanceof QuestControl) {
                result.add((QuestControl) node);
            }
        }
        return result;
    }

    private List<Quest> getQuestsForAct(Act act) {
        return questsBlock.getQuests(difficult)
                .stream()
                .filter(waypoint -> waypoint.getAct() == act)
                .collect(Collectors.toList());

    }

    @Override
    public void saveCharacter() {
        List<Quest> toSave = new ArrayList<>();
        toSave.addAll(mergeDataToSave(getQuestControls(act1), getQuestsForAct(Act.ACT1)));
        toSave.addAll(mergeDataToSave(getQuestControls(act2), getQuestsForAct(Act.ACT2)));
        toSave.addAll(mergeDataToSave(getQuestControls(act3), getQuestsForAct(Act.ACT3)));
        toSave.addAll(mergeDataToSave(getQuestControls(act4), getQuestsForAct(Act.ACT4)));
        toSave.addAll(mergeDataToSave(getQuestControls(act5), getQuestsForAct(Act.ACT5)));
        questsBlock.setQuests(toSave, difficult);
    }

    private List<Quest> mergeDataToSave(List<QuestControl> controls, List<Quest> quests) {
        Collections.sort(controls);
        for (int i = 0; i < quests.size(); i++) {
            quests.get(i).setComplete(controls.get(i).getIsCompleted());
        }
        return quests;
    }

    @FXML
    private void onChangeDifficult() {
        saveCharacter();
        difficult = difficulties.getSelectionModel().getSelectedItem();
        loadQuests();
    }
}
