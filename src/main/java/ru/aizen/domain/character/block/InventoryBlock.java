package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public class InventoryBlock extends DataBlock {
    public static final byte[] identifier = new byte[]{0x4A, 0x4D};
    public static final int ORDER = 12;

    private byte[] bytes;

    public InventoryBlock() {
        super(ORDER);
    }

    @Override
    public InventoryBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
