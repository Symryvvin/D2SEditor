package ru.aizen.domain.character;

import java.util.stream.Stream;

public enum Title {
    NO_TITLE("", "00"),
    UNKNOWN("UNKNOWN", "70"),
    // vanilla
    SIR_DAME("Sir/Dame", "04"),
    LORD_LADY("Lord/Lady", "08"),
    BARON_BARONESS("Baron/Baroness", "0C"),
    // vanilla hardcore
    COUNT_COUNTESS("Count/Countess", "04"),
    DUKE_DUCHESS("Duke/Duchess", "08"),
    KING_QUEEN("King/Queen", "0C"),
    // expansion
    SLAYER("Slayer", "05"),
    CHAMPION("Champion", "0A"),
    PATRIARCH_MATRIARCH("Patriarch/Matriarch", "0F"),
    // expansion hardcore
    DESTROYER("Destroyer", "05"),
    CONQUEROR("Conqueror", "0A"),
    GUARDIAN("Guardian", "0F");

    private String name;
    private String value;

    Title(String name, String value) {
        this.name = name;
        this.value = value;
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

    private static Title find(Stream<Title> stream, byte value) {
        return stream.filter(t -> t.getValue() == value)
                .findFirst().orElse(UNKNOWN);
    }

    public String getName() {
        return name;
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
}
