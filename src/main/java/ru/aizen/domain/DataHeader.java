package ru.aizen.domain;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DataHeader {
    private String signature;
    private GameVersion version;
    private ByteBuffer fileSize;
    private ByteBuffer checksum;

    public DataHeader(byte[] header) {
        this.signature = Hex.encodeHexString(
                Arrays.copyOfRange(header, 0, 4));
        this.version = GameVersion.parse(Hex.encodeHexString(
                Arrays.copyOfRange(header, 4, 5)));
        this.fileSize = (ByteBuffer) ByteBuffer.allocate(2)
                .put(header[8])
                .put(header[9])
                .order(ByteOrder.LITTLE_ENDIAN)
                .flip();
        this.checksum = (ByteBuffer) ByteBuffer.allocate(4)
                .put(header, 12, 4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .flip();
    }

    public String getSignature() {
        return signature;
    }

    public GameVersion getVersion() {
        return version;
    }

    public short getFileSize() {
        return fileSize.getShort();
    }

    public int getChecksum() {
        return checksum.getInt();
    }

    @Override
    public String toString() {
        return "DataHeader{" +
                "signature='" + signature + '\'' +
                ", version=" + version +
                ", fileSize=" + fileSize.getShort() +
                ", checksum=" + checksum.getInt() +
                '}';
    }
}