package ru.aizen.domain.util;

import org.apache.commons.codec.DecoderException;
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
            hexByte.add(String.valueOf(new char[]{hexChars[i - 1], hexChars[i]}));
        }
        return hexByte.stream()
                .reduce("", (first, second) ->
                        first + second + "\t");
    }

    public static byte[] getDecodeHexString(String hex) throws DecoderException {
        hex = hex.replace("\t", "");
        return Hex.decodeHex(hex);
    }

    public static List<Integer> getUnsignedByteList(byte[] bytes) {
        List<Integer> result = new ArrayList<>();
        for (byte b : bytes) {
            result.add(Byte.toUnsignedInt(b));
        }
        return result;
    }
}
