package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import ru.aizen.domain.attribute.Attribute;
import ru.aizen.domain.attribute.AttributesBlock;
import ru.aizen.domain.character.DataBlock;
import ru.aizen.domain.character.HeaderBlock;
import ru.aizen.domain.character.MetaBlock;
import ru.aizen.domain.util.BinHexUtils;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 */
public class DataReader {
    private static final int HEADER_BLOCK_OFFSET = 0;
    private static final int HEADER_BLOCK_SIZE = 16;
    private static final int META_BLOCK_OFFSET = HEADER_BLOCK_SIZE;
    private static final int META_BLOCK_SIZE = 40;
    private static final String ATTRIBUTES_BLOCK_START = "6766";
    private static final String SKILLS_BLOCK_START = "6966";

    private ByteBuffer data;

    public DataReader(byte[] data) {

        this.data = ByteBuffer.wrap(data);
    }

    /**
     * Read header of file from 0 to 16 bytes
     * @return header object
     */
    public HeaderBlock readHeader() {
        return (HeaderBlock) new HeaderBlock().parse(getBlockBuffer(HEADER_BLOCK_OFFSET, HEADER_BLOCK_SIZE));
    }

    /**
     * Read meta block from 12 to 56 bytes
     * @return meta object
     */
    public MetaBlock readMeta() {
        return (MetaBlock) new MetaBlock().parse(getBlockBuffer(META_BLOCK_OFFSET, META_BLOCK_SIZE));
    }

    /**
     * Attribute block placed between [67 66] (+2 because 67 66 is not interested value)
     * and [69 66] (start of skills block) bytes
     * @return bytes of attributes
     */
    public AttributesBlock readAttributes() throws DecoderException {
        int start = getPositionInDataByHexCode(ATTRIBUTES_BLOCK_START) + 2;
        int end = getPositionInDataByHexCode(SKILLS_BLOCK_START);
        return (AttributesBlock) new AttributesBlock().parse(getBlockBuffer(start, end - start));
    }

    private ByteBuffer getBlockBuffer(int offset, int size){
        byte[] bytes = new byte[size];
        data.position(offset);
        data.get(bytes, 0, size);
        return ByteBuffer.wrap(bytes);
    }

    /**
     * Calculate position of byte sub array from hex string in data byte array
     * @param hex hex code
     * @return position value
     */
    private int getPositionInDataByHexCode(String hex) throws DecoderException {
        byte[] subArray = Hex.decodeHex(hex);
        List<Integer> arrayList = BinHexUtils.getUnsignedByteList(data.array());
        List<Integer> subArrayList = BinHexUtils.getUnsignedByteList(subArray);
        return Collections.indexOfSubList(arrayList, subArrayList);
    }


}
