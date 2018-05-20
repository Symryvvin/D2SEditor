package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aizen.app.AppConfig;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class SaveFileTest {
    private Path path;

    @Autowired
    private Character character;

    @Test
    public void testCheckSum() throws ValidatorException {
        Character character = getTestCharacter();
        Assert.assertEquals(-705664889, character.getBlockReader().readHeader().getChecksum());
    }

    @Test
    public void testWriter() throws IOException {
        Character character = getTestCharacter();
        byte[] expected = character.getBlockReader().getBytes();
        byte[] save = character.save(path);
        Assert.assertArrayEquals(expected, save);
    }

    private Character getTestCharacter() {
        String fileName = "/test.d2s";
        try {
            path = Paths.get(getClass().getResource(fileName).toURI());
            character.load(path);
        } catch (IOException | URISyntaxException | ValidatorException e) {
            e.printStackTrace();
        }
        return character;
    }

}
