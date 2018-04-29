package ru.aizen.domain.character.block;

import ru.aizen.domain.UByte;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class HeaderBlock extends DataBlock {
    public static final int HEADER_BLOCK_OFFSET = 0;
    public static final int HEADER_BLOCK_SIZE = 16;

    public static final int VERSION = 0x60;

    private int signature;
    private int version;
    private int fileSize;
    private int checksum;

    public HeaderBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.signature = buffer.getInt();
        this.version = buffer.getInt();
        this.fileSize = buffer.getInt();
        this.checksum = buffer.getInt();
        return this;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_BLOCK_SIZE)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(signature)
                .putInt(96) //hardcoded 1.10+ version
                .putInt(0)
                .putInt(0); //clear checksum
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    public int getSignature() {
        return signature;
    }

    public int getVersion() {
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
        return "HeaderBlock{" +
                "signature='" + signature + '\'' +
                ", version=" + version +
                ", fileSize=" + fileSize +
                ", checksum=" + checksum +
                '}';
    }


}