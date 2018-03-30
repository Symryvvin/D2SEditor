package ru.aizen.domain.character;

import java.nio.ByteBuffer;

public interface DataBlock {

    DataBlock parse(ByteBuffer buffer);

    ByteBuffer collect();
}
