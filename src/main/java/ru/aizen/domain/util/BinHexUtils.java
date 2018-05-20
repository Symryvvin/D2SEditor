package ru.aizen.domain.util;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;

public final class BinHexUtils {
    private BinHexUtils() {
        throw new AssertionError();
    }

    public static String getFormattedHexString(byte[] data) {
        String hex = Hex.encodeHexString(data);
        List<String> hexByte = new ArrayList<>();
        char[] hexChars = hex.toCharArray();
        for (int i = 1; i < hexChars.length; i = i + 2) {
            hexByte.add(String.valueOf(new char[]{hexChars[i - 1], hexChars[i]}).toUpperCase());
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexByte.size(); i++) {
            if (i != 0 && (i + 1) % 16 == 0) {
                result.append(hexByte.get(i)).append("\n");
            } else {
                result.append(hexByte.get(i)).append("\t");
            }
        }
        return result.toString();
    }

    public static byte[] getDecodeHexString(String hex) {
        hex = hex.replace("\t", "");
        return decodeHex(hex);
    }

    public static byte[] decodeHex(String hex) {
        char[] data = hex.toCharArray();
        int len = data.length;
        byte[] out = new byte[len >> 1];
        int i = 0;

        for (int j = 0; j < len; ++i) {
            int f = toDigit(data[j]) << 4;
            ++j;
            f |= toDigit(data[j]);
            ++j;
            out[i] = (byte) (f & 255);
        }

        return out;
    }

    private static int toDigit(char ch) {
        return Character.digit(ch, 16);
    }

    public static List<Integer> getUnsignedByteList(byte[] bytes) {
        List<Integer> result = new ArrayList<>();
        for (byte b : bytes) {
            result.add(Byte.toUnsignedInt(b));
        }
        return result;
    }
}
