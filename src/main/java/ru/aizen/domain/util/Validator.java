package ru.aizen.domain.util;

import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.exception.ValidatorException;

import java.util.Arrays;

public final class Validator {

    private Validator() {
        throw new AssertionError();
    }

    public static void checkName(String name) throws ValidatorException {
        if (!name.matches("^[a-zA-Z]+$"))
            throw new ValidatorException("Invalid character name('" + name + "'). " +
                    "Name may contains only letters and '-' or '_'.");
        if (name.length() < 2 || name.length() > 15)
            throw new ValidatorException("Invalid character name('" + name + "'). " +
                    "Name must be more than 2 characters and less than 15 characters.");
        if (name.startsWith("-") || name.startsWith("_"))
            throw new ValidatorException("Invalid character name('" + name + "'). " +
                    "Name can`t start with '-' or '_' characters.");
        if (name.endsWith("-") || name.endsWith("_"))
            throw new ValidatorException("Invalid character name('" + name + "'). " +
                    "Name can`t end with '-' or '_' characters.");
    }

    public static void validateFormat(byte[] bytes) throws ValidatorException {
        String valid = "55AA55AA";
        byte[] validBytes = BinHexUtils.decodeHex(valid);
        if (!Arrays.equals(validBytes, bytes)) {
            throw new ValidatorException("Invalid game save file");
        }
    }

    public static void validateVersion(int version) throws ValidatorException {
        if (version != HeaderBlock.VERSION)
            throw new ValidatorException("Version of save file must be 1.10+");
    }
}
