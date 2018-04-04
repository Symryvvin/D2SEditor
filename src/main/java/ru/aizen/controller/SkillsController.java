package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.aizen.control.NumericField;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillPage;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.data.CSVLoader;

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
        for (int i = 0; i < 30; i++) {
            data.get(i).setValue(values.get(i).getValue());
            data.get(i).setOrder(values.get(i).getOrder());
        }
        skillsByPage = data
                .stream()
                .collect(Collectors.groupingBy(Skill::getPage));
        for (Map.Entry<Integer, List<Skill>> entry : skillsByPage.entrySet()) {
            if (entry.getKey() == 1) {
                firstTree.setText(pages.get(0).getName());
                for (Skill skill : entry.getValue()) {
                    NumericField value = (NumericField) firstTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    value.setText(String.valueOf(skill.getValue()));
                    Label name = (Label) firstTree.getContent().lookup("#name" + (entry.getValue().indexOf(skill) + 1));
                    name.setText(skill.getName());
                }
            }
            if (entry.getKey() == 2) {
                secondTree.setText(pages.get(1).getName());
                for (Skill skill : entry.getValue()) {
                    NumericField value = (NumericField) secondTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    value.setText(String.valueOf(skill.getValue()));
                    Label name = (Label) secondTree.getContent().lookup("#name" + (entry.getValue().indexOf(skill) + 1));
                    name.setText(skill.getName());
                }
            }
            if (entry.getKey() == 3) {
                thirdTree.setText(pages.get(2).getName());
                for (Skill skill : entry.getValue()) {
                    NumericField value = (NumericField) thirdTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    value.setText(String.valueOf(skill.getValue()));
                    Label name = (Label) thirdTree.getContent().lookup("#name" + (entry.getValue().indexOf(skill) + 1));
                    name.setText(skill.getName());
                }
            }
        }
    }

    @Override
    public void saveCharacter() {
        for (Map.Entry<Integer, List<Skill>> entry : skillsByPage.entrySet()) {
            int pageIndex = entry.getKey();
            List<Skill> skills = entry.getValue();
            if (pageIndex == 1) {
                for (Skill skill : skills) {
                    NumericField value = (NumericField) firstTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    skill.setValue(Integer.parseInt(value.getText()));
                }
            }
            if (pageIndex == 2) {
                for (Skill skill : skills) {
                    NumericField value = (NumericField) secondTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    skill.setValue(Integer.parseInt(value.getText()));
                }
            }
            if (pageIndex == 3) {
                for (Skill skill : skills) {
                    NumericField value = (NumericField) thirdTree.getContent().lookup("#value" + (entry.getValue().indexOf(skill) + 1));
                    skill.setValue(Integer.parseInt(value.getText()));
                }
            }
        }
        List<Skill> result = skillsByPage.entrySet()
                .stream()
                .flatMap(item -> item.getValue().stream()).sorted()
                .collect(Collectors.toList());
        skillsBlock.setSkills(result);
    }
}
