package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class HotKeysBlock extends DataBlock {
    public static final int OFFSET = 56;
    public static final int SIZE = 112;
    public static final int ORDER = 3;

    private byte[] bytes;

    public HotKeysBlock() {
        super(ORDER);
    }

    @Override
    public HotKeysBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
