package ru.aizen.domain.character;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.character.block.MetaBlock;
import ru.aizen.domain.character.block.StubBlock;
import ru.aizen.domain.data.BlockSize;
import ru.aizen.domain.data.CharacterData;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.data.DataReader;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class keep all data of character witch can be present on application forms
 */
public class Character {
    private CharacterData characterData;
    private HeaderBlock headerBlock;
    private MetaBlock metaBlock;
    private AttributesBlock attributesBlock;

    private List<StubBlock> stubs;

    public Character() {
    }

    public void load(Path path) throws IOException, DecoderException {
        characterData = new CharacterData(path);
        characterData.read();
        DataReader reader = characterData.getReader();
        headerBlock = reader.readHeader();
        metaBlock = reader.readMeta();
        attributesBlock = reader.readAttributes();
        stubs = new ArrayList<>();
    }

    public void save(byte[] toSave) throws IOException {
        characterData.setOutputData(toSave);
        FileUtils.save(characterData);
    }

    public void backup() throws IOException {
        FileUtils.backup(characterData);
    }

    public void restore() throws IOException {
        FileUtils.restore(characterData);
    }

    public String getName(){
        return metaBlock.getName();
    }

    public Status getStatus(){
        return metaBlock.getStatus();
    }

    public Title getTitle(){
        return metaBlock.getTitle();
    }

    public CharacterClass getCharacterClass(){
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
