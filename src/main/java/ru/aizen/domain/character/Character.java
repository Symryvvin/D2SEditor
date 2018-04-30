package ru.aizen.domain.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.block.*;
import ru.aizen.domain.data.BlockReader;
import ru.aizen.domain.data.BlockWriter;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.exception.ValidatorException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class keep all data of character witch can be present on application forms
 */
@Component
public class Character {
    private Map<String, DataBlock> blocks;

    //Properties
    //TODO move to specific class for binding
    private StringProperty title = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty level = new SimpleStringProperty();
    private StringProperty characterClass = new SimpleStringProperty();
    private StringProperty expansion = new SimpleStringProperty();

    private final BlockReader blockReader;

    @Autowired
    public Character(BlockReader blockReader) {
        this.blockReader = blockReader;
        this.blocks = new HashMap<>();
    }

    public void load(Path path) throws IOException, ValidatorException {
        blockReader.read(path);
        blocks.put(HeaderBlock.HEADER, blockReader.readHeader());
        blocks.put(MetaBlock.META, blockReader.readMeta());
        blocks.put(AttributesBlock.ATTRIBUTES, blockReader.readAttributes());
        blocks.put(SkillsBlock.SKILLS, blockReader.readSkills());
        blocks.putAll(stubs());
    }

    public void save(Path path) throws IOException, ValidatorException {
        List<UByte> bytes = blocks.values()
                .stream()
                .sorted()
                .flatMap(b -> b.collect().stream())
                .collect(Collectors.toList());
        new BlockWriter().write(bytes, path);
        load(path);
    }

    /**
     * Temporary method to creating stub data block
     * @return stub block
     */
    private Map<String, DataBlock> stubs() {
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
        Map<String, DataBlock> result = new HashMap<>();
        result.put("STUB", hotKeysMercenaryQuestWayPointsNPC);
        result.put("ITEMS", skillsItems);
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

    public MetaBlock getMetaBlock() {
        return (MetaBlock) blocks.get(MetaBlock.META);
    }

    public AttributesBlock getAttributesBlock() {
        return (AttributesBlock) blocks.get(AttributesBlock.ATTRIBUTES);
    }

    public SkillsBlock getSkillsBlock() {
        return (SkillsBlock) blocks.get(SkillsBlock.SKILLS);
    }

    public BlockReader getBlockReader() {
        return blockReader;
    }
}
