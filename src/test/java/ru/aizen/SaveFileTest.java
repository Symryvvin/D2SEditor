package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.character.Character;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void testCheckSum() {
        Character character = getTestCharacter();
        Assert.assertEquals(-667916153, character.getCharacterData().getReader().readHeader().getChecksum());
    }

    @Test
    public void testInputOutputArrays() throws IOException {
        Character character = getTestCharacter();
        Assert.assertArrayEquals(character.getCharacterData().getBytes(), character.getCharacterData().getDataToSave());
    }

    private Character getTestCharacter() {
        String fileName = "/test.d2s";
        Character character = new Character();
        try {
            character.load(Paths.get(getClass().getResource(fileName).toURI()));
        } catch (IOException | DecoderException | URISyntaxException e) {
            e.printStackTrace();
        }
        return character;
    }

}
