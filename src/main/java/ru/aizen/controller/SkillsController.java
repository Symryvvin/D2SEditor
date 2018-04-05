package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import ru.aizen.control.SkillControl;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.data.CSVLoader;

import java.util.List;
import java.util.stream.Collectors;

public class SkillsController extends AbstractController {
    private SkillsBlock skillsBlock;
    @FXML private TabPane tabs;
    private List<Skill> skills;

    private final String skillPath = "icons/skills/";
    private CharacterClass characterClass;

    @Override
    protected void loadCharacter() {
        refresh();
    }

    /**
     * Init or refresh (if choose another class) information in skill tabs
     */
    public void refresh() {
        skillsBlock = character.getSkillsBlock();
        characterClass = character.getCharacterClass();
        setTabsName();
        skills = addValues(skillsBlock.getSkills());
        skills.stream()
                .collect(Collectors.groupingBy(Skill::getPage))
                .forEach((key, value) -> refreshPage(value, key - 1));
    }

    /**
     * Set skill tab name dependence of character class
     */
    private void setTabsName() {
        CSVLoader.pages()
                .stream()
                .filter(skill -> skill.getCharacterClass() == characterClass)
                .collect(Collectors.toList())
                .forEach(skillPage -> tabs.getTabs().get(skillPage.getIndex() - 1).setText(skillPage.getName()));
    }

    private void refreshPage(List<Skill> skills, int index) {
        for (Skill skill : skills) {
            int orderOnPage = skills.indexOf(skill) + 1;
            SkillControl skillControl = (SkillControl) tabs.getTabs()
                    .get(index)
                    .getContent()
                    .lookup("#skill" + orderOnPage);
            skillControl.setName(String.valueOf(skill.getName()));
            skillControl.setValue(String.valueOf(skill.getValue()));
            skillControl.setImageByPath(skillPath + characterClass.getName().toLowerCase() + skill.getImagePath());
            GridPane.setColumnIndex(skillControl, skill.getColumn() - 1);
            GridPane.setRowIndex(skillControl, skill.getRow() - 1);
        }
    }

    /**
     * Add values from skill list loader from skill data block and skill list with other data (name, image, ect.)
     * dependence of character class
     * @param values skill values
     * @return result list of character skills
     */
    private List<Skill> addValues(List<Skill> values) {
        List<Skill> data = CSVLoader.skills()
                .stream()
                .filter(skill -> skill.getCharacterClass() == characterClass)
                .collect(Collectors.toList());
        for (int i = 0; i < data.size(); i++) {
            data.get(i).mergeValue(values.get(i));
        }
        return data;
    }

    @Override
    public void saveCharacter() {
        List<Skill> result = skills.stream()
                .collect(Collectors.groupingBy(Skill::getPage))
                .entrySet()
                .stream()
                .flatMap(entry -> getSkillValues(entry.getValue(), entry.getKey() - 1).stream())
                .sorted().collect(Collectors.toList());
        skillsBlock.setSkills(result);
    }

    /**
     * Reading new value from UI and write them into result list for save
     * @param skills list of skills
     * @param index tab page index
     * @return list of skills with new values
     */
    private List<Skill> getSkillValues(List<Skill> skills, int index) {
        for (Skill skill : skills) {
            int orderOnPage = skills.indexOf(skill) + 1;
            SkillControl skillControl = (SkillControl) tabs.getTabs().get(index).getContent().lookup("#skill" + orderOnPage);
            skill.setValue(Integer.parseInt(skillControl.getValue().getText()));
        }
        return skills;
    }
}