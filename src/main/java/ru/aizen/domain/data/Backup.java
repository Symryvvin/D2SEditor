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

    private String name;
    private Path parent;
    private Path original;
    private Path directory;
    private Path selectedBackup;
    private List<Path> paths;

    public Backup(Path original) throws IOException {
        this.name = original.getFileName().toString().replace(".d2s", "");
        this.original = original;
        this.parent = original.getParent();
        directory = parent.resolve(name);
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


    public Path renameAll(String newName) throws IOException {
        renameWithExtension(newName, "key");
        renameWithExtension(newName, "map");
        renameWithExtension(newName, "ma0");
        renameWithExtension(newName, "ma1");
        renameWithExtension(newName, "ma2");
        renameWithExtension(newName, "ma3");
        renameBackupDirectory(newName);
        return Files.move(original, parent.resolve(newName + ".d2s"));
    }

    private void renameBackupDirectory(String newName) throws IOException {
        Path newBackUpParent = parent.resolve(newName);
        if (Files.notExists(newBackUpParent)) {
            Files.createDirectory(newBackUpParent);
        }
        for (Path path : paths) {
            String fileName = path.getFileName().toString();
            Files.move(path, newBackUpParent.resolve(fileName));
        }
        Files.deleteIfExists(directory);
    }

    private void renameWithExtension(String newName, String extension) throws IOException {
        Path old = parent.resolve(name + "." + extension);
        if (Files.exists(old))
            Files.move(parent.resolve(name + "." + extension), parent.resolve(newName + "." + extension));
    }

}
