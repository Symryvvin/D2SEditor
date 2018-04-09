package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import ru.aizen.control.SkillControl;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillPage;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.data.CSVLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SkillsController extends AbstractController {
    @FXML private Label firstPageName;
    @FXML private GridPane firstPage;
    @FXML private Label secondPageName;
    @FXML private GridPane secondPage;
    @FXML private Label thirdPageName;
    @FXML private GridPane thirdPage;

    private List<SkillControl> skillControls;
    private SkillsBlock skillsBlock;

    public SkillsController(Character character) {
        super(character);
    }

    public void initialize() {
        skillControls = new ArrayList<>();
    }

    @Override
    public void loadCharacter() {
        skillsBlock = character.getSkillsBlock();
        skillControls.clear();
        firstPage.getChildren().clear();
        secondPage.getChildren().clear();
        thirdPage.getChildren().clear();
        List<SkillPage> pages = CSVLoader.pages()
                .stream()
                .filter(skillPage -> skillPage.getCharacterClass() == character.getCharacterClass())
                .collect(Collectors.toList());
        List<Skill> skills = addValues(skillsBlock.getSkills());
        firstPageName.setText(pages.get(0).getName());
        secondPageName.setText(pages.get(1).getName());
        thirdPageName.setText(pages.get(2).getName());
        for (Skill skill : skills) {
            SkillControl control = new SkillControl();
            control.setName(skill.getName());
            control.setImageByPath("icons/skills/" + character.getCharacterClass().getName().toLowerCase() + skill.getImagePath());
            control.setValue(String.valueOf(skill.getValue()));
            skillControls.add(control);
            if (skill.getPage() == 1) {
                firstPage.add(control, skill.getColumn(), skill.getRow());
            }
            if (skill.getPage() == 2) {
                secondPage.add(control, skill.getColumn(), skill.getRow());
            }
            if (skill.getPage() == 3) {
                thirdPage.add(control, skill.getColumn(), skill.getRow());
            }
        }
    }

    private List<Skill> addValues(List<Skill> values) {
        List<Skill> data = CSVLoader.skills()
                .stream()
                .filter(skill -> skill.getCharacterClass() == character.getCharacterClass())
                .collect(Collectors.toList());
        for (int i = 0; i < data.size(); i++) {
            data.get(i).mergeValue(values.get(i));
        }
        return data;
    }

    private List<Skill> getValuesFromControl(List<SkillControl> values) {
        List<Skill> data = CSVLoader.skills()
                .stream()
                .filter(skill -> skill.getCharacterClass() == character.getCharacterClass())
                .collect(Collectors.toList());
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setValue(Integer.parseInt(values.get(i).getValue().getText()));
        }
        return data;
    }

    @Override
    public void saveCharacter() {
        List<Skill> skills = getValuesFromControl(skillControls);
        skillsBlock.setSkills(skills);
    }
}