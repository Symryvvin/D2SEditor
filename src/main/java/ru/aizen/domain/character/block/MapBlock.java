package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class MapBlock extends DataBlock {
    public static final int OFFSET = 168;
    public static final int SIZE = 9;
    public static final int ORDER = 4;

    private byte[] bytes;

    public MapBlock() {
        super(ORDER);
    }

    @Override
    public MapBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
