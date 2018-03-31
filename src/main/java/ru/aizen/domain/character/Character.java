package ru.aizen.domain.character;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.block.DataBlock;
import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.character.block.MetaBlock;
import ru.aizen.domain.data.BlockSize;
import ru.aizen.domain.data.CharacterData;
import ru.aizen.domain.data.DataReader;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class keep all data of character witch can be present on application forms
 */
public class Character {
    private CharacterData characterData;
    private HeaderBlock headerBlock;
    private MetaBlock metaBlock;
    private AttributesBlock attributesBlock;

    public Character() {
    }

    public void load(Path path) throws IOException, DecoderException {
        characterData = new CharacterData(path);
        characterData.read();
        DataReader reader = characterData.getReader();
        headerBlock = reader.readHeader();
        metaBlock = reader.readMeta();
        attributesBlock = reader.readAttributes();
    }

    public void save() throws DecoderException, IOException {
        metaBlock.setLevel(attributesBlock.getAttributes().get(AttributesBlock.LEVEL));
        List<DataBlock> blocks = new ArrayList<>();
        blocks.add(headerBlock);
        blocks.add(metaBlock);
        blocks.add(attributesBlock);
        blocks.addAll(stubs());
        Collections.sort(blocks);
        characterData.write(blocks);
    }

    /**
     * Temporary method to creating stub data block
     * @return
     */
    private List<DataBlock> stubs() throws DecoderException {
       int hotKeysMercenaryQuestWayPointsNPCStart = headerBlock.getSize() + metaBlock.getSize();
        int hotKeysMercenaryQuestWayPointsNPCSize = BlockSize.getAttributesBlockStart(characterData.getBytes()) -
                hotKeysMercenaryQuestWayPointsNPCStart;
        DataBlock hotKeysMercenaryQuestWayPointsNPC = characterData.createStubBlock(3,
                hotKeysMercenaryQuestWayPointsNPCStart,
                hotKeysMercenaryQuestWayPointsNPCSize);
        int skillsItemsStart = BlockSize.getSkillsBlockStart(characterData.getBytes());
        int skillsItemsSize = characterData.getBytes().length - skillsItemsStart;
        DataBlock skillsItems = characterData.createStubBlock(5,
                skillsItemsStart,
                skillsItemsSize);
        List<DataBlock> result = new ArrayList<>();
        result.add(hotKeysMercenaryQuestWayPointsNPC);
        result.add(skillsItems);
        return result;
    }

    public void backup() throws IOException {
        FileUtils.backup(characterData);
    }

    public void restore() throws IOException {
        FileUtils.restore(characterData);
    }

    public String getName() {
        return metaBlock.getName();
    }

    public Status getStatus() {
        return metaBlock.getStatus();
    }

    public Title getTitle() {
        return metaBlock.getTitle();
    }

    public CharacterClass getCharacterClass() {
        return metaBlock.getCharacterClass();
    }

    public CharacterData getCharacterData() {
        return characterData;
    }

    public AttributesBlock getAttributesBlock() {
        return attributesBlock;
    }

    public void setAttributesBlock(AttributesBlock attributesBlock) {
        this.attributesBlock = attributesBlock;
    }


}
