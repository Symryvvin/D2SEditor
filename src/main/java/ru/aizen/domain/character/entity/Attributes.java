package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ru.aizen.domain.data.Extensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Attributes {
    private static final String PATH = "/csv/attributes";

    private static List<Attribute> attributes;

    static {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Quest.class.getResourceAsStream(PATH + Extensions.CSV)))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader()
                    .withTrim()
                    .parse(reader);
            attributes = parser.getRecords()
                    .stream()
                    .map(Attribute::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Attribute getById(long id) {
        return attributes.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
