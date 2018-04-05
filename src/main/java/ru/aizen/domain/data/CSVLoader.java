package ru.aizen.domain.data;

import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.attribute.Attribute;
import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillPage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CSVLoader {
    private static Map<Long, Attribute> attributes;
    private static final String ATTRIBUTES_CSV = "/attributes.csv";
    private static List<Skill> skills;
    private static final String SKILLS_CSV = "/skills.csv";
    private static List<SkillPage> pages;
    private static final String PAGES_CSV = "/pages.csv";

    static {
        try {
            attributes = Files.readAllLines(Paths.get(CSVLoader.class.getResource(ATTRIBUTES_CSV).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                    .collect(Collectors.toMap(Attribute::getId, a -> a));
            skills = Files.readAllLines(Paths.get(CSVLoader.class.getResource(SKILLS_CSV).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> {
                        String name = i[0];
                        CharacterClass characterClass = CharacterClass.valueOf(i[1]);
                        int page = Integer.parseInt(i[2]);
                        int row = Integer.parseInt(i[3]);
                        int column = Integer.parseInt(i[4]);
                        String image = i[5] + ".png";
                        return new Skill(name, characterClass, page, row, column, image);
                    })
                    .collect(Collectors.toList());
            pages = Files.readAllLines(Paths.get(CSVLoader.class.getResource(PAGES_CSV).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> {
                        CharacterClass characterClass = CharacterClass.valueOf(i[0]);
                        int index = Integer.parseInt(i[1]);
                        String name = i[2];
                        return new SkillPage(name, characterClass, index);
                    })
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Map<Long, Attribute> attributes() {
        return attributes;
    }

    public static List<Skill> skills() {
        return skills;
    }

    public static List<SkillPage> pages() {
        return pages;
    }
}
