package ru.aizen.domain.util;

import ru.aizen.domain.Attribute;
import ru.aizen.domain.Attributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class HeroDataExtractorUtils {
    private HeroDataExtractorUtils() {
        throw new AssertionError();
    }

    public static Attributes unpackAttributes(byte[] data) throws URISyntaxException, IOException {
        List<String> fromCsv = Files.readAllLines(
                Paths.get(HeroDataExtractorUtils.class.getResource("/attributes.csv").toURI()));
        Map<Integer, Attribute> attributeMap = fromCsv.stream()
                .skip(1)
                .map(i -> i.split(";"))
                .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                .collect(Collectors.toMap(Attribute::getId, a -> a));
        Attributes attributes = new Attributes();
        List<Integer> bits = getBitListFromByteArray(data);
        int cursor = 0;
        int end;
        while (true) {
            int id = readBits(bits, cursor, cursor + Attribute.ID_OFFSET);
            if (id == 511)
                break;
            cursor += Attribute.ID_OFFSET;
            Attribute attribute = attributeMap.get(id);
            end = cursor + attribute.getLength();
            attributes.put(attribute.getName(),
                    readBits(bits, cursor, end) / attribute.getDivide());
            cursor = end;
        }
        return attributes;
    }

    private static List<Integer> getBitListFromByteArray(byte[] bytes) {
        List<Integer> bits = new ArrayList<>();
        for (byte aByte : bytes) {
            String bitString = String.format("%8s",
                    Integer.toBinaryString(Byte.toUnsignedInt(aByte))).replace(' ', '0');
            String reversed = new StringBuilder(bitString).reverse().toString();
            char[] chars = reversed.toCharArray();
            for (char ch : chars) {
                bits.add(ch == '1' ? 1 : 0);
            }
        }
        return bits;
    }

    private static int readBits(List<Integer> bits, int cursor, int length) {
        List<Integer> list = bits.subList(cursor, length);
        Collections.reverse(list);
        return Integer.parseInt(list.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b), 2);
    }
}
