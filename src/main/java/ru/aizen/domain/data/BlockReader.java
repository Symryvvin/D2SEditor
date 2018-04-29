package ru.aizen.domain.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.UByte;
import ru.aizen.domain.character.block.*;
import ru.aizen.domain.dao.AttributeDao;
import ru.aizen.domain.dao.SkillDao;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 */
@Component
public class BlockReader {
    private byte[] bytes;

    private final AttributeDao attributeDao;
    private final SkillDao skillDao;

    @Autowired
    public BlockReader(AttributeDao attributeDao, SkillDao skillDao) {
        this.attributeDao = attributeDao;
        this.skillDao = skillDao;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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
        return (HeaderBlock) new HeaderBlock(1).parse(getBlockBuffer(HeaderBlock.HEADER_BLOCK_OFFSET,
                HeaderBlock.HEADER_BLOCK_SIZE));
    }

    /**
     * Read meta block from 12 to 56 bytes
     * @return meta object
     */
    public MetaBlock readMeta() {
        return (MetaBlock) new MetaBlock(2).parse(getBlockBuffer(MetaBlock.META_BLOCK_OFFSET, MetaBlock.META_BLOCK_SIZE));
    }

    /**
     * Attribute block placed between [67 66]
     * and [69 66] (start of skills block) bytes
     * @return block of attributes
     */
    public AttributesBlock readAttributes() {
        int offset = getSubArrayPosition(AttributesBlock.identifier);
        int size = getSubArrayPosition(SkillsBlock.identifier) - offset;
        return new AttributesBlock(4, attributeDao).parse(getBlockBuffer(offset, size));
    }

    /**
     * Attribute block start from [69 66] and size is 30
     * @return block of skills
     */
    public SkillsBlock readSkills() {
        int offset = getSubArrayPosition(SkillsBlock.identifier);
        return new SkillsBlock(5, skillDao).parse(getBlockBuffer(offset, SkillsBlock.SKILLS_BLOCK_SIZE));
    }

    private ByteBuffer getBlockBuffer(int offset, int size) {
        return ByteBuffer.wrap(Arrays.copyOfRange(bytes, offset, size + offset));
    }

    /**
     * Calculate position of byte sub array in data byte array
     * @param subArray sub array
     * @return position value
     */
    public int getSubArrayPosition(byte[] subArray) {
        List<UByte> arrayList = UByte.getUnsignedBytes(bytes);
        List<UByte> subArrayList = UByte.getUnsignedBytes(subArray);
        return Collections.indexOfSubList(arrayList, subArrayList);
    }
}
