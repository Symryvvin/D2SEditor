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

    public String toBinary() {
        return String.format("%" + SIZE + "s",
                Integer.toBinaryString(value)).replace(' ', Binary.ZERO);
    }

    public void revert() {
        revertBits(toByte());
    }

    private void revertBits(byte x) {
        byte b = 0;
        for (int i = 0; i < SIZE; ++i) {
            b <<= 1;
            b |= (x & 1);
            x >>= 1;
        }
        value = Byte.toUnsignedInt(b);
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

    public static UByte parseUByte(String s, int radix) throws NumberFormatException {
        int i = Integer.parseInt(s, radix);
        return new UByte(i);
    }
}
