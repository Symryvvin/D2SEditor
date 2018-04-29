package ru.aizen.domain.character.block;

import org.apache.commons.lang3.StringUtils;
import ru.aizen.domain.UByte;
import ru.aizen.domain.character.attribute.Attribute;
import ru.aizen.domain.dao.AttributeDao;
import ru.aizen.domain.util.BinaryUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributesBlock extends DataBlock {
    private static final String STOP_CODE = "01FF";

    public static final long STRENGTH = 0;
    public static final long ENERGY = 1;
    public static final long DEXTERITY = 2;
    public static final long VITALITY = 3;
    public static final long STAT_POINTS = 4;
    public static final long SKILL_POINTS = 5;
    public static final long HP = 6;
    public static final long MAX_HP = 7;
    public static final long MP = 8;
    public static final long MAX_MP = 9;
    public static final long SP = 10;
    public static final long MAX_SP = 11;
    public static final long LEVEL = 12;
    public static final long EXPERIENCE = 13;
    public static final long GOLD = 14;
    public static final long GOLD_IN_STASH = 15;

    private Map<Long, Long> attributes;

    private final AttributeDao attributeDao;

    public AttributesBlock(int order, AttributeDao attributeDao) {
        super(order);
        this.attributeDao = attributeDao;
        this.attributes = new HashMap<>();
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        unpack(buffer.array());
        return this;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.wrap(pack());
        size = buffer.capacity();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private void unpack(byte[] data) {
        String bits = BinaryUtils.getBitString(crapStartCode(data), true);
        int stopId = Integer.parseInt(STOP_CODE, 16);
        int cursor = 0;
        int end;
        while (true) {
            long id = readValue(bits, cursor, cursor + Attribute.ID_OFFSET);
            if (id == stopId)
                break;
            cursor += Attribute.ID_OFFSET;
            Attribute attribute = getBy(id);
            end = cursor + attribute.getLength();
            put(attribute.getId(), readValue(bits, cursor, end));
            cursor = end;
        }
    }

    private byte[] crapStartCode(byte[] data) {
        return Arrays.copyOfRange(data, 2, data.length);
    }

    private byte[] pack() {
        byte[] result = BinaryUtils.fromBinaryString(getBinary(), true);
        ByteBuffer buffer = ByteBuffer.allocate(2 + result.length);
        buffer.put((ByteBuffer) ByteBuffer.allocate(2).put((byte) 103).put((byte) 102).flip()).put(result).flip();
        return buffer.array();
    }

    private String getBinary() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Long, Long> entry : attributes.entrySet()) {
            long id = entry.getKey();
            Attribute attribute = getBy(id);
            long value = entry.getValue();
            builder.append(getBinaryString(id, Attribute.ID_OFFSET))
                    .append(getBinaryString(value, attribute.getLength()));
        }
        builder.append("111111111");
        while (builder.length() % 8 != 0) {
            builder.append("0");
        }
        return builder.toString();
    }

    private String getBinaryString(Long value, int length) {
        return StringUtils.reverse(String.format("%" + length + "s",
                Long.toBinaryString(value)).replace(' ', '0'));
    }

    private long readValue(String bits, int initial, int length) {
        return BinaryUtils.reversedBitsToInt(bits.substring(initial, length));
    }

    public Map<Long, Long> getAttributes() {
        return attributes;
    }

    public void put(Long key, Long value) {
        attributes.put(key, value);
    }

    public boolean containsKey(long key) {
        return attributes.containsKey(key);
    }

    public Long get(long key) {
        return attributes.get(key);
    }

    private Attribute getBy(long id) {
        return attributeDao.getAttributeById((int) id);
    }
}