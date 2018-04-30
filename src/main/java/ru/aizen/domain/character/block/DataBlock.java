package ru.aizen.domain.character.block;

import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.List;

public abstract class DataBlock implements Comparable<DataBlock> {
    protected final int order;

    public DataBlock(int order) {
        this.order = order;
    }

    public abstract DataBlock parse(ByteReader reader);

    public abstract List<UByte> collect();

    @Override
    public int compareTo(DataBlock o) {
        return Integer.compare(order, o.order);
    }
}
