package ru.aizen.domain.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import ru.aizen.domain.Attribute;
import ru.aizen.domain.Attributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributePacker {
    private static final String STOP_CODE = "01FF";
    private int stopId;

    public AttributePacker() {
        stopId = bitsToInt(STOP_CODE);
    }

    public Attributes unpackAttributes(byte[] data) throws URISyntaxException, IOException {
        List<String> fromCsv = Files.readAllLines(
                Paths.get(getClass().getResource("/attributes.csv").toURI()));
        Map<Integer, Attribute> attributeMap = fromCsv.stream()
                .skip(1)
                .map(i -> i.split(";"))
                .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                .collect(Collectors.toMap(Attribute::getId, a -> a));
        Attributes attributes = new Attributes();
        List<Integer> bits = getBits(data, true);
        int cursor = 0;
        int end;
        while (true) {
            int id = readValue(bits, cursor, cursor + Attribute.ID_OFFSET);
            if (id == stopId)
                break;
            cursor += Attribute.ID_OFFSET;
            Attribute attribute = attributeMap.get(id);
            end = cursor + attribute.getLength();
            attributes.put(attribute.getName(),
                    readValue(bits, cursor, end) / attribute.getDivide());
            cursor = end;
        }
        return attributes;
    }

    private static List<Integer> getBits(byte[] bytes, boolean reverted) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(BinaryUtils.toBinaryString(b, reverted));
        }
        return result.toString()
                .chars()
                .map(i -> i == '1' ? 1 : 0)
                .boxed()
                .collect(Collectors.toList());
    }

    private static int readValue(List<Integer> bits, int cursor, int length) {
        List<Integer> list = bits.subList(cursor, length);
        return reversedBitsToInt(list);
    }

    private static int bitsToInt(List<Integer> bits) {
        return Integer.parseInt(bits.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b), 2);
    }

    private static int reversedBitsToInt(List<Integer> bits) {
        Collections.reverse(bits);
        return bitsToInt(bits);
    }

    private static int bitsToInt(String hex) {
        try {
            return bitsToInt(getBits(Hex.decodeHex(hex), false));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
