package ru.aizen.domain.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

public final class BinaryUtils {

    private BinaryUtils() {
        throw new AssertionError();
    }

    public static byte revertBits(byte x) {
        byte b = 0;
        for (int i = 0; i < 8; ++i) {
            b <<= 1;
            b |= (x & 1);
            x >>= 1;
        }
        return b;
    }

    public static String toBinaryString(byte b, boolean reverted) {
        if (reverted)
            b = revertBits(b);
        return String.format("%8s",
                Integer.toBinaryString(Byte.toUnsignedInt(b))).replace(' ', '0');
    }

    public static byte[] fromBinaryString(String bin, boolean reverted) {
        char[] chars = bin.toCharArray();
        ByteBuffer buffer = ByteBuffer.allocate(bin.length() / 8);
        for (int i = 0; i < chars.length; i = i + 8) {
            byte b =(byte) Integer.parseInt(new String(chars, i,  8), 2);
            if (reverted)
                b = revertBits(b);
            buffer.put(b);
        }
        buffer.flip();
        return buffer.array();
    }

    public static String getBitString(byte[] bytes, boolean reverted) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(BinaryUtils.toBinaryString(b, reverted));
        }
        return result.toString();
    }

    public static long bitsToInt(String bits) {
        return Long.parseLong(bits, 2);
    }

    public static long reversedBitsToInt(String bits) {
        return bitsToInt(StringUtils.reverse(bits));
    }
}
