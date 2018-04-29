package ru.aizen.domain.data;

import ru.aizen.domain.character.attribute.Attribute;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public final class CSVLoader {
    private static Map<Long, Attribute> attributes;
    private static final String ATTRIBUTES_CSV = "/attributes.csv";

    static {
        try {
            attributes = Files.readAllLines(Paths.get(CSVLoader.class.getResource(ATTRIBUTES_CSV).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                    .collect(Collectors.toMap(Attribute::getId, a -> a));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Map<Long, Attribute> attributes() {
        return attributes;
    }

}
