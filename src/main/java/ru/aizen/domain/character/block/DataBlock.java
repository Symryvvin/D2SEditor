package ru.aizen.domain.character.block;

import java.nio.ByteBuffer;

public abstract class DataBlock implements Comparable<DataBlock> {
    protected final int order;

    public DataBlock(int order) {
        this.order = order;
    }

    public abstract DataBlock parse(ByteBuffer buffer);

    public abstract ByteBuffer collect();

    @Override
    public int compareTo(DataBlock o) {
        return Integer.compare(order, o.order);
    }
}
