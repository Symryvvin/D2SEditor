package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

/**
 * Stub data block witch not parsed an must be save to file without changes
 */
public class StubBlock extends DataBlock {
    private byte[] bytes;

    public StubBlock(int order) {
        super(order);
    }

    @Override
    public StubBlock parse(ByteReader reader) {
        this.bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
