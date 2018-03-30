package ru.aizen.domain.character;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.character.block.MetaBlock;
import ru.aizen.domain.data.CharacterData;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

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
        headerBlock = characterData.getReader().readHeader();
        metaBlock = characterData.getReader().readMeta();
        attributesBlock = characterData.getReader().readAttributes();
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
