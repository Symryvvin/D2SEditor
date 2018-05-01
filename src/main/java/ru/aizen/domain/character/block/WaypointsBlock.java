package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class WaypointsBlock extends DataBlock {
    public static final int OFFSET = 633;
    public static final int SIZE = 81;
    public static final int ORDER = 9;

    private byte[] bytes;

    public WaypointsBlock() {
        super(ORDER);
    }

    @Override
    public WaypointsBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
