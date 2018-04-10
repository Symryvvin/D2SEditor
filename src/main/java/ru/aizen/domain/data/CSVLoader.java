package ru.aizen.domain.data;

import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.attribute.Attribute;
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
    private static List<SkillPage> pages;
    private static final String PAGES_CSV = "/pages.csv";

    static {
        try {
            attributes = Files.readAllLines(Paths.get(CSVLoader.class.getResource(ATTRIBUTES_CSV).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                    .collect(Collectors.toMap(Attribute::getId, a -> a));
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

    public static List<SkillPage> pages() {
        return pages;
    }
}
