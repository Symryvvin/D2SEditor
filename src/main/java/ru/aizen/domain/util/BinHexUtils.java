package ru.aizen.domain.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import ru.aizen.domain.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BinHexUtils {
    private BinHexUtils() {
        throw new AssertionError();
    }

    public static String getFormattedHexString(byte[] data) {
        String hex = HexBin.encode(data);
        List<String> hexByte = new ArrayList<>();
        char[] hexChars = hex.toCharArray();
        for (int i = 1; i < hexChars.length; i = i + 2) {
            hexByte.add(String.valueOf(new char[]{hexChars[i - 1], hexChars[i]}));
        }
        return hexByte.stream()
                .reduce("", (first, second) ->
                        first + second + "\t");
    }

    public static byte[] getDecodeHexString(String hex) {
        hex = hex.replace("\t", "");
        return HexBin.decode(hex);
    }

    public static int calculateCheckSum(Hero hero) {
        byte[] preData = hero.getPreData();
        byte[] postData = hero.getPostData();
        byte[] zero = new byte[4];

        Arrays.fill(zero, (byte) 0);
        int preCheckSum = BinHexUtils.getCheckSum(preData, 0);
        int onCheckSum = BinHexUtils.getCheckSum(zero, preCheckSum);
        hero.setCheckSum(BinHexUtils.getCheckSum(postData, onCheckSum));
        return hero.getCheckSum();
    }

    public static int getCheckSum(byte[] data, int start) {
        int acc = start;
        for (Byte b : data) {
            int by = b.intValue() < 0 ? b.intValue() + 256 : b.intValue();
            acc = (acc << 1) + by + ((acc < 0) ? 1 : 0);
        }
        return acc;
    }
}
