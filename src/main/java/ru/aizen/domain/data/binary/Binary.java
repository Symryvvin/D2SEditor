package ru.aizen.domain.data.binary;

import java.util.Arrays;
import java.util.BitSet;

/**
 * Wrapper for simply work with BitSet - representation data in binary format
 */
public class Binary {
    private BitSet bits;
    private int capacity;

    public Binary() {
        this.capacity = 0;
        this.bits = new BitSet(capacity);
    }

    public Binary(byte[] bytes) {
        this.bits = BitSet.valueOf(bytes);
        this.capacity = bytes.length * Byte.SIZE;
    }

    public Binary(int capacity) {
        this.bits = new BitSet(capacity);
        this.capacity = capacity;
    }

    public void setTrue(int index) {
        bits.set(index);
    }

    public void setFalse(int index) {
        bits.clear(index);
    }

    public void set(int index, boolean value) {
        bits.set(index, value);
    }

    public boolean get(int index) {
        return bits.get(index);
    }

    public void addBinary(Binary binary) {
        for (int i = 0; i < binary.capacity; i++) {
            if (binary.get(i))
                setTrue(capacity + i);
        }
        capacity += binary.capacity;
    }

    public byte getByte(int from, int length) {
        commonCheckLength(length);
        if (length > Byte.SIZE)
            throw new IllegalArgumentException("Trying to get long value more than 8 bits. Length = " + length);
        return (byte) getValue(bits.get(from, from + length));
    }

    public byte getByte(int from) {
        return getByte(from, Byte.SIZE);
    }

    public short getShort(int from, int length) {
        commonCheckLength(length);
        if (length > Short.SIZE)
            throw new IllegalArgumentException("Trying to get long value more than 16 bits. Length = " + length);
        return (short) getValue(bits.get(from, from + length));
    }

    public short getShort(int from) {
        return getShort(from, Short.SIZE);
    }

    public int getInt(int from, int length) {
        commonCheckLength(length);
        if (length > Integer.SIZE)
            throw new IllegalArgumentException("Trying to get long value more than 32 bits. Length = " + length);
        return (int) getValue(bits.get(from, from + length));
    }

    public int getInt(int from) {
        return getInt(from, Integer.SIZE);
    }

    public long getLong(int from, int length) {
        commonCheckLength(length);
        if (length > Long.SIZE)
            throw new IllegalArgumentException("Trying to get long value more than 64 bits. Length = " + length);
        return getValue(bits.get(from, from + length));
    }

    public long getLong(int from) {
        return getLong(from, Long.SIZE);
    }

    private long getValue(BitSet set) {
        long[] array = set.toLongArray();
        return array.length == 0 ? 0 : array[0];
    }

    private void commonCheckLength(int length) {
        if (length < 0)
            throw new IllegalArgumentException("Length of bits < 0 value = " + length);
    }

    public void putLong(long l) {
        addBinary(new Binary(longToBytes(l)));
    }

    public void putLong(long l, int length) {
        Binary binary = new Binary(longToBytes(l));
        binary.capacity(length);
        addBinary(binary);
    }

    private byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }


    public void capacity(int value) {
        capacity = value;
    }

    public int capacity() {
        return capacity;
    }

    public byte[] array() {
        byte[] bytes = bits.toByteArray();

        if (capacity > bytes.length * Byte.SIZE) {
            byte[] result = new byte[capacity / 8];
            Arrays.fill(result, (byte) 0);
            for (int i = 0; i < bytes.length; i++) {
                result[i] = bytes[i];
            }
            return result;
        }
        return bytes;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (i != 0 && i % 8 == 0)
                builder.append(" ");
            if (bits.get(i))
                builder.append("1");
            else
                builder.append("0");
        }
        return builder.toString();
    }


}