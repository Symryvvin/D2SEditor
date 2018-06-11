package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ru.aizen.domain.data.Extensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Waypoints {
    private static final String PATH = "/csv/waypoints";

    private static List<Waypoint> waypoints;

    static {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Quest.class.getResourceAsStream(PATH + Extensions.CSV)))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader()
                    .withTrim()
                    .parse(reader);
            waypoints = parser.getRecords()
                    .stream()
                    .map(Waypoint::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Waypoint getByOrder(int order) {
        return waypoints.stream()
                .filter(w -> w.getOrder() == order)
                .findFirst()
                .orElse(null);
    }

    public static List<String> getTowns() {
        List<String> result = new ArrayList<>();
        result.add(getByOrder(0).getName());
        result.add(getByOrder(9).getName());
        result.add(getByOrder(18).getName());
        result.add(getByOrder(27).getName());
        result.add(getByOrder(30).getName());
        return result;
    }
}
