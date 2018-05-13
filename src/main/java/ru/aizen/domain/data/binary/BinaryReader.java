package ru.aizen.domain.data.binary;

public class BinaryReader {
    private Binary binary;
    private int position;

    public BinaryReader(Binary binary) {
        this.binary = binary;
        this.position = 0;
    }

    public BinaryReader(byte[] data) {
        this.binary = new Binary(data);
        this.position = 0;
    }

    public byte readByte() {
        return Byte.parseByte(getValue(Byte.SIZE), 2);
    }

    public short readShort() {
        return Short.parseShort(getValue(Short.SIZE), 2);
    }

    public int readInt() {
        return Integer.parseInt(getValue(Integer.SIZE), 2);
    }

    public long readLong() {
        return Long.parseLong(getValue(Long.SIZE), 2);
    }

    public byte readByte(int length) {
        return Byte.parseByte(getValue(length), 2);
    }

    public short readShort(int length) {
        return Short.parseShort(getValue(length), 2);
    }

    public int readInt(int length) {
        return Integer.parseInt(getValue(length), 2);
    }

    public long readLong(int length) {
        return Long.parseLong(getValue(length), 2);
    }

    public String getValue(int length) {
        Binary result = new Binary(binary.substring(position, position + length));
        result.reverse();
        position += length;
        return result.toString();
    }
}
