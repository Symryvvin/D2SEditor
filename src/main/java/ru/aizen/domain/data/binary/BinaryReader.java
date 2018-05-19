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
        return readByte(Byte.SIZE);
    }

    public short readShort() {
        return readShort(Short.SIZE);
    }

    public int readInt() {
        return readInt(Integer.SIZE);
    }

    public long readLong() {
        return readLong(Long.SIZE);
    }

    public byte readByte(int size) {
        byte b = binary.getByte(position, size);
        position += size;
        return b;
    }

    public short readShort(int size) {
        short s = binary.getShort(position, size);
        position += size;
        return s;
    }

    public int readInt(int size) {
        int i = binary.getInt(position, size);
        position += size;
        return i;
    }

    public long readLong(int size) {
        long l = binary.getLong(position, size);
        position += size;
        return l;
    }
}
