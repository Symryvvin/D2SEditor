package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ru.aizen.domain.data.Extensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Quests {
    private static final String PATH = "/csv/quests";

    private static List<Quest> quests;

    static {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Quest.class.getResourceAsStream(PATH + Extensions.CSV)))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader()
                    .parse(reader);
            quests = parser.getRecords()
                    .stream()
                    .map(Quest::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Quest getByPositionAndAct(int position, Act act) {
        return quests.stream()
                .filter(q -> q.getAct() == act && q.getPosition() == position)
                .findFirst()
                .orElse(null);
    }
}
