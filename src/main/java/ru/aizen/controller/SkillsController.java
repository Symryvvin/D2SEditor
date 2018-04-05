package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import ru.aizen.control.SkillControl;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillPage;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.data.CSVLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkillsController extends AbstractController {
    @FXML private Tab firstTree;
    @FXML private Tab secondTree;
    @FXML private Tab thirdTree;

    private SkillsBlock skillsBlock;
    private Map<Integer, List<Skill>> skillsByPage;

    @Override
    protected void loadCharacter() {
        refresh();
    }

    public void refresh() {
        skillsBlock = character.getSkillsBlock();
        List<Skill> values = skillsBlock.getSkills();

        List<Skill> data = CSVLoader.skills()
                .stream()
                .filter(skill -> skill.getCharacterClass() == character.getCharacterClass())
                .collect(Collectors.toList());

        List<SkillPage> pages = CSVLoader.pages()
                .stream()
                .filter(skill -> skill.getCharacterClass() == character.getCharacterClass())
                .collect(Collectors.toList());

        skillsByPage = mergeSkillData(data, values).stream()
                .collect(Collectors.groupingBy(Skill::getPage));
        for (Map.Entry<Integer, List<Skill>> entry : skillsByPage.entrySet()) {
            int pageIndex = entry.getKey();
            List<Skill> skills = entry.getValue();
            switch (pageIndex) {
                case 1:
                    firstTree.setText(pages.get(0).getName());
                    refreshPage(skills, firstTree);
                    break;
                case 2:
                    secondTree.setText(pages.get(1).getName());
                    refreshPage(skills, secondTree);
                    break;
                case 3:
                    thirdTree.setText(pages.get(2).getName());
                    refreshPage(skills, thirdTree);
                    break;
            }
        }
    }

    private void refreshPage(List<Skill> skills, Tab tab) {
        for (Skill skill : skills) {
            int orderOnPage = skills.indexOf(skill) + 1;
            SkillControl skillControl = (SkillControl) tab.getContent().lookup("#skill" + orderOnPage);
            skillControl.setName(String.valueOf(skill.getName()));
            skillControl.setValue(String.valueOf(skill.getValue()));
            GridPane.setColumnIndex(skillControl, skill.getColumn() - 1);
            GridPane.setRowIndex(skillControl, skill.getRow() - 1);
        }
    }

    private List<Skill> mergeSkillData(List<Skill> data, List<Skill> values) {
        List<Skill> result = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            result.add(Skill.merge(data.get(i), values.get(i)));
        }
        return result;
    }

    @Override
    public void saveCharacter() {
        List<Skill> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Skill>> entry : skillsByPage.entrySet()) {
            int pageIndex = entry.getKey();
            List<Skill> skills = entry.getValue();
            switch (pageIndex) {
                case 1:
                    result.addAll(getSkillValues(skills, firstTree));
                    break;
                case 2:
                    result.addAll(getSkillValues(skills, secondTree));
                    break;
                case 3:
                    result.addAll(getSkillValues(skills, thirdTree));
                    break;
            }
        }
        Collections.sort(result);
        skillsBlock.setSkills(result);
    }

    private List<Skill> getSkillValues(List<Skill> skills, Tab tab) {
        for (Skill skill : skills) {
            int orderOnPage = skills.indexOf(skill) + 1;
            SkillControl skillControl = (SkillControl) tab.getContent().lookup("#skill" + orderOnPage);
            skill.setValue(Integer.parseInt(skillControl.getValue().getText()));
        }
        return skills;
    }
}