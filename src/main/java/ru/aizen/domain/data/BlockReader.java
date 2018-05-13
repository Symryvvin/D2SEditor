package ru.aizen.domain.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.block.*;
import ru.aizen.domain.dao.AttributeDao;
import ru.aizen.domain.dao.CharacterDao;
import ru.aizen.domain.dao.SkillDao;
import ru.aizen.domain.dao.WaypointDao;
import ru.aizen.domain.exception.ValidatorException;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class keep save file data and can give byte block of different parts saved hero data
 */
@Component
public class BlockReader {
    private byte[] bytes;

    private final CharacterDao characterDao;
    private final WaypointDao waypointDao;
    private final AttributeDao attributeDao;
    private final SkillDao skillDao;

    @Autowired
    public BlockReader(CharacterDao characterDao,
                       WaypointDao waypointDao,
                       AttributeDao attributeDao,
                       SkillDao skillDao) {
        this.characterDao = characterDao;
        this.waypointDao = waypointDao;
        this.attributeDao = attributeDao;
        this.skillDao = skillDao;
    }

    /**
     * Read data to bytes field
     * @throws IOException
     */
    public void read(Path path) throws IOException {
        bytes = Files.readAllBytes(path);
    }

    /**
     * Create stub data block from A bytes with B size
     */
    public UnknownBlock unknownBlock(int order, int start, int size) {
        return new UnknownBlock(order).parse(getByteReader(start, size));
    }

    /**
     * Read header of file from 0 to 16 bytes
     * @return header object
     */
    public HeaderBlock readHeader() throws ValidatorException {
        HeaderBlock header = new HeaderBlock()
                .parse(getByteReader(HeaderBlock.HEADER_BLOCK_OFFSET, HeaderBlock.HEADER_BLOCK_SIZE));
        header.validate();
        return header;
    }

    /**
     * Read meta block from 12 to 56 bytes
     * @return meta object
     */
    public MetaBlock readMeta() {
        return new MetaBlock(characterDao)
                .parse(getByteReader(MetaBlock.META_BLOCK_OFFSET, MetaBlock.META_BLOCK_SIZE));
    }

    public HotKeysBlock readHotKeys() {
        return new HotKeysBlock()
                .parse(getByteReader(HotKeysBlock.OFFSET, HotKeysBlock.SIZE));
    }

    public MapBlock readMap() {
        return new MapBlock(waypointDao)
                .parse(getByteReader(MapBlock.OFFSET, MapBlock.SIZE));
    }

    public MercenaryBlock readMercenary() {
        return new MercenaryBlock()
                .parse(getByteReader(MercenaryBlock.OFFSET, MercenaryBlock.SIZE));
    }

    public QuestsBlock readQuests() {
        return new QuestsBlock()
                .parse(getByteReader(QuestsBlock.OFFSET, QuestsBlock.SIZE));
    }

    public WaypointsBlock readWaypoints() {
        return new WaypointsBlock(waypointDao)
                .parse(getByteReader(WaypointsBlock.OFFSET, WaypointsBlock.SIZE));
    }

    public NPCBlock readNPC() {
        return new NPCBlock()
                .parse(getByteReader(NPCBlock.OFFSET, NPCBlock.SIZE));
    }

    /**
     * Attribute block placed between [67 66]
     * and [69 66] (start of skills block) bytes
     * @return block of attributes
     */
    public AttributesBlock readAttributes() {
        int offset = getSubArrayPosition(AttributesBlock.identifier);
        int size = getSubArrayPosition(SkillsBlock.identifier) - offset;
        return new AttributesBlock(attributeDao)
                .parse(getByteReader(offset, size));
    }

    /**
     * Attribute block start from [69 66] and size is 30
     * @return block of skills
     */
    public SkillsBlock readSkills() {
        int offset = getSubArrayPosition(SkillsBlock.identifier);
        return new SkillsBlock(skillDao)
                .parse(getByteReader(offset, SkillsBlock.SKILLS_BLOCK_SIZE));
    }

    public InventoryBlock readInventory() {
        int offset = getSubArrayPosition(InventoryBlock.identifier);
        return new InventoryBlock(attributeDao)
                .parse(getByteReader(offset, bytes.length - offset));
    }

    private ByteReader getByteReader(int offset, int size) {
        return new ByteReader(Arrays.copyOfRange(bytes, offset, size + offset), ByteOrder.LITTLE_ENDIAN);
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

    public byte[] getBytes() {
        return bytes;
    }


}
