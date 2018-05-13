package ru.aizen.domain.data.binary;

public class BinaryWriter {
    private Binary binary;
    private int capacity;

    public BinaryWriter(int allocate) {
        binary = new Binary(allocate);
        binary.fillByZero(allocate);
    }

    public BinaryWriter() {
        binary = new Binary();
    }

    public void writeByte(byte value, int length) {
        writeValue(value, length);
    }

    public void writeShort(short value, int length) {
        writeValue(value, length);
    }

    public void writeInt(int value, int length) {
        writeValue(value, length);
    }

    public void writeLong(long value, int length) {
        writeValue(value, length);
    }

    public void writeHex(String hex) {
        long value = Long.parseLong(hex, 16);
        writeValue(value, hex.length());
    }


    public void writeByte(byte value) {
        writeValue(value, Byte.SIZE);
    }

    public void writeShort(short value) {
        writeValue(value, Short.SIZE);
    }

    public void writeInt(int value) {
        writeValue(value, Integer.SIZE);
    }

    public void writeLong(long value) {
        writeValue(value, Long.SIZE);

    }

    private void writeValue(long value, int length) {
        Binary result = new Binary();
        result.putBinary(Long.toBinaryString(value));
        result.reverse();
        result.fillByZero(length);
        binary.putBinary(result);
        capacity = binary.length();
    }

    public Binary getBinary() {
        binary.fillToFullBytes();
        return binary;
    }

}
