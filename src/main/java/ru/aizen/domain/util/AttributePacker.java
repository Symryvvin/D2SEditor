package ru.aizen.domain.util;

import ru.aizen.domain.Attribute;
import ru.aizen.domain.Attributes;

public class AttributePacker {
    private static final String STOP_CODE = "01FF";

    public AttributePacker() {
    }

    public Attributes unpackAttributes(byte[] data) {
        Attributes attributes = new Attributes();
        String bits = BinaryUtils.getBitString(data, true);
        int stopId = Integer.parseInt(STOP_CODE, 16);
        int cursor = 0;
        int end;
        while (true) {
            int id = readValue(bits, cursor, cursor + Attribute.ID_OFFSET);
            if (id == stopId)
                break;
            cursor += Attribute.ID_OFFSET;
            Attribute attribute = attributes.getBy(id);
            end = cursor + attribute.getLength();
            attributes.put(attribute.getName(),
                    readValue(bits, cursor, end) / attribute.getDivide());
            cursor = end;
        }
        return attributes;
    }

    private static int readValue(String bits, int initial, int length) {
        return BinaryUtils.reversedBitsToInt(bits.substring(initial, length));
    }


}
