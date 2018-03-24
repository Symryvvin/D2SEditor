package ru.aizen.domain.util;

import ru.aizen.domain.HeroData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    public static void backUp(HeroData heroData) throws IOException {
        Files.copy(heroData.getInput(), heroData.getBackUp(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void fromBackUp(HeroData heroData) throws IOException {
        Files.copy(heroData.getBackUp(), heroData.getInput(), StandardCopyOption.REPLACE_EXISTING);
    }
}
