package ru.aizen.domain.character;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.CharacterData;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
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
    private Attributes attributes;

    private AttributePacker aReader;

    public Character() {
        aReader = new AttributePacker();
    }

    public void load(Path path) throws IOException, DecoderException {
        characterData = new CharacterData(path);
        characterData.read();
        headerBlock = (HeaderBlock) characterData.getReader().readHeader();
        metaBlock = (MetaBlock) characterData.getReader().readMeta();
        aReader = new AttributePacker();
        attributes = aReader.unpackAttributes(characterData.getAttributesBlock());
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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }


}
