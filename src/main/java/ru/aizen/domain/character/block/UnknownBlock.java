package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

/**
 * Stub data block witch not parsed an must be save to file without changes
 */
public class UnknownBlock extends DataBlock {
    private byte[] bytes;

    public UnknownBlock(int order) {
        super(order);
    }

    @Override
    public UnknownBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
