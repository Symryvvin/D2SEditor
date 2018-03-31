package ru.aizen.domain.character.block;

import org.apache.commons.lang3.StringUtils;
import ru.aizen.domain.character.attribute.Attribute;
import ru.aizen.domain.util.BinaryUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributesBlock extends DataBlock {
    private static final String STOP_CODE = "01FF";

    public static final String STRENGTH = "Strength";
    public static final String ENERGY = "Energy";
    public static final String DEXTERITY = "Dexterity";
    public static final String VITALITY = "Vitality";
    public static final String STAT_POINTS = "Stat points";
    public static final String SKILL_POINTS = "Skill points";
    public static final String HP = "Hit point";
    public static final String MAX_HP = "Max hit points";
    public static final String MP = "Mana points";
    public static final String MAX_MP = "Max mana points";
    public static final String SP = "Stamina points";
    public static final String MAX_SP = "Max stamina points";
    public static final String LEVEL = "Level";
    public static final String EXPERIENCE = "Experience";
    public static final String GOLD = "Gold";
    public static final String GOLD_IN_STASH = "Gold in stash";


    private Map<String, Integer> attributes;

    private static Map<Integer, Attribute> attributeMap;
    private static final String ATTRIBUTES_DATA = "/attributes.csv";

    static {
        try {
            attributeMap = Files.readAllLines(Paths.get(AttributesBlock.class.getResource(ATTRIBUTES_DATA).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                    .collect(Collectors.toMap(Attribute::getId, a -> a));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public AttributesBlock(int order) {
        super(order);
        attributes = new HashMap<>();
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        unpack(buffer.array());
        return this;
    }

    @Override
    public ByteBuffer collect() {
        ByteBuffer buffer = ByteBuffer.wrap(pack());
        size = buffer.capacity();
        return buffer;
    }

    private void unpack(byte[] data) {
        String bits = BinaryUtils.getBitString(crapStartCode(data), true);
        int stopId = Integer.parseInt(STOP_CODE, 16);
        int cursor = 0;
        int end;
        while (true) {
            int id = readValue(bits, cursor, cursor + Attribute.ID_OFFSET);
            if (id == stopId)
                break;
            cursor += Attribute.ID_OFFSET;
            Attribute attribute = getBy(id);
            end = cursor + attribute.getLength();
            put(attribute.getName(), readValue(bits, cursor, end));
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
        Map<Integer, Integer> idValue = getIdValueMap();
        for (Map.Entry<Integer, Integer> entry : idValue.entrySet()) {
            int id = entry.getKey();
            Attribute attribute = attributeMap.get(id);
            int value = entry.getValue();
            builder.append(getBinaryString(id, Attribute.ID_OFFSET))
                    .append(getBinaryString(value, attribute.getLength()));
        }
        builder.append("111111111");
        while (builder.length() % 8 != 0) {
            builder.append("0");
        }
        return builder.toString();
    }

    private String getBinaryString(Integer value, int length) {
        return StringUtils.reverse(String.format("%" + length + "s",
                Integer.toBinaryString(value)).replace(' ', '0'));
    }

    private Map<Integer, Integer> getIdValueMap() {
        Map<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            Integer id = findIdInAttributeMapByName(entry.getKey());
            if (id != null) {
                result.put(id, entry.getValue());
            }
        }
        return result;
    }

    private Integer findIdInAttributeMapByName(String name) {
        for (Map.Entry<Integer, Attribute> entry : attributeMap.entrySet()) {
            if (entry.getValue().getName().equals(name))
                return entry.getValue().getId();
        }
        return null;
    }

    private int readValue(String bits, int initial, int length) {
        return BinaryUtils.reversedBitsToInt(bits.substring(initial, length));
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void put(String key, Integer value) {
        attributes.put(key, value);
    }

    public boolean containsKey(String key) {
        return attributes.containsKey(key);
    }

    public Integer get(String key) {
        return attributes.get(key);
    }

    private Attribute getBy(int id) {
        return attributeMap.get(id);
    }
}
