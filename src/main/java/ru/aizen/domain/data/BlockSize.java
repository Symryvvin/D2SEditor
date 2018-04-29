package ru.aizen.domain.data;

import ru.aizen.domain.util.BinHexUtils;

import java.util.Collections;
import java.util.List;

public final class BlockSize {
    public static final String ATTRIBUTES_BLOCK_START = "6766";
    public static final String SKILLS_BLOCK_START = "6966";

    private BlockSize() {
        throw new AssertionError();
    }

    public static int getAttributesBlockStart(byte[] data) {
        return getPositionInDataByHexCode(ATTRIBUTES_BLOCK_START, data);
    }

    public static int getSkillsBlockStart(byte[] data) {
        return getPositionInDataByHexCode(SKILLS_BLOCK_START, data);
    }

    /**
     * Calculate position of byte sub array from hex string in data byte array
     * @param hex hex code
     * @return position value
     */
    private static int getPositionInDataByHexCode(String hex, byte[] data) {
        byte[] subArray = BinHexUtils.decodeHex(hex);
        List<Integer> arrayList = BinHexUtils.getUnsignedByteList(data);
        List<Integer> subArrayList = BinHexUtils.getUnsignedByteList(subArray);
        return Collections.indexOfSubList(arrayList, subArrayList);
    }
}
