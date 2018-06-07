package ru.aizen.domain.character.entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Act {
    ACT1(1, "Act 1", "Rogue Encampment"),
    ACT2(2, "Act 2", "Lut Gholein"),
    ACT3(3, "Act 3", "Kurast Docktown"),
    ACT4(4, "Act 4", "The Pandemonium Fortress"),
    ACT5(5, "Act 5", "Harrogath");

    private int number;
    private String name;
    private String town;

    Act(int number, String name, String town) {
        this.number = number;
        this.name = name;
        this.town = town;
    }

    public static Act getByNumber(int number) {
        return Arrays.stream(values())
                .collect(Collectors.toMap(Act::getNumber, Function.identity()))
                .get(number);
    }

    public static List<String> getTowns() {
        return Arrays.stream(values())
                .map(Act::getTown)
                .collect(Collectors.toList());
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getTown() {
        return town;
    }
}
