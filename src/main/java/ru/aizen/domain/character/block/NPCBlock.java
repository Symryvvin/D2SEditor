package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class NPCBlock extends DataBlock {
    public static final int OFFSET = 714;
    public static final int SIZE = 51;
    public static final int ORDER = 9;

    private byte[] bytes;

    public NPCBlock() {
        super(ORDER);
    }

    @Override
    public NPCBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
