package ru.aizen.domain.character.block;

import ru.aizen.domain.character.entity.Attribute;
import ru.aizen.domain.dao.AttributeDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.data.binary.BinaryReader;
import ru.aizen.domain.data.binary.BinaryWriter;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributesBlock extends DataBlock {
    /** Identifier of start attributes block */
    public static final byte[] identifier = new byte[]{0x67, 0x66};
    public static final int ORDER = 10;
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
    private int blockSize;
    private final AttributeDao attributeDao;

    public AttributesBlock(AttributeDao attributeDao) {
        super(ORDER);
        this.attributeDao = attributeDao;
        this.attributes = new HashMap<>();
    }

    @Override
    public AttributesBlock parse(ByteReader reader) {
        blockSize = reader.getBytes().length;
        reader.skip(2); // skip identifier 0x6766
        unpack(reader.readBytes(blockSize - 2));
        return this;
    }

    @Override
    public List<UByte> collect() {
        return UByte.getUnsignedBytes(pack());
    }

    private void unpack(byte[] data) {
        BinaryReader reader = new BinaryReader(data);
        int stopId = Integer.parseInt(STOP_CODE, 16);
        while (true) {
            long id = reader.readLong(Attribute.ID_OFFSET);
            if (id == stopId)
                break;
            Attribute attribute = getBy(id);
            put(attribute.getId(), reader.readLong(attribute.getLength()));
        }
    }

    private byte[] pack() {
        byte[] result = getArray();
        ByteBuffer buffer = ByteBuffer.allocate(2 + result.length);
        buffer.put(identifier).put(result).flip();
        return buffer.array();
    }

    private byte[] getArray() {
        BinaryWriter writer = new BinaryWriter();
        for (Map.Entry<Long, Long> entry : attributes.entrySet()) {
            long id = entry.getKey();
            Attribute attribute = getBy(id);
            long value = entry.getValue();
            if (value != 0) {
                writer.writeLong(id, Attribute.ID_OFFSET);
                writer.writeLong(value, attribute.getLength());
            }

        }
        writer.writeHex(STOP_CODE, 9);
        return writer.getBinary().array();
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