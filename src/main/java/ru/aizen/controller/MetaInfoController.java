package ru.aizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;

@Component
public class MetaInfoController extends AbstractController {
    private final SkillsController skillsController;
    private final EditorController editorController;

    @Autowired
    public MetaInfoController(Character character,
                              SkillsController skillsController,
                              EditorController editorController) {
        super(character);
        this.skillsController = skillsController;
        this.editorController = editorController;
    }

    @Override
    protected void loadCharacter() {

    }

    @Override
    public void saveCharacter() {

    }

    public void pickAssassin() {
        pick(CharacterClass.ASSASSIN);
    }

    public void pickAmazon() {
        pick(CharacterClass.AMAZON);
    }

    public void pickNecromancer() {
        pick(CharacterClass.NECROMANCER);
    }

    public void pickBarbarian() {
        pick(CharacterClass.BARBARIAN);
    }

    public void pickPaladin() {
        pick(CharacterClass.PALADIN);
    }

    public void pickSorceress() {
        pick(CharacterClass.SORCERESS);
    }

    public void pickDruid() {
        pick(CharacterClass.DRUID);
    }

    private void pick(CharacterClass characterClass) {
        character.setCharacterClass(characterClass);
        editorController.changeTitleList();
        skillsController.loadCharacter();
    }
}
