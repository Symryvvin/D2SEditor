package ru.aizen.domain.data;

import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class DataWriter {
    private Path destination;

    public DataWriter(Path destination) {
        this.destination = destination;
    }

    public void write(byte[] bytes) throws IOException {
        FileUtils.save(destination, bytes);
    }
}
