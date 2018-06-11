package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.app.control.SkillControl;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.character.entity.Skill;
import ru.aizen.domain.character.entity.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SkillsController extends BaseController {
    @FXML private Label firstPageName;
    @FXML private GridPane firstPage;
    @FXML private Label secondPageName;
    @FXML private GridPane secondPage;
    @FXML private Label thirdPageName;
    @FXML private GridPane thirdPage;

    private List<SkillControl> skillControls;
    private SkillsBlock skillsBlock;

    private CharacterClass characterClass;

    @Autowired
    public SkillsController(Character character) {
        super(character);
    }

    public void initialize() {
        skillControls = new ArrayList<>();
    }

    @Override
    public void loadCharacter() {
        skillsBlock = character.getSkillsBlock();
        characterClass = character.getMetaBlock().getCharacterClass();
        clear();
        Map<String, List<Skill>> skills = addValues(skillsBlock.getSkillValues());
        List<String> pages = skills.keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        firstPageName.setText(pages.get(0));
        addToPage(skills.get(pages.get(0)), firstPage);
        secondPageName.setText(pages.get(1));
        addToPage(skills.get(pages.get(1)), secondPage);
        thirdPageName.setText(pages.get(2));
        addToPage(skills.get(pages.get(2)), thirdPage);
    }

    private void clear() {
        skillControls.clear();
        firstPage.getChildren().clear();
        secondPage.getChildren().clear();
        thirdPage.getChildren().clear();
    }

    private Map<String, List<Skill>> addValues(Map<Integer, Byte> values) {
        List<Skill> skills = Skills.getByClass(characterClass);
        for (Skill skill : skills) {
            byte value = values.get(skill.getOrder());
            skill.setValue(value);
        }
        return skills.stream().collect(Collectors.groupingBy(Skill::getPage));
    }

    private void addToPage(List<Skill> skills, GridPane page) {
        for (Skill skill : skills) {
            SkillControl control = new SkillControl();
            control.setName(skill.getName());
            control.setImageByPath(skill.getImagePath());
            control.setValue(String.valueOf(skill.getValue()));
            control.setSkillOrder(skill.getOrder());
            skillControls.add(control);
            page.add(control, skill.getColumn(), skill.getRow());
        }
    }

    @Override
    public void saveCharacter() {
        skillsBlock.setSkillValues(
                skillControls.stream()
                        .collect(Collectors.toMap(SkillControl::getSkillOrder, SkillControl::getValue)));
    }
}