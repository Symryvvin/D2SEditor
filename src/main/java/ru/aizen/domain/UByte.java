package ru.aizen.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Very simple implementation of unsigned byte value in Java from 0 to 255 of integers. Only for this project
 */
public class UByte {
    public static final int MIN_VALUE = 0x00;
    public static final int MAX_VALUE = 0xff;
    public static final int SIZE = 8;

    private final int value;

    public UByte(byte value) {
        this.value = Byte.toUnsignedInt(value);
    }

    public int get() {
        return value;
    }

    public byte toByte() {
        return (byte) (value > 128 ? value - 256 : value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
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
