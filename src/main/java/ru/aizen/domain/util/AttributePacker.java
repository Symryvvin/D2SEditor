package ru.aizen.domain.util;

import ru.aizen.domain.Attribute;
import ru.aizen.domain.Attributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributePacker {
    private static final String STOP_CODE = "01FF";
    private int stopId;

    public AttributePacker() {
        stopId = BinaryUtils.bitsToInt(STOP_CODE);
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
        List<Integer> bits = BinaryUtils.getBits(data, true);
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

    private static int readValue(List<Integer> bits, int cursor, int length) {
        List<Integer> list = bits.subList(cursor, length);
        return BinaryUtils.reversedBitsToInt(list);
    }




}
