package ru.aizen.domain.character.block;

import ru.aizen.domain.dao.SkillDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillsBlock extends DataBlock {
    public static final int SKILLS_BLOCK_SIZE = 32;
    /** Identifier of start skill block */
    public static final byte[] identifier = new byte[]{0x69, 0x66};
    public static final int ORDER = 12;

    private Map<Integer, Byte> values;
    private final SkillDao skillDao;

    public SkillsBlock(SkillDao skillDao) {
        super(ORDER);
        this.skillDao = skillDao;
    }

    @Override
    public SkillsBlock parse(ByteReader reader) {
        reader.skip(2); // skip identifier 0x6966
        values = new HashMap<>();
        byte[] data = reader.readBytes(30);
        for (int i = 0; i < data.length; i++) {
            values.put(i + 1, data[i]);
        }
        return this;
    }

    @Override
    public List<UByte> collect() {
        byte[] result = new byte[30];
        values.forEach((key, value) -> result[key - 1] = value);
        ByteBuffer buffer = ByteBuffer.allocate(2 + result.length)
                .put(identifier)
                .put(result);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    public Map<Integer, Byte> getSkillValues() {
        return values;
    }

    public void setSkillValues(Map<Integer, Byte> values) {
        this.values = values;
    }
}
