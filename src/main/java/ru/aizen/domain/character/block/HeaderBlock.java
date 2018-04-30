package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.Validator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class HeaderBlock extends DataBlock {
    public static final int HEADER_BLOCK_OFFSET = 0;
    public static final int HEADER_BLOCK_SIZE = 16;

    public static final Integer VERSION = 0x60;
    public static final String SIGNATURE = "55AA55AA";

    private byte[] signature;
    private int version;
    private int fileSize;
    private int checksum;

    public HeaderBlock(int order) {
        super(order);
    }

    @Override
    public HeaderBlock parse(ByteReader reader) {
        this.signature = reader.readBytes(4);
        this.version = reader.readInt();
        this.fileSize = reader.readInt();
        this.checksum = reader.readInt();
        return this;
    }

    public void validate() throws ValidatorException {
        Validator.validateFormat(signature);
        Validator.validateVersion(version);
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_BLOCK_SIZE)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put(signature)
                .putInt(96) //hardcoded 1.10+ version
                .putInt(0)
                .putInt(0); //clear checksum
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    public byte[] getSignature() {
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