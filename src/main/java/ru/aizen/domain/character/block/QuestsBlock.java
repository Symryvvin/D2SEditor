package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.entity.Quest;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestsBlock extends DataBlock {
    public static final int OFFSET = 335;
    public static final int SIZE = 298;
    public static final int ORDER = 7;

    private final byte[] identifier = new byte[]{0x57, 0x6F, 0x6F, 0x21};
    private final byte[] unknown = new byte[]{0x06, 0x00, 0x00, 0x00, 0x2A, 0x01};
    private final int dataSize = 96;

    private Map<Difficult, List<Quest>> quests;

    private byte[] bytes;

    public QuestsBlock() {
        super(ORDER);
        quests = new HashMap<>();
    }

    @Override
    public QuestsBlock parse(ByteReader reader) {
        bytes = reader.getBytes();
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(bytes);
    }
}
