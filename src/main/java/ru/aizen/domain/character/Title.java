package ru.aizen.domain.character;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Title {
    NO_TITLE("", "00", Difficult.START),
    UNKNOWN("UNKNOWN", "70", Difficult.UNKNOWN),
    // vanilla
    SIR_DAME("Sir/Dame", "04", Difficult.NORMAL),
    LORD_LADY("Lord/Lady", "08", Difficult.NIGHTMARE),
    BARON_BARONESS("Baron/Baroness", "0C", Difficult.HELL),
    // vanilla hardcore
    COUNT_COUNTESS("Count/Countess", "04",Difficult.NORMAL),
    DUKE_DUCHESS("Duke/Duchess", "08", Difficult.NIGHTMARE),
    KING_QUEEN("King/Queen", "0C", Difficult.HELL),
    // expansion
    SLAYER("Slayer", "05",Difficult.NORMAL),
    CHAMPION("Champion", "0A", Difficult.NIGHTMARE),
    PATRIARCH_MATRIARCH("Patriarch/Matriarch", "0F", Difficult.HELL),
    // expansion hardcore
    DESTROYER("Destroyer", "05",Difficult.NORMAL),
    CONQUEROR("Conqueror", "0A", Difficult.NIGHTMARE),
    GUARDIAN("Guardian", "0F", Difficult.HELL);

    private String name;
    private String value;
    private Difficult difficult;

    Title(String name, String value, Difficult difficult) {
        this.name = name;
        this.value = value;
        this.difficult = difficult;
    }

    public static Stream<Title> vanillaSet() {
        return Stream.of(SIR_DAME, LORD_LADY, BARON_BARONESS);
    }

    public static Stream<Title> expansionSet() {
        return Stream.of(SLAYER, CHAMPION, PATRIARCH_MATRIARCH);
    }

    public static Stream<Title> vanillaHardcoreSet() {
        return Stream.of(COUNT_COUNTESS, DUKE_DUCHESS, KING_QUEEN);
    }

    public static Stream<Title> expansionHardCoreSet() {
        return Stream.of(DESTROYER, CONQUEROR, GUARDIAN);
    }

    public static Title parse(String value, Status status) {
        return parse(Byte.parseByte(value, 16), status);
    }

    public static Title parse(Difficult difficult, List<Title> titles) {
        return titles.stream().filter(title -> title.difficult == difficult).findFirst().orElse(Title.UNKNOWN);
    }

    public static Title parse(byte value, Status status) {
        if (value == 0)
            return NO_TITLE;
        if (status.isExpansion() && !status.isHardcore()) {
            return find(expansionSet(), value);
        }
        if (status.isExpansion() && status.isHardcore()) {
            return find(expansionHardCoreSet(), value);
        }
        if (!status.isExpansion() && !status.isHardcore()) {
            return find(vanillaSet(), value);
        }
        if (!status.isExpansion() && status.isHardcore()) {
            return find(vanillaHardcoreSet(), value);
        }
        return UNKNOWN;
    }

    public static List<Title> getTitleListFromStatus(Status status) {
        if (status.isExpansion()) {
            if (status.isHardcore())
                return expansionHardCoreSet().collect(Collectors.toList());
            return expansionSet().collect(Collectors.toList());
        } else {
            if (status.isHardcore()) {
                return vanillaHardcoreSet().collect(Collectors.toList());
            }
            return vanillaSet().collect(Collectors.toList());
        }
    }

    private static Title find(Stream<Title> stream, byte value) {
        return stream.filter(t -> t.getValue() == value)
                .findFirst().orElse(UNKNOWN);
    }

    public String getName() {
        return name;
    }

    public Difficult getDifficult() {
        return difficult;
    }

    public byte getValue() {
        return Byte.parseByte(value, 16);
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", value='" + value +
                '}';
    }


    public enum Difficult {
        UNKNOWN,
        START,
        NORMAL,
        NIGHTMARE,
        HELL
    }
}
