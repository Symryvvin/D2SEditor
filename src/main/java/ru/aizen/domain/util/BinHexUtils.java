package ru.aizen.domain.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import ru.aizen.domain.HeroData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static int calculateCheckSum(HeroData heroData) {
        byte[] zero = new byte[4];
        Arrays.fill(zero, (byte) 0);
        List<Integer> preData = getUnsignedByteList(heroData.getPreData());
        List<Integer> zeroCheckSum = getUnsignedByteList(zero);
        List<Integer> postData = getUnsignedByteList(heroData.getPostData());
        List<Integer> fullData = Stream.of(preData, zeroCheckSum, postData)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        heroData.setCheckSum(getCheckSum(fullData));
        return heroData.getCheckSum();
    }

    private static List<Integer> getUnsignedByteList(byte[] bytes){
        List<Integer> result = new ArrayList<>();
        for(byte b : bytes){
            result.add(Byte.toUnsignedInt(b));
        }
        return result;
    }

    private static int getCheckSum(List<Integer> byteList) {
        return byteList.stream()
                .reduce(0, (first, second) -> (first << 1) + second + ((first < 0) ? 1 : 0));
    }
}
