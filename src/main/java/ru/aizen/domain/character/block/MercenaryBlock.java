package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class MercenaryBlock extends DataBlock {
    public static final int OFFSET = 177;
    public static final int SIZE = 14;
    public static final int ORDER = 5;

    private byte[] bytes;

    public MercenaryBlock() {
        super(ORDER);
    }

    @Override
    public MercenaryBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}

