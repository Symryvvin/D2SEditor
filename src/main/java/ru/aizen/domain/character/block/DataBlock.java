package ru.aizen.domain.character.block;

import java.nio.ByteBuffer;

public abstract class DataBlock implements Comparable<DataBlock> {
    protected final int order;
    protected int size;

    public DataBlock(int order) {
        this.order = order;
    }

    public abstract DataBlock parse(ByteBuffer buffer);

    public abstract ByteBuffer collect();

    public int getSize(){
        return size;
    }

    @Override
    public int compareTo(DataBlock o) {
        return Integer.compare(order, o.order);
    }
}
