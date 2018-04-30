package ru.aizen.domain.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Simplify work with byte buffer
 */
public class ByteReader {
    private ByteBuffer byteBuffer;
    private ByteOrder order;

    /**
     * Constructor for ByteReader
     * @param bytes bytes for read
     * @param order order of reading bytes
     */
    public ByteReader(byte[] bytes, ByteOrder order) {
        this.byteBuffer = ByteBuffer.wrap(bytes);
        this.order = order;
        byteBuffer.order(order);
    }

    /**
     * Read Int32 value from 4 bytes
     * @return integer value
     */
    public Integer readInt() {
        return byteBuffer.getInt();
    }

    /**
     * Read Int16 value from 2 bytes
     * @return short value
     */
    public Short readShort() {
        return byteBuffer.getShort();
    }

    /**
     * Read Int8 value from 1 byte
     * @return byte value
     */
    public Byte readByte() {
        return byteBuffer.get();
    }

    /**
     * Read array of bytes from current position
     * @param length length of byte array
     * @return byte array
     */
    public byte[] readBytes(int length) {
        byte[] result = new byte[length];
        byteBuffer.get(result);
        return result;
    }

    /**
     * Read String object from current position
     * @param length count of byte
     * @return string literal
     */
    public String readString(int length) {
        return new String(readBytes(length));
    }

    /**
     * Skip bytes from current position and set new position of cursor
     * @param amount amount of skipped bytes
     */
    public void skip(int amount) {
        byteBuffer.position(byteBuffer.position() + amount);
    }

    public ByteReader getByteReaderForBytes(int size) {
        return new ByteReader(readBytes(size), this.order);
    }

    /**
     * Get current position
     * @return current position
     */
    public int position() {
        return byteBuffer.position();
    }

    /**
     * Set new position
     * @param position new position value
     */
    public void to(int position) {
        byteBuffer.position(position);
    }

    /**
     * Get byte array from reader
     * @return byte array
     */
    public byte[] getBytes() {
        return byteBuffer.array();
    }
}
