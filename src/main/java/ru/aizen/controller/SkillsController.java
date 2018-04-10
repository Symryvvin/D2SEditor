package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.control.SkillControl;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillPage;
import ru.aizen.domain.character.block.SkillsBlock;
import ru.aizen.domain.dao.SkillDao;
import ru.aizen.domain.data.CSVLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final SkillDao skillDao;

    @Autowired
    public SkillsController(Character character, SkillDao skillDao) {
        super(character);
        this.skillDao = skillDao;
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
        List<Skill> skills = addValues(skillsBlock.getSkillValues());
        firstPageName.setText(pages.get(0).getName());
        secondPageName.setText(pages.get(1).getName());
        thirdPageName.setText(pages.get(2).getName());
        for (Skill skill : skills) {
            SkillControl control = new SkillControl();
            control.setName(skill.getName());
            control.setImageByPath(skill.getImagePath());
            control.setValue(String.valueOf(skill.getValue()));
            control.setSkillOrder(skill.getOrder());
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

    private List<Skill> addValues(Map<Integer, Byte> values) {
        List<Skill> skills = skillDao.getSkills(character.getCharacterClass());
        for (Skill skill : skills) {
            byte value = values.get(skill.getOrder());
            skill.setValue(value);
        }
        return skills;
    }

    @Override
    public void saveCharacter() {
        skillsBlock.setSkillValues(
                skillControls.stream()
                        .collect(Collectors.toMap(SkillControl::getSkillOrder, SkillControl::getValue)));
    }
}