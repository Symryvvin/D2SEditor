package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveFileTest {
    private Path toSave;

    @Test
    public void testCheckSum() throws ValidatorException {
        Character character = getTestCharacter();
        // Assert.assertEquals(-705677177, character.getBlockReader().readHeader().getChecksum());
    }

    @Test
    public void testWriter() throws IOException {
        Character character = getTestCharacter();
        byte[] expected = character.getBlockReader().getBytes();
        byte[] save = character.save(toSave);
        Assert.assertArrayEquals(expected, save);
    }

    private Character getTestCharacter() {
        Character character = new Character();
        String origin = "/test.d2s";
        String saved = "/test-saved.d2s";
        try {
            toSave = Paths.get(getClass().getResource(saved).toURI());
            character.load(Paths.get(getClass().getResource(origin).toURI()));
        } catch (IOException | URISyntaxException | ValidatorException e) {
            e.printStackTrace();
        }
        return character;
    }

}
