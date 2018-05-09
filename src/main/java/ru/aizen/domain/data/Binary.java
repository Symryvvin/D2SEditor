package ru.aizen.domain.data;

import java.util.*;

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

    private char[] value;

    public Binary(byte[] bytes) {
        this.value = toCharArray(UByte.getUnsignedBytes(bytes));
    }

    public Binary(int size) {
        this.value = new char[size];
    }

    private char[] toCharArray(List<UByte> bytes) {
        bytes.forEach(UByte::revert);
        return bytes.stream()
                .map(UByte::toBinary)
                .reduce("", (f, s) -> f + s)
                .toCharArray();
    }

    public void fillByZero(int max) {
        StringBuilder builder = new StringBuilder(new String(value));
        while (builder.length() < max) {
            builder.append("0");
        }
        value = builder.toString().toCharArray();
    }


    public boolean getValueAt(int index) {
        return booleans.get(value[index]);
    }

    public void setValueAt(int index, boolean b) {
        value[index] = chars.get(b);
    }

    public byte[] toByteArray() {
        List<UByte> bytes = new ArrayList<>();
        for (int i = 0; i < value.length; i = i + 8) {
            UByte b = toUByte(i);
            b.revert();
            bytes.add(b);
        }
        return UByte.toArray(bytes);
    }

    public UByte toUByte(int from) {
        return UByte.parseUByte(new String(value, from, UByte.SIZE), 2);
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }
}
