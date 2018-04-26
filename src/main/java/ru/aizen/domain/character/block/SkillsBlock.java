package ru.aizen.domain.character.block;

import ru.aizen.domain.UByte;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillsBlock extends DataBlock {
    private Map<Integer, Byte> values;

    public SkillsBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        buffer.getShort();
        values = new HashMap<>();
        byte[] data = new byte[30];
        buffer.get(data, 0, 30);
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
                .put((ByteBuffer) ByteBuffer.allocate(2).put((byte) 105).put((byte) 102).flip())
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
