package ru.aizen.domain.character;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.block.*;
import ru.aizen.domain.dao.CharacterDao;
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
@Component
public class Character {
    private CharacterData characterData;
    private HeaderBlock headerBlock;
    private MetaBlock metaBlock;
    private AttributesBlock attributesBlock;
    private SkillsBlock skillsBlock;

    private final CharacterDao characterDao;

    @Autowired
    public Character(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }

    public void load(Path path) throws IOException, DecoderException {
        characterData = new CharacterData(path);
        characterData.read();
        DataReader reader = characterData.getReader();
        headerBlock = reader.readHeader();
        metaBlock = reader.readMeta();
        attributesBlock = reader.readAttributes();
        skillsBlock = reader.readSkills();
    }

    public void save() throws DecoderException, IOException {
        metaBlock.setLevel(attributesBlock.getAttributes().get(AttributesBlock.LEVEL).intValue());
        List<DataBlock> blocks = new ArrayList<>();
        blocks.add(headerBlock);
        blocks.add(metaBlock);
        blocks.add(attributesBlock);
        blocks.add(skillsBlock);
        blocks.addAll(stubs());
        Collections.sort(blocks);
        characterData.write(blocks);
    }

    /**
     * Temporary method to creating stub data block
     * @return stub block
     */
    private List<DataBlock> stubs() throws DecoderException {
        int hotKeysMercenaryQuestWayPointsNPCStart = headerBlock.getSize() + metaBlock.getSize();
        int hotKeysMercenaryQuestWayPointsNPCSize = BlockSize.getAttributesBlockStart(characterData.getBytes()) -
                hotKeysMercenaryQuestWayPointsNPCStart;
        DataBlock hotKeysMercenaryQuestWayPointsNPC = characterData.createStubBlock(3,
                hotKeysMercenaryQuestWayPointsNPCStart,
                hotKeysMercenaryQuestWayPointsNPCSize);
        int itemsStart = BlockSize.getSkillsBlockStart(characterData.getBytes()) + skillsBlock.getSize();
        int itemsSize = characterData.getBytes().length - itemsStart;
        DataBlock skillsItems = characterData.createStubBlock(6,
                itemsStart,
                itemsSize);
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
        return characterDao.getCharacterClassByValue(metaBlock.getCharacterClass());
    }

    public void setName(String name) {
        metaBlock.setName(name);
    }

    public void setStatus(Status status) {
        metaBlock.setStatus(status);
    }

    public void setTitle(Title title) {
        metaBlock.setTitle(title);
    }

    public void setCharacterClass(CharacterClass characterClass) {
        byte value = characterDao.getValueByCharacterClass(characterClass);
        metaBlock.setCharacterClass(value);
    }

    public CharacterData getCharacterData() {
        return characterData;
    }

    public AttributesBlock getAttributesBlock() {
        return attributesBlock;
    }

    public SkillsBlock getSkillsBlock() {
        return skillsBlock;
    }

    public void setAttributesBlock(AttributesBlock attributesBlock) {
        this.attributesBlock = attributesBlock;
    }


}
