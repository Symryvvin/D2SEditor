package ru.aizen.domain.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;
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

    //Properties
    private StringProperty title = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty level = new SimpleStringProperty();
    private StringProperty characterClass = new SimpleStringProperty();
    private StringProperty expansion = new SimpleStringProperty();

    private final CharacterDao characterDao;

    @Autowired
    public Character(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }

    public void load(Path path) throws IOException {
        characterData = new CharacterData(path);
        characterData.read();
        DataReader reader = characterData.getReader();
        headerBlock = reader.readHeader();
        metaBlock = reader.readMeta();
        attributesBlock = reader.readAttributes();
        skillsBlock = reader.readSkills();
    }

    public void save() throws IOException {
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
    private List<DataBlock> stubs() {
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
        characterData.setLastBackup(FileUtils.backup(characterData));
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
