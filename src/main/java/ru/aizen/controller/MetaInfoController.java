package ru.aizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;

@Component
public class MetaInfoController extends AbstractController {
    private final SkillsController skillsController;

    @Autowired
    public MetaInfoController(Character character,
                              SkillsController skillsController) {
        super(character);
        this.skillsController = skillsController;
    }

    @Override
    protected void loadCharacter() {

    }

    @Override
    public void saveCharacter() {

    }

    public void pickAssassin() {
        character.setCharacterClass(CharacterClass.ASSASSIN);
        skillsController.loadCharacter();
    }

    public void pickAmazon() {
        character.setCharacterClass(CharacterClass.AMAZON);
        skillsController.loadCharacter();
    }

    public void pickNecromancer() {
        character.setCharacterClass(CharacterClass.NECROMANCER);
        skillsController.loadCharacter();
    }

    public void pickBarbarian() {
        character.setCharacterClass(CharacterClass.BARBARIAN);
        skillsController.loadCharacter();
    }

    public void pickPaladin() {
        character.setCharacterClass(CharacterClass.PALADIN);
        skillsController.loadCharacter();
    }

    public void pickSorceress() {
        character.setCharacterClass(CharacterClass.SORCERESS);
        skillsController.loadCharacter();
    }

    public void pickDruid() {
        character.setCharacterClass(CharacterClass.DRUID);
        skillsController.loadCharacter();
    }
}
