package ru.aizen.domain.character.attribute;

import ru.aizen.domain.character.CharacterClass;

public class SkillPage {
    private String name;
    private CharacterClass characterClass;
    private int index;

    public SkillPage(String name, CharacterClass characterClass, int index) {
        this.name = name;
        this.characterClass = characterClass;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "SkillPage{" +
                "name='" + name + '\'' +
                ", characterClass=" + characterClass +
                ", index=" + index +
                '}';
    }
}
