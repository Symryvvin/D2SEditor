package ru.aizen.domain.data;

import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Backup {
    public static final String fileName = "info";

    private List<Path> paths;
    private Path folder;
    private String name;

    public Backup(String name, Path folder) {
        this.name = name;
        this.folder = folder;
        load(folder);
    }

    private void load(Path folder) {
        paths = FileUtils.loadBackupList(folder);
    }

    private void save() throws IOException {
        Path destination = Paths.get(folder.toString() + "/" + fileName);
        Files.deleteIfExists(destination);
        Files.write(destination, name.getBytes(), StandardOpenOption.APPEND);
        for (Path p : paths) {
            Files.write(destination, p.toString().getBytes(), StandardOpenOption.APPEND);
        }
    }

    public void delete(Path path) throws IOException {
        if (paths.contains(path))
            paths.remove(path);
        Files.deleteIfExists(path);
    }

    @Override
    public String toString() {
        return "Backup{" +
                "paths=" + paths +
                ", folder=" + folder +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Path> getPaths() {
        return paths;
    }
}
