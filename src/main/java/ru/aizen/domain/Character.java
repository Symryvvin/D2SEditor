package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.CharacterData;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Class keep all data of character witch can be present on application forms
 */
public class Character {
    private CharacterData characterData;
    private String name;
    private Status status;
    private Title title;
    private CharacterClass characterClass;
    private Attributes attributes;

    private AttributePacker aReader;

    public Character() {
        aReader = new AttributePacker();
    }

    public void load(Path path) throws IOException, DecoderException {
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
