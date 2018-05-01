package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class QuestsBlock extends DataBlock {
    public static final int OFFSET = 335;
    public static final int SIZE = 107;
    public static final int ORDER = 7;

    private byte[] bytes;

    public QuestsBlock() {
        super(ORDER);
    }

    @Override
    public QuestsBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
