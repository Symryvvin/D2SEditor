package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import ru.aizen.control.NumericField;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillTree;
import ru.aizen.domain.character.block.SkillsBlock;

import java.util.List;

public class SkillsController extends AbstractController {
    @FXML private Tab firstTree;
    @FXML private Tab secondTree;
    @FXML private Tab thirdTree;

    private SkillsBlock skillsBlock;

    @Override
    protected void loadCharacter() {
        skillsBlock = character.getSkillsBlock();
        List<SkillTree> skillTrees = skillsBlock.getSkillTrees();
        List<Skill> first = skillTrees.get(0).getSkills();
        for (Skill skill : first) {
            NumericField value = (NumericField) firstTree.getContent().lookup("#value" + skill.getOrder());
            value.setText(String.valueOf(skill.getValue()));
        }
        List<Skill> second = skillTrees.get(1).getSkills();
        for (Skill skill : second) {
            NumericField value = (NumericField) secondTree.getContent().lookup("#value" + skill.getOrder());
            value.setText(String.valueOf(skill.getValue()));
        }
        List<Skill> third = skillTrees.get(2).getSkills();
        for (Skill skill : third) {
            NumericField value = (NumericField) thirdTree.getContent().lookup("#value" + skill.getOrder());
            value.setText(String.valueOf(skill.getValue()));
        }
    }

    @Override
    public void saveCharacter() {
        List<SkillTree> skillTrees = skillsBlock.getSkillTrees();
        List<Skill> first = skillTrees.get(0).getSkills();
        for (Skill skill : first) {
            NumericField value = (NumericField) firstTree.getContent().lookup("#value" + skill.getOrder());
            skill.setValue(Integer.parseInt(value.getText()));
        }
        List<Skill> second = skillTrees.get(1).getSkills();
        for (Skill skill : second) {
            NumericField value = (NumericField) secondTree.getContent().lookup("#value" + skill.getOrder());
            skill.setValue(Integer.parseInt(value.getText()));
        }
        List<Skill> third = skillTrees.get(2).getSkills();
        for (Skill skill : third) {
            NumericField value = (NumericField) thirdTree.getContent().lookup("#value" + skill.getOrder());
            skill.setValue(Integer.parseInt(value.getText()));
        }
        skillsBlock.setSkillTrees(skillTrees);
    }
}
