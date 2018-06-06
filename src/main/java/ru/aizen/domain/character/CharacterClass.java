package ru.aizen.domain.character;

import ru.aizen.domain.character.entity.Gender;

import java.util.Arrays;

public enum CharacterClass {
    UNKNOWN(0xff, "Unknown Class", null),
    AMAZON(0x00, "Amazon", Gender.FEMALE),
    SORCERESS(0x01, "Sorceress", Gender.FEMALE),
    NECROMANCER(0x02, "Necromancer", Gender.MALE),
    PALADIN(0x03, "Paladin", Gender.MALE),
    BARBARIAN(0x04, "Barbarian", Gender.MALE),
    DRUID(0x05, "Druid", Gender.MALE),
    ASSASSIN(0x06, "Assassin", Gender.FEMALE);

    private int value;
    private String name;
    private Gender gender;

    CharacterClass(int value, String name, Gender gender) {
        this.value = value;
        this.name = name;
        this.gender = gender;
    }

    public static CharacterClass getByValue(int value) {
        return Arrays.stream(CharacterClass.values())
                .filter(c -> c.value == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }
}
