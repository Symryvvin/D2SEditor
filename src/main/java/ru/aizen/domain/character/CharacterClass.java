package ru.aizen.domain.character;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CharacterClass {
    AMAZON("Amazon", 0),
    SORCERESS("Sorceress", 1),
    NECROMANCER("Necromancer", 2),
    PALADIN("Paladin", 3),
    BARBARIAN("Barbarian", 4),
    DRUID("Druid", 5),
    ASSASSIN("Assassin", 6);

    private String name;
    private int value;

    CharacterClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static CharacterClass parse(byte value) {
        Map<Integer, CharacterClass> classes = Arrays.stream(CharacterClass.values())
                .collect(Collectors.toMap(CharacterClass::getValue, Function.identity()));
        return classes.get((int)value);
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
