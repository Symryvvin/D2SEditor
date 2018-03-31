package ru.aizen.domain.util;

import ru.aizen.domain.data.CharacterData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    public static void backup(CharacterData characterData) throws IOException {
        Files.copy(characterData.getInput(), characterData.getBackUp(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void restore(CharacterData characterData) throws IOException {
        Files.copy(characterData.getBackUp(), characterData.getInput(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void save(Path destination, byte[] data) throws IOException {
        Files.write(destination, data);
    }
}
