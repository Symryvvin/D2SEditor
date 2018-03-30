package ru.aizen.domain.attribute;

import ru.aizen.domain.character.DataBlock;
import ru.aizen.domain.util.BinaryUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributesBlock extends HashMap<String, Integer> implements DataBlock {
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

    public AttributesBlock() {
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        unpack(buffer.array());
        return this;
    }

    @Override
    public ByteBuffer collect() {
        return null;
    }

    @Override
    public Integer put(String key, Integer value) {
        return super.put(key, value);
    }

    public Attribute getBy(int id) {
        return attributeMap.get(id);
    }

    private void unpack(byte[] data) {
        String bits = BinaryUtils.getBitString(data, true);
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
            put(attribute.getName(), readValue(bits, cursor, end) / attribute.getDivide());
            cursor = end;
        }
    }

    private int readValue(String bits, int initial, int length) {
        return BinaryUtils.reversedBitsToInt(bits.substring(initial, length));
    }
}