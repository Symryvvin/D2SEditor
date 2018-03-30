package ru.aizen.domain.character.block;

import java.nio.ByteBuffer;

public interface DataBlock {

    DataBlock parse(ByteBuffer buffer);

    ByteBuffer collect();
}
