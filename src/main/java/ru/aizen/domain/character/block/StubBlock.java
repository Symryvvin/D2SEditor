package ru.aizen.domain.character.block;

import java.nio.ByteBuffer;

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
    public ByteBuffer collect() {
        return buffer;
    }
}
