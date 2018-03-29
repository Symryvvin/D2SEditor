package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.character.*;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Class keep all data of character witch can be present on application forms
 */
public class Character {
    private CharacterData characterData;
    private Header header;
    private Meta meta;
    private Attributes attributes;

    private AttributePacker aReader;

    public Character() {
        aReader = new AttributePacker();
    }

    public void load(Path path) throws IOException, DecoderException {
        characterData = new CharacterData(path);
        characterData.read();
        header = characterData.getReader().readHeader();
        meta = characterData.getReader().readMeta();
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
        return meta.getName();
    }

    public Status getStatus(){
        return meta.getStatus();
    }

    public Title getTitle(){
        return meta.getTitle();
    }

    public CharacterClass getCharacterClass(){
        return meta.getCharacterClass();
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
