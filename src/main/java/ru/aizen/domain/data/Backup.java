package ru.aizen.domain.data;

import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Backup {
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
