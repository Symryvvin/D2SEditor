package ru.aizen.domain.util;

import org.apache.commons.lang3.StringUtils;

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

    public static String getBitString(byte[] bytes, boolean reverted) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(BinaryUtils.toBinaryString(b, reverted));
        }
        return result.toString();
    }

    public static int bitsToInt(String bits) {
        return Integer.parseInt(bits, 2);
    }

    public static int reversedBitsToInt(String bits) {
        return bitsToInt(StringUtils.reverse(bits));
    }
}
