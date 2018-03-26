package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class Character {
    private CharacterData characterData;
    private Attributes attributes;

    private AttributePacker aReader;

    public Character() {
        aReader = new AttributePacker();
    }

    public void load(Path path) throws DecoderException, IOException {
        characterData = new CharacterData(path);
        characterData.read();
        aReader = new AttributePacker();
        attributes = aReader.unpackAttributes(characterData.getAttributesBlock());
    }

    public void save(byte[] toSave) throws IOException {
        characterData.setOutputData(toSave);
        characterData.calculateCheckSum();
        FileUtils.save(characterData);
    }

    public void backup() throws IOException {
        FileUtils.backup(characterData);
    }

    public void restore() throws IOException {
        FileUtils.restore(characterData);
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
