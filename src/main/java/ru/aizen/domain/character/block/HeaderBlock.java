package ru.aizen.domain.character.block;

import ru.aizen.domain.UByte;
import ru.aizen.domain.data.BlockSize;
import ru.aizen.domain.data.GameVersion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class HeaderBlock extends DataBlock {

    private int signature;
    private GameVersion version;
    private int fileSize;
    private int checksum;

    public HeaderBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.signature = buffer.getInt();
        this.version = GameVersion.parse(buffer.getInt());
        this.fileSize = buffer.getInt();
        this.checksum = buffer.getInt();
        return this;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(BlockSize.HEADER_BLOCK_SIZE)
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

    public void setSignature(int signature) {
        this.signature = signature;
    }

    public GameVersion getVersion() {
        return version;
    }

    public void setVersion(GameVersion version) {
        this.version = version;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
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