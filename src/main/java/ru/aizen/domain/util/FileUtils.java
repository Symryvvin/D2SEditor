package ru.aizen.domain.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    public static void save(Path destination, byte[] data) throws IOException {
        Files.write(destination, data);
    }
}
