package ru.aizen.domain.util;

import ru.aizen.domain.data.CharacterData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    public static Path backup(CharacterData characterData) throws IOException {
        Path backup = Paths.get(characterData.getBackupFolder().toString() + "/" +
                LocalDateTime.now().toString().replace(":", "-") + ".bak");
        return Files.copy(characterData.getInput(), backup, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void restore(Path backup, CharacterData characterData) throws IOException {
        Files.copy(backup, characterData.getInput(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void save(Path destination, byte[] data) throws IOException {
        Files.write(destination, data);
    }
}
