package ru.aizen.domain.character.block;

import ru.aizen.domain.UByte;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Stub data block witch not parsed an must be save to file without changes
 */
public class StubBlock extends DataBlock {
    private ByteBuffer buffer;

    public StubBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        this.buffer = buffer;
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(buffer.array());
    }
}
