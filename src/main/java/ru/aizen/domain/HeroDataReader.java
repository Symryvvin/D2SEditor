package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import ru.aizen.domain.util.BinHexUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 *
 */
public class HeroDataReader {
    private static final String ATTRIBUTES_BLOCK_START = "6766";
    private static final String SKILLS_BLOCK_START = "6966";

    private byte[] data;

    public HeroDataReader(byte[] data) {
        this.data = data;
    }

    /**
     * Attribute block placed between [67 66] (+2 because 67 66 is not interested value)
     * and [69 66] (start of skills block) bytes
     * @return bytes of attributes
     */
    public byte[] getAttributesBlock() throws DecoderException {
        int start = getPositionInDataByHexCode(ATTRIBUTES_BLOCK_START) + 2;
        int end = getPositionInDataByHexCode(SKILLS_BLOCK_START);
        return Arrays.copyOfRange(data, start, end);
    }

    /**
     * Calculate position of byte sub array from hex string in data byte array
     * @param hex hex code
     * @return position value
     */
    private int getPositionInDataByHexCode(String hex) throws DecoderException {
        byte[] subArray = Hex.decodeHex(hex);
        List<Integer> arrayList = BinHexUtils.getUnsignedByteList(data);
        List<Integer> subArrayList = BinHexUtils.getUnsignedByteList(subArray);
        return Collections.indexOfSubList(arrayList, subArrayList);
    }


}
