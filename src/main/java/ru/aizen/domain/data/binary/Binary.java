package ru.aizen.domain.data.binary;

import ru.aizen.domain.data.UByte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Binary {
    private static final Map<Character, Boolean> booleans = new HashMap<>();
    private static final Map<Boolean, Character> chars = new HashMap<>();

    public static final char ZERO = '0';
    public static final char ONE = '1';

    static {
        booleans.put(ONE, true);
        booleans.put(ZERO, false);
        chars.put(true, ONE);
        chars.put(false, ZERO);
    }

    private StringBuilder binary;

    public Binary(byte[] bytes) {
        this.binary = init(UByte.getUnsignedBytes(bytes));
    }

    public Binary(int capacity) {
        this.binary = new StringBuilder();
        fillByZero(capacity);
    }

    public Binary() {
        this.binary = new StringBuilder("");
    }

    private StringBuilder init(List<UByte> bytes) {
        bytes.forEach(UByte::revert);
        return new StringBuilder(bytes.stream()
                .map(UByte::toBinary)
                .reduce("", (f, s) -> f + s));
    }

    public void fillByZero(int max) {
        while (binary.length() < max) {
            binary.append("0");
        }
    }

    public boolean getValueAt(int index) {
        return booleans.get(binary.charAt(index));
    }

    public void setValueAt(int index, boolean b) {
        binary.setCharAt(index, chars.get(b));
    }

    public byte[] toByteArray() {
        List<UByte> bytes = new ArrayList<>();
        for (int i = 0; i < binary.length(); i = i + 8) {
            UByte b = toUByte(i);
            b.revert();
            bytes.add(b);
        }
        return UByte.toArray(bytes);
    }

    public UByte toUByte(int from) {
        return UByte.parseUByte(subString(from, UByte.SIZE), 2);
    }

    public long getValue(int from, int length) {
        String bits = new StringBuilder(subString(from, length)).reverse().toString();
        return Long.parseLong(bits, 2);
    }

    private String subString(int from, int length) {
        return binary.substring(from, from + length);
    }

    @Override
    public String toString() {
        return binary.toString();
    }
}
