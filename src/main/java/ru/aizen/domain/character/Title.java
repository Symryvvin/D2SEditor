package ru.aizen.domain.character;

import ru.aizen.domain.character.entity.Gender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.aizen.domain.character.Difficult.*;
import static ru.aizen.domain.character.entity.Game.Mode;
import static ru.aizen.domain.character.entity.Game.Mode.HARDCORE;
import static ru.aizen.domain.character.entity.Game.Mode.SOFTCORE;
import static ru.aizen.domain.character.entity.Game.Version;
import static ru.aizen.domain.character.entity.Game.Version.CLASSIC;
import static ru.aizen.domain.character.entity.Game.Version.EXPANSION;
import static ru.aizen.domain.character.entity.Gender.*;

public enum Title {
    NO_STATUS("", ALL, NORMAL, null, null, 0x00, 0x01, 0x02, 0x03),
    SIR("Sir", MALE, NIGHTMARE, SOFTCORE, CLASSIC, 0x04, 0x05, 0x06, 0x07),
    DAME("Dame", FEMALE, NIGHTMARE, SOFTCORE, CLASSIC, 0x04, 0x05, 0x06, 0x07),
    LORD("Lord", MALE, HELL, SOFTCORE, CLASSIC, 0x08, 0x09, 0x0A, 0x0B),
    LADY("Lady", FEMALE, HELL, SOFTCORE, CLASSIC, 0x08, 0x09, 0x0A, 0x0B),
    BARON("Baron", MALE, COMPLETE, SOFTCORE, CLASSIC, 0x0C),
    BARONESS("Baroness", FEMALE, COMPLETE, SOFTCORE, CLASSIC, 0x0C),
    COUNT("Count", MALE, NIGHTMARE, HARDCORE, CLASSIC, 0x04, 0x05, 0x06, 0x07),
    COUNTESS("Countess", FEMALE, NIGHTMARE, HARDCORE, CLASSIC, 0x04, 0x05, 0x06, 0x07),
    DUKE("Duke", MALE, HELL, HARDCORE, CLASSIC, 0x08, 0x09, 0x0A, 0x0B),
    DUCHESS("Duchess", FEMALE, HELL, HARDCORE, CLASSIC, 0x08, 0x09, 0x0A, 0x0B),
    KING("King", MALE, COMPLETE, HARDCORE, CLASSIC, 0x0C),
    QUEEN("Queen", FEMALE, COMPLETE, HARDCORE, CLASSIC, 0x0C),
    SLAYER("Slayer", ALL, NIGHTMARE, SOFTCORE, EXPANSION, 0x05, 0x06, 0x07, 0x08),
    CHAMPION("Champion", ALL, HELL, SOFTCORE, EXPANSION, 0x0A, 0x0B, 0x0C, 0x0D),
    PATRIARCH("Patriarch", MALE, COMPLETE, SOFTCORE, EXPANSION, 0x0F),
    MATRIARCH("Matriarch", FEMALE, COMPLETE, SOFTCORE, EXPANSION, 0x0F),
    DESTROYER("Destroyer", ALL, NIGHTMARE, HARDCORE, EXPANSION, 0x05, 0x06, 0x07, 0x08),
    CONQUEROR("Conqueror", ALL, HELL, HARDCORE, EXPANSION, 0x0A, 0x0B, 0x0C, 0x0D),
    GUARDIAN("Guardian", ALL, COMPLETE, HARDCORE, EXPANSION, 0x0F),

    UNKNOWN("Unknown", ALL, null, null, null);

    private List<Integer> values;
    private String name;
    private Gender gender;
    private Difficult difficult;
    private Mode mode;
    private Version version;

    Title(String name, Gender gender, Difficult difficult, Mode mode, Version version, Integer... values) {
        this.name = name;
        this.gender = gender;
        this.difficult = difficult;
        this.mode = mode;
        this.version = version;
        this.values = Arrays.asList(values);
    }

    public static Title getByValue(int value, Gender gender, Status status) {
        Mode mode = status.isHardcore() ? HARDCORE : SOFTCORE;
        Version version = status.isExpansion() ? EXPANSION : CLASSIC;
        return Arrays.stream(values())
                .filter(t -> (t.gender == gender || t.gender == ALL) &&
                        t.mode == mode &&
                        t.version == version &&
                        t.values.contains(value) ||
                        t.difficult == NORMAL)
                .findFirst().orElse(UNKNOWN);
    }

    public static List<Title> getTitlesForCharacter(Character character) {
        Mode mode = character.getMetaBlock().getStatus().isHardcore() ? HARDCORE : SOFTCORE;
        Version version = character.getMetaBlock().getStatus().isExpansion() ? EXPANSION : CLASSIC;
        Gender gender = character.getMetaBlock().getCharacterClass().getGender();
        return Arrays.stream(values())
                .filter(t -> (t.gender == gender || t.gender == ALL) &&
                        t.mode == mode &&
                        t.version == version ||
                        t.difficult == NORMAL)
                .collect(Collectors.toList());
    }

    public byte getValue() {
        return values.get(0).byteValue();
    }

    public String getName() {
        return name;
    }

    public Difficult getDifficult() {
        return difficult;
    }


}
