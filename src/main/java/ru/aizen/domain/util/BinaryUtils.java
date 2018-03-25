package ru.aizen.domain.util;

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
}
