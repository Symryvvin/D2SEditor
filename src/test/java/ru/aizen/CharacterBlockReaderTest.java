package ru.aizen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.data.BlockReader;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.BinHexUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterBlockReaderTest {
    private Character character;
    private BlockReader reader;

    @Before
    public void getTestData() throws IOException, URISyntaxException, ValidatorException {
        character = new Character();
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        reader = character.getBlockReader();
    }

    @Test
    public void testExtractAttributes() {
        AttributesBlock expected = new AttributesBlock();
        expected.put(AttributesBlock.STRENGTH, 15L);
        expected.put(AttributesBlock.ENERGY, 20L);
        expected.put(AttributesBlock.DEXTERITY, 20L);
        expected.put(AttributesBlock.VITALITY, 25L);
        expected.put(AttributesBlock.STAT_POINTS, 5L);
        expected.put(AttributesBlock.SKILL_POINTS, 1L);
        expected.put(AttributesBlock.HP, 14336L);
        expected.put(AttributesBlock.MAX_HP, 14336L);
        expected.put(AttributesBlock.MP, 5632L);
        expected.put(AttributesBlock.MAX_MP, 5632L);
        expected.put(AttributesBlock.SP, 21760L);
        expected.put(AttributesBlock.MAX_SP, 21760L);
        expected.put(AttributesBlock.LEVEL, 2L);
        expected.put(AttributesBlock.EXPERIENCE, 536L);
        expected.put(AttributesBlock.GOLD, 3L);
        expected.put(AttributesBlock.GOLD_IN_STASH, 4L);
        AttributesBlock attributesBlock = reader.readAttributes();
        Assert.assertEquals(expected.getAttributes(), attributesBlock.getAttributes());
    }

    @Test
    public void testDataHeader() throws URISyntaxException, IOException, ValidatorException {
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        HeaderBlock header = reader.readHeader();
        Assert.assertArrayEquals(BinHexUtils.decodeHex(HeaderBlock.SIGNATURE), header.getSignature());
        Assert.assertEquals(0x60, header.getVersion());
        Assert.assertEquals(998, header.getFileSize());
        //  Assert.assertEquals(-705677177, header.getChecksum());
    }

}
