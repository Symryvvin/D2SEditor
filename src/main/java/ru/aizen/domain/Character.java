package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;

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
