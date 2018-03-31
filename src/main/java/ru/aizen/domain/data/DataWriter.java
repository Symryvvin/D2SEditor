package ru.aizen.domain.data;

import org.apache.commons.codec.binary.Hex;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class DataWriter {
    private Path destination;

    public DataWriter(Path destination) {
        this.destination = destination;
    }

    public void write(byte[] bytes) throws IOException {
        System.out.println(Hex.encodeHexString(bytes));
        FileUtils.save(destination, bytes);
    }
}
