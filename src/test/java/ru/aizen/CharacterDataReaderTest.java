package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aizen.domain.data.DataReader;
import ru.aizen.domain.data.GameVersion;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.block.HeaderBlock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterDataReaderTest {
    private DataReader reader;

    @Before
    public void getTestData() throws IOException, URISyntaxException, DecoderException {
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        Character character = new Character();
        character.load(path);
        reader = character.getCharacterData().getReader();
    }

    @Test
    public void testExtractAttributes() throws DecoderException {
        AttributesBlock expected = new AttributesBlock();
        expected.put(AttributesBlock.STRENGTH, 15);
        expected.put(AttributesBlock.ENERGY, 20);
        expected.put(AttributesBlock.DEXTERITY, 20);
        expected.put(AttributesBlock.VITALITY, 25);
        expected.put(AttributesBlock.STAT_POINTS, 5);
        expected.put(AttributesBlock.SKILL_POINTS, 1);
        expected.put(AttributesBlock.HP, 56);
        expected.put(AttributesBlock.MAX_HP, 56);
        expected.put(AttributesBlock.MP, 22);
        expected.put(AttributesBlock.MAX_MP, 22);
        expected.put(AttributesBlock.SP, 85);
        expected.put(AttributesBlock.MAX_SP, 85);
        expected.put(AttributesBlock.LEVEL, 2);
        expected.put(AttributesBlock.EXPERIENCE, 536);
        expected.put(AttributesBlock.GOLD, 3);
        expected.put(AttributesBlock.GOLD_IN_STASH, 4);
        AttributesBlock attributesBlock = reader.readAttributes();
        Assert.assertEquals(expected, attributesBlock);
    }

    @Test
    public void testDataHeader() throws URISyntaxException, DecoderException, IOException {
        Character character = new Character();
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        HeaderBlock header = character.getCharacterData().getReader().readHeader();
        Assert.assertEquals(-1437226411, header.getSignature());
        Assert.assertEquals(GameVersion.VERSION_1_10, header.getVersion());
        Assert.assertEquals(998, header.getFileSize());
        Assert.assertEquals(-667916153, header.getChecksum());
    }

}
