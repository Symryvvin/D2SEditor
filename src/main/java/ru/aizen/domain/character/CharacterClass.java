package ru.aizen.domain.character;

import java.util.Arrays;

public enum CharacterClass {
    UNDEFINED("Undefined", -1),
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

    public static CharacterClass parse(byte value) throws Exception {
        if (Arrays.stream(CharacterClass.values()).anyMatch(item -> item.value == value)) {
            for (CharacterClass characterClass : CharacterClass.values()) {
                if (characterClass.value == value)
                    return characterClass;
            }
        } else
            throw new Exception(String.format("Unsupported byte value [%s] for character class.", value));
        return UNDEFINED;
    }

    public String getName() {
        return name;
    }
}
