package ru.aizen.domain.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Integer> getBits(byte[] bytes, boolean reverted) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(BinaryUtils.toBinaryString(b, reverted));
        }
        return result.toString()
                .chars()
                .map(i -> i == '1' ? 1 : 0)
                .boxed()
                .collect(Collectors.toList());
    }

    public static int bitsToInt(List<Integer> bits) {
        return Integer.parseInt(bits.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b), 2);
    }

    public static int reversedBitsToInt(List<Integer> bits) {
        Collections.reverse(bits);
        return bitsToInt(bits);
    }

    public static int bitsToInt(String hex) {
        try {
            return bitsToInt(getBits(Hex.decodeHex(hex), false));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
