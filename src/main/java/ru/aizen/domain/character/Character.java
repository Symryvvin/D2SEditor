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
    private Map<Integer, DataBlock> blocks;

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
        blocks.put(HeaderBlock.ORDER, blockReader.readHeader());
        blocks.put(MetaBlock.ORDER, blockReader.readMeta());
        blocks.put(HotKeysBlock.ORDER, blockReader.readHotKeys());
        blocks.put(MapBlock.ORDER, blockReader.readMap());
        blocks.put(MercenaryBlock.ORDER, blockReader.readMercenary());
        blocks.put(6, blockReader.unknownBlock(6, 191, 144));
        blocks.put(QuestsBlock.ORDER, blockReader.readQuests());
        blocks.put(WaypointsBlock.ORDER, blockReader.readWaypoints());
        blocks.put(NPCBlock.ORDER, blockReader.readNPC());
        blocks.put(AttributesBlock.ORDER, blockReader.readAttributes());
        blocks.put(SkillsBlock.ORDER, blockReader.readSkills());
        blocks.put(InventoryBlock.ORDER, blockReader.readInventory());
    }

    public byte[] save(Path path) throws IOException {
        List<UByte> bytes = blocks.values()
                .stream()
                .sorted()
                .flatMap(b -> b.collect().stream())
                .collect(Collectors.toList());
        return new BlockWriter().write(bytes, path);
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
        return (MetaBlock) blocks.get(MetaBlock.ORDER);
    }

    public AttributesBlock getAttributesBlock() {
        return (AttributesBlock) blocks.get(AttributesBlock.ORDER);
    }

    public SkillsBlock getSkillsBlock() {
        return (SkillsBlock) blocks.get(SkillsBlock.ORDER);
    }

    public WaypointsBlock getWaypointsBlock() {
        return (WaypointsBlock) blocks.get(WaypointsBlock.ORDER);
    }

    public MapBlock getMapBlock() {
        return (MapBlock) blocks.get(MapBlock.ORDER);
    }

    public QuestsBlock getQuestBlock() {
        return (QuestsBlock) blocks.get(QuestsBlock.ORDER);
    }

    public BlockReader getBlockReader() {
        return blockReader;
    }


}
