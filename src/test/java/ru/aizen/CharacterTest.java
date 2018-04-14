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
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.dao.CharacterDao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class CharacterTest {
    @Autowired
    private Character character;
    @Autowired
    private CharacterDao characterDao;

    @Test
    public void testStatus() {
        Status status;
        status = new Status((byte) 0); //00000000
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(false, status.isHardcore());
        Assert.assertEquals(false, status.isDead());
        status = new Status("20"); //00100000
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(false, status.isHardcore());
        Assert.assertEquals(false, status.isDead());
        status = new Status("24"); //00100100
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(true, status.isHardcore());
        Assert.assertEquals(false, status.isDead());
        status = new Status("28"); //00101000
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(false, status.isHardcore());
        Assert.assertEquals(true, status.isDead());
        status = new Status((byte) 44); //00101100
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(true, status.isHardcore());
        Assert.assertEquals(true, status.isDead());
        status = new Status("04"); //00000100
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(true, status.isHardcore());
        Assert.assertEquals(false, status.isDead());
        status = new Status("0C"); //00001100
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(true, status.isHardcore());
        Assert.assertEquals(true, status.isDead());
        status = new Status((byte) 8); //00001000
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(false, status.isHardcore());
        Assert.assertEquals(true, status.isDead());
    }

    @Test
    public void testCharacterMeta() throws URISyntaxException, IOException, DecoderException {
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        Assert.assertEquals("Test", character.getName());
        Assert.assertEquals(true, character.getStatus().isExpansion());
        Assert.assertEquals(false, character.getStatus().isHardcore());
        Assert.assertEquals(false, character.getStatus().isDead());
        Assert.assertEquals(0, character.getTitle());
        Assert.assertEquals(CharacterClass.DRUID, character.getCharacterClass());
        // Assert.assertEquals(LocalDateTime.parse("2018-03-25T09:17:11"), character.getTime());
    }


}
