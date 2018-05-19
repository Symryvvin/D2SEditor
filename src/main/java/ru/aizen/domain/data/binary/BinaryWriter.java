package ru.aizen.domain.data.binary;

public class BinaryWriter {
    private Binary binary;
    private int capacity;

    public BinaryWriter(int capacity) {
        this.capacity = capacity;
        binary = new Binary(capacity);
    }

    public BinaryWriter() {
        binary = new Binary();
        capacity = 0;
    }

    public void writeByte(byte value, int size) {
        binary.putLong(value, size);
        capacity += size;
    }

    public void writeShort(short value, int size) {
        binary.putLong(value, size);
        capacity += size;
    }

    public void writeInt(int value, int size) {
        binary.putLong(value, size);
        capacity += size;
    }

    public void writeLong(long value, int size) {
        binary.putLong(value, size);
        capacity += size;
    }

    public void writeHex(String hex, int size) {
        long value = Long.parseLong(hex, 16);
        binary.putLong(value, size);
        capacity += size;
    }


    public void writeByte(byte value) {
        writeByte(value, Byte.SIZE);
    }

    public void writeShort(short value) {
        writeShort(value, Short.SIZE);
    }

    public void writeInt(int value) {
        writeInt(value, Integer.SIZE);
    }

    public void writeLong(long value) {
        writeLong(value, Long.SIZE);

    }

    public Binary getBinary() {
        return binary;
    }

    public int getCapacity() {
        return capacity;
    }
}
