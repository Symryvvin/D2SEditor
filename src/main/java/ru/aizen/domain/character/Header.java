package ru.aizen.domain.character;

import ru.aizen.domain.GameVersion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Header {
    private int signature;
    private GameVersion version;
    private int fileSize;
    private int checksum;

    public Header(byte[] header) {
        ByteBuffer buffer = ByteBuffer.wrap(header);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.signature = buffer.getInt();
        this.version = GameVersion.parse(buffer.getInt());
        this.fileSize = buffer.getInt();
        this.checksum = buffer.getInt();
    }

    public int getSignature() {
        return signature;
    }

    public GameVersion getVersion() {
        return version;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getChecksum() {
        return checksum;
    }

    @Override
    public String toString() {
        return "Header{" +
                "signature='" + signature + '\'' +
                ", version=" + version +
                ", fileSize=" + fileSize +
                ", checksum=" + checksum +
                '}';
    }
}