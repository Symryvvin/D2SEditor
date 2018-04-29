package ru.aizen.domain.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.block.*;
import ru.aizen.domain.dao.CharacterDao;
import ru.aizen.domain.data.BlockReader;
import ru.aizen.domain.data.BlockWriter;
import ru.aizen.domain.exception.ValidatorException;

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
    private HeaderBlock headerBlock;
    private MetaBlock metaBlock;
    private AttributesBlock attributesBlock;
    private SkillsBlock skillsBlock;

    //Properties
    private StringProperty title = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty level = new SimpleStringProperty();
    private StringProperty characterClass = new SimpleStringProperty();
    private StringProperty expansion = new SimpleStringProperty();

    private final CharacterDao characterDao;
    private final BlockReader blockReader;

    @Autowired
    public Character(CharacterDao characterDao,
                     BlockReader blockReader) {
        this.characterDao = characterDao;
        this.blockReader = blockReader;
    }

    public void load(Path path) throws IOException, ValidatorException {
        blockReader.read(path);
        headerBlock = blockReader.readHeader();
        metaBlock = blockReader.readMeta();
        attributesBlock = blockReader.readAttributes();
        skillsBlock = blockReader.readSkills();
    }

    public void save(Path path) throws IOException, ValidatorException {
        metaBlock.setLevel(attributesBlock.getAttributes().get(AttributesBlock.LEVEL).intValue());
        List<DataBlock> blocks = new ArrayList<>();
        blocks.add(headerBlock);
        blocks.add(metaBlock);
        blocks.add(attributesBlock);
        blocks.add(skillsBlock);
        blocks.addAll(stubs());
        Collections.sort(blocks);
        new BlockWriter().write(blocks, path);
        blockReader.read(path);
    }

    /**
     * Temporary method to creating stub data block
     * @return stub block
     */
    private List<DataBlock> stubs() {
        int hotKeysMercenaryQuestWayPointsNPCStart = HeaderBlock.HEADER_BLOCK_SIZE + MetaBlock.META_BLOCK_SIZE;
        int hotKeysMercenaryQuestWayPointsNPCSize = blockReader.getSubArrayPosition(AttributesBlock.identifier) -
                hotKeysMercenaryQuestWayPointsNPCStart;
        DataBlock hotKeysMercenaryQuestWayPointsNPC = blockReader.createStubBlock(3,
                hotKeysMercenaryQuestWayPointsNPCStart,
                hotKeysMercenaryQuestWayPointsNPCSize);
        int itemsStart = blockReader.getSubArrayPosition(SkillsBlock.identifier) + SkillsBlock.SKILLS_BLOCK_SIZE;
        int itemsSize = blockReader.getBytes().length - itemsStart;
        DataBlock skillsItems = blockReader.createStubBlock(6,
                itemsStart,
                itemsSize);
        List<DataBlock> result = new ArrayList<>();
        result.add(hotKeysMercenaryQuestWayPointsNPC);
        result.add(skillsItems);
        return result;
    }

    public String getName() {
        return metaBlock.getName();
    }

    public Status getStatus() {
        return metaBlock.getStatus();
    }

    public byte getTitle() {
        return metaBlock.getTitle();
    }

    public CharacterClass getCharacterClass() {
        CharacterClass characterClass = characterDao.getCharacterClassByValue(metaBlock.getCharacterClass());
        setCharacterClass(characterClass);
        return characterClass;
    }

    public void setName(String name) {
        metaBlock.setName(name);
    }

    public void setStatus(Status status) {
        metaBlock.setStatus(status);
    }

    public void setTitle(byte title) {
        metaBlock.setTitle(title);
    }

    public final void setTitleValue(String value) {
        title.set(value);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public final void setNameValue(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final void setLevel(String value) {
        level.set(value + " ");
    }

    public StringProperty levelProperty() {
        return level;
    }

    public final void setCharacterClass(CharacterClass value) {
        characterClass.set(StringUtils.capitalize(value.name().toLowerCase()));
        byte byteValue = characterDao.getValueByCharacterClass(value);
        metaBlock.setCharacterClass(byteValue);
    }

    public StringProperty classProperty() {
        return characterClass;
    }

    public final void setExpansion(boolean value) {
        expansion.set(value ? "Expansion Character" : "");
    }

    public StringProperty expansionProperty() {
        return expansion;
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

    public BlockReader getBlockReader() {
        return blockReader;
    }
}
