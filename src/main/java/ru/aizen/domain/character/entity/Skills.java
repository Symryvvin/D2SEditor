package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ru.aizen.domain.character.CharacterClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.aizen.domain.data.Extensions.CSV;

public final class Skills {
    private static final Map<CharacterClass, Skills> SKILLS;

    public static final String PATH = "/csv/skills/";
    public static final String NAME_HEADER = "name";
    public static final String PAGE_HEADER = "page";
    public static final String ROW_HEADER = "row";
    public static final String COLUMN_HEADER = "column";
    public static final String ICON_HEADER = "icon";
    public static final String ORDER_HEADER = "order";
    public static final String DESCRIPTION_HEADER = "description";

    private List<Skill> skills;

    static {
        SKILLS = new HashMap<>();
        SKILLS.put(CharacterClass.AMAZON, new Skills().parse(CharacterClass.AMAZON));
        SKILLS.put(CharacterClass.SORCERESS, new Skills().parse(CharacterClass.SORCERESS));
        SKILLS.put(CharacterClass.NECROMANCER, new Skills().parse(CharacterClass.NECROMANCER));
        SKILLS.put(CharacterClass.PALADIN, new Skills().parse(CharacterClass.PALADIN));
        SKILLS.put(CharacterClass.BARBARIAN, new Skills().parse(CharacterClass.BARBARIAN));
        SKILLS.put(CharacterClass.DRUID, new Skills().parse(CharacterClass.DRUID));
        SKILLS.put(CharacterClass.ASSASSIN, new Skills().parse(CharacterClass.ASSASSIN));
    }

    private Skills parse(CharacterClass characterClass) {
        String path = PATH + characterClass.getName().toLowerCase() + CSV;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Skills.class.getResourceAsStream(path)))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader()
                    .withTrim()
                    .parse(reader);
            skills = parser.getRecords()
                    .stream()
                    .map(Skill::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static List<Skill> getByClass(CharacterClass characterClass) {
        return SKILLS.get(characterClass).skills;
    }
}
