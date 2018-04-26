package ru.aizen.domain.data;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.character.block.*;

import java.nio.ByteBuffer;

import static ru.aizen.domain.data.BlockSize.*;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 */
public class DataReader {

    private ByteBuffer data;

    public DataReader(byte[] data) {
        this.data = ByteBuffer.wrap(data);
    }

    /**
     * Create stub data block from A bytes with B size
     */
    public StubBlock createStubBlock(int order, int start, int size) {
        return (StubBlock) new StubBlock(order).parse(getBlockBuffer(start, size));
    }

    /**
     * Read header of file from 0 to 16 bytes
     * @return header object
     */
    public HeaderBlock readHeader() {
        return (HeaderBlock) new HeaderBlock(1).parse(getBlockBuffer(HEADER_BLOCK_OFFSET, HEADER_BLOCK_SIZE));
    }

    /**
     * Read meta block from 12 to 56 bytes
     * @return meta object
     */
    public MetaBlock readMeta() {
        return (MetaBlock) new MetaBlock(2).parse(getBlockBuffer(META_BLOCK_OFFSET, META_BLOCK_SIZE));
    }

    /**
     * Attribute block placed between [67 66]
     * and [69 66] (start of skills block) bytes
     * @return block of attributes
     */
    public AttributesBlock readAttributes() throws DecoderException {
        int start = BlockSize.getAttributesBlockStart(data.array());
        int end = BlockSize.getSkillsBlockStart(data.array());
        return (AttributesBlock) new AttributesBlock(4).parse(getBlockBuffer(start, end - start));
    }

    /**
     * Attribute block start from [69 66] and size is 30
     * @return block of skills
     */
    public SkillsBlock readSkills() throws DecoderException {
        int start = BlockSize.getSkillsBlockStart(data.array());
        return (SkillsBlock) new SkillsBlock(5).parse(getBlockBuffer(start, BlockSize.SKILLS_BLOCK_SIZE));
    }

    private ByteBuffer getBlockBuffer(int offset, int size) {
        byte[] bytes = new byte[size];
        data.position(offset);
        data.get(bytes, 0, size);
        return ByteBuffer.wrap(bytes);
    }

    public int getDataSize() {
        return data.capacity();
    }

    public void setData(byte[] data) {
        this.data = ByteBuffer.wrap(data);
    }
}
