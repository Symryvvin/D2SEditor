package ru.aizen.domain.util;

public final class CheckUtils {

    private CheckUtils() {
        throw new AssertionError();
    }

    public static void checkName(String name) throws Exception {
        if (!name.matches("^[a-zA-Z]+$"))
            throw new Exception("Invalid character name('" + name + "'). Name may contains only letters and '-' or '_'.");
        if (name.length() < 2 || name.length() > 15)
            throw new Exception("Invalid character name('" + name + "'). Name must be more than 2 characters and less than 15 characters.");
        if (name.startsWith("-") || name.startsWith("_"))
            throw new Exception("Invalid character name('" + name + "'). Name can`t start with '-' or '_' characters.");
        if (name.endsWith("-") || name.endsWith("_"))
            throw new Exception("Invalid character name('" + name + "'). Name can`t end with '-' or '_' characters.");
    }
}
