package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aizen.app.AppConfig;
import ru.aizen.domain.character.Character;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class SaveFileTest {

    @Autowired
    private Character character;

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

    @Test
    public void testWriter() throws DecoderException, IOException {
        Character character = getTestCharacter();
        byte[] expected = character.getCharacterData().getBytes();
        character.save();
        Assert.assertArrayEquals(expected, character.getCharacterData().getBytes());

    }

    private Character getTestCharacter() {
        String fileName = "/test.d2s";
        try {
            character.load(Paths.get(getClass().getResource(fileName).toURI()));
        } catch (IOException | DecoderException | URISyntaxException e) {
            e.printStackTrace();
        }
        return character;
    }

}
