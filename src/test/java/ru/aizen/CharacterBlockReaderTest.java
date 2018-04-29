package ru.aizen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aizen.app.AppConfig;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.block.HeaderBlock;
import ru.aizen.domain.dao.AttributeDao;
import ru.aizen.domain.data.BlockReader;
import ru.aizen.domain.data.GameVersion;
import ru.aizen.domain.exception.ValidatorException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class CharacterBlockReaderTest {
    @Autowired
    private Character character;
    @Autowired
    private BlockReader reader;
    @Autowired
    private AttributeDao attributeDao;

    @Before
    public void getTestData() throws IOException, URISyntaxException, ValidatorException {
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        reader = character.getCharacterData().getReader();
    }

    @Test
    public void testExtractAttributes() {
        AttributesBlock expected = new AttributesBlock(0, attributeDao);
        expected.put(AttributesBlock.STRENGTH, 15L);
        expected.put(AttributesBlock.ENERGY, 20L);
        expected.put(AttributesBlock.DEXTERITY, 20L);
        expected.put(AttributesBlock.VITALITY, 25L);
        expected.put(AttributesBlock.STAT_POINTS, 5L);
        expected.put(AttributesBlock.SKILL_POINTS, 1L);
        expected.put(AttributesBlock.HP, 14464L);
        expected.put(AttributesBlock.MAX_HP, 14464L);
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
        HeaderBlock header = character.getCharacterData().getReader().readHeader();
        Assert.assertEquals(-1437226411, header.getSignature());
        Assert.assertEquals(GameVersion.VERSION_1_10, header.getVersion());
        Assert.assertEquals(998, header.getFileSize());
        Assert.assertEquals(-667916153, header.getChecksum());
    }

}
