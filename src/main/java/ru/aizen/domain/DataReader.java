package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import ru.aizen.domain.character.Header;
import ru.aizen.domain.character.Meta;
import ru.aizen.domain.util.BinHexUtils;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 */
public class DataReader {
    private static final int HEADER_BLOCK_START = 0;
    private static final int HEADER_BLOCK_SIZE = 16;
    private static final int META_BLOCK_START = HEADER_BLOCK_SIZE;
    private static final int META_BLOCK_SIZE = 40;
    private static final String ATTRIBUTES_BLOCK_START = "6766";
    private static final String SKILLS_BLOCK_START = "6966";

    private ByteBuffer data;

    public DataReader(byte[] data) {

        this.data = ByteBuffer.wrap(data);
    }

    /**
     * Read header of file from 0 to 16 bytes
     * @return bytes of header block
     */
    public Header readHeader() {
        byte[] header = new byte[HEADER_BLOCK_SIZE];
        data.position(HEADER_BLOCK_START);
        data.get(header, 0, header.length);
        return new Header(header);
    }

    /**
     * Read meta block from 12 to 56 bytes
     * @return bytes of header block
     */
    public Meta readMeta() {
        byte[] meta = new byte[META_BLOCK_SIZE];
        data.position(META_BLOCK_START);
        data.get(meta, 0, meta.length);
        return new Meta(meta);
    }

    /**
     * Attribute block placed between [67 66] (+2 because 67 66 is not interested value)
     * and [69 66] (start of skills block) bytes
     * @return bytes of attributes
     */
    public byte[] getAttributesBlock() throws DecoderException {
        int start = getPositionInDataByHexCode(ATTRIBUTES_BLOCK_START) + 2;
        int end = getPositionInDataByHexCode(SKILLS_BLOCK_START);
        byte[] attributes = new byte[end - start];
        data.position(start);
        data.get(attributes, 0, attributes.length);
        return attributes;
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
