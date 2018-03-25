package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;

public class Character {
    private HeroData data;
    private Attributes attributes;

    public Character(HeroData data) {
        this.data = data;
    }

    public void load() throws DecoderException {
        data.calculateCheckSum();
        AttributePacker aReader = new AttributePacker();
        attributes = aReader.unpackAttributes(data.getAttributesBlock());
    }

    public void save(byte[] toSave) throws IOException {
        data.setOutputData(toSave);
        data.calculateCheckSum();
        FileUtils.save(data);
    }

    public void backup() throws IOException {
        FileUtils.backup(data);
    }

    public void restore() throws IOException {
        FileUtils.restore(data);
    }

    public HeroData getData() {
        return data;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }



}
