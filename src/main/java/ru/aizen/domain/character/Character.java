package ru.aizen.domain.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.block.*;
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

    private final BlockReader blockReader;

    @Autowired
    public Character(BlockReader blockReader) {
        this.blockReader = blockReader;
    }

    public void load(Path path) throws IOException, ValidatorException {
        blockReader.read(path);
        headerBlock = blockReader.readHeader();
        headerBlock.validate();
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
        load(path);
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

    public final void setCharacterClass(String value) {
        characterClass.set(value);
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

    public HeaderBlock getHeaderBlock() {
        return headerBlock;
    }

    public void setHeaderBlock(HeaderBlock headerBlock) {
        this.headerBlock = headerBlock;
    }

    public MetaBlock getMetaBlock() {
        return metaBlock;
    }

    public void setMetaBlock(MetaBlock metaBlock) {
        this.metaBlock = metaBlock;
    }

    public AttributesBlock getAttributesBlock() {
        return attributesBlock;
    }

    public void setAttributesBlock(AttributesBlock attributesBlock) {
        this.attributesBlock = attributesBlock;
    }

    public SkillsBlock getSkillsBlock() {
        return skillsBlock;
    }

    public void setSkillsBlock(SkillsBlock skillsBlock) {
        this.skillsBlock = skillsBlock;
    }

    public BlockReader getBlockReader() {
        return blockReader;
    }
}
