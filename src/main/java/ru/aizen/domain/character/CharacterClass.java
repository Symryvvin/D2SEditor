package ru.aizen.domain.character;

public enum CharacterClass {
    AMAZON(" Amazon", 0),
    SORCERESS("Sorceress", 1),
    NECROMANCER("Necromancer", 2),
    PALADIN("Paladin", 3),
    BARBARIAN("Barbarian", 4),
    DRUID("Druid", 5),
    ASSASSIN(" Assassin", 6);

    private final String name;
    private int value;

    CharacterClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public CharacterClass parse(byte value) {
        for (CharacterClass characterClass : CharacterClass.values()) {
            if (characterClass.value == value)
                return characterClass;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }


}
