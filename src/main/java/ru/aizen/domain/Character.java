package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;
import ru.aizen.domain.util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class Character {
    private HeroData data;
    private Attributes attributes;

    private AttributePacker aReader;

    public Character() {
        aReader = new AttributePacker();
    }

    public void load(Path path) throws DecoderException {
        data = new HeroData(path);
        data.calculateCheckSum();
        aReader = new AttributePacker();
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
