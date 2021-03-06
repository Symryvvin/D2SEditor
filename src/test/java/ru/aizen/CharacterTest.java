package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.block.MetaBlock;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterTest {

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
    public void testCharacterMeta() throws URISyntaxException, IOException, ValidatorException {
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        Character character = new Character();
        character.load(path);
        MetaBlock metaBlock = character.getMetaBlock();
        Assert.assertEquals("test", metaBlock.getName());
        Assert.assertEquals(true, metaBlock.getStatus().isExpansion());
        Assert.assertEquals(false, metaBlock.getStatus().isHardcore());
        Assert.assertEquals(false, metaBlock.getStatus().isDead());
        Assert.assertEquals(0, metaBlock.getTitle().getValue());
        Assert.assertEquals(CharacterClass.DRUID, metaBlock.getCharacterClass());
    }

    @Test
    public void testCheckName() throws Exception {
        String name = "Aizen";
        Validator.checkName(name);
    }


}
