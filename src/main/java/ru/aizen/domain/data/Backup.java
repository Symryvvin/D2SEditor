package ru.aizen.domain.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Backup {
    private static final String BACKUP_EXT = ".bak";

    private Path original;
    private Path directory;
    private Path selectedBackup;
    private List<Path> paths;

    public Backup(Path original) throws IOException {
        this.original = original;
        directory = getDirectory();
        createDirectoryNotExist();
        load();
    }

    /**
     * Load all list of paths from backup directory
     */
    private void load() {
        paths = new ArrayList<>();
        File[] files = directory.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                paths.add(file.toPath());
            }
        }
    }

    /**
     * Trim file extension and get path for backups directory
     * @return full path for backups directory
     */
    private Path getDirectory() {
        return Paths.get(original.toString().replace(".d2s", ""));
    }

    /**
     * Create directory with name as character name if it not exist
     * @throws IOException IO errors
     */
    private void createDirectoryNotExist() throws IOException {
        if (Files.notExists(directory)) {
            Files.createDirectory(directory);
        }
    }

    public void createBackup() throws IOException {
        Path backup = Paths.get(directory.toString() + "/" + getCurrentTime() + BACKUP_EXT);
        selectedBackup = backup;
        paths.add(selectedBackup);
        Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().toString().replace(":", "-");
    }

    public void selectBackup(Path backup) {
        selectedBackup = backup;
    }

    public void revertLast() throws IOException {
        Files.copy(selectedBackup, original, StandardCopyOption.REPLACE_EXISTING);
    }

    public void delete(Path path) throws IOException {
        if (paths.contains(path))
            paths.remove(path);
        Files.deleteIfExists(path);
    }

    public List<Path> getPaths() {
        return paths;
    }


}
