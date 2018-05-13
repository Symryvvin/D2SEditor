package ru.aizen.domain.data.binary;

import ru.aizen.domain.data.UByte;

import java.util.ArrayList;
import java.util.List;

public class Binary {
    public static final char ZERO = '0';
    public static final char ONE = '1';

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

    public Binary(String value) {
        this.binary = new StringBuilder(value);
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

    public void fillToFullBytes() {
        while (binary.length() % 8 != 0) {
            binary.append("0");
        }
    }

    public void reverse() {
        binary.reverse();
    }

    public void putBinary(String value) {
        binary.append(value);
    }

    public void putBinary(Binary value) {
        putBinary(value.toString());
    }

    public boolean getValueAt(int index) {
        return binary.charAt(index) == ONE;
    }

    public void setValueAt(int index, boolean b) {
        char ch = b ? ONE : ZERO;
        binary.setCharAt(index, ch);
    }

    public byte[] toByteArray() {
        List<UByte> bytes = new ArrayList<>();
        for (int i = 0; i < binary.length(); i = i + 8) {
            UByte b = UByte.parseUByte(binary.substring(i, i + UByte.SIZE), 2);
            b.revert();
            bytes.add(b);
        }
        return UByte.toArray(bytes);
    }

    public int length() {
        return binary.length();
    }

    public String substring(int start, int end) {
        return binary.substring(start, end);
    }

    @Override
    public String toString() {
        return binary.toString();
    }
}
