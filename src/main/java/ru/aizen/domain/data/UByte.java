package ru.aizen.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Very simple implementation of unsigned byte value in Java from 0 to 255 of integers. Only for this project
 */
public class UByte {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 255;
    public static final int SIZE = 8;

    private int value;

    public UByte(byte value) {
        this.value = Byte.toUnsignedInt(value);
    }

    public UByte(int value) throws NumberFormatException {
        if (value < MIN_VALUE || value > MAX_VALUE)
            throw new NumberFormatException(
                    "Value out of range. Value: \"" + value + "\"");
        this.value = value;
    }

    public int get() {
        return value;
    }

    public byte toByte() {
        return (byte) (value > 128 ? value & 0xff : value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UByte uByte = (UByte) o;
        return value == uByte.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public static List<UByte> getUnsignedBytes(byte[] bytes) {
        List<UByte> result = new ArrayList<>();
        for (byte b : bytes) {
            result.add(new UByte(b));
        }
        return result;
    }

    public static byte[] toArray(List<UByte> bytes) {
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i).toByte();
        }
        return result;
    }
}
