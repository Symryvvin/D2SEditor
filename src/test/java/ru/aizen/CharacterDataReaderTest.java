package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aizen.domain.Character;
import ru.aizen.domain.DataHeader;
import ru.aizen.domain.GameVersion;
import ru.aizen.domain.HeroDataReader;
import ru.aizen.domain.attribute.AttributePacker;
import ru.aizen.domain.attribute.Attributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterDataReaderTest {
    private HeroDataReader reader;

    @Before
    public void getTestData() throws IOException, URISyntaxException {
        String fileName = "/test.d2s";
        reader = new HeroDataReader(Files.readAllBytes(Paths.get(getClass().getResource(fileName).toURI())));
    }

    @Test
    public void testFindAttributeSection() throws DecoderException {
        String hex = "001E084081000A066440A0800201060071C001401C8000C0022400B0000A00AAC002802AC040D000430000C0C100008007040000FE03";
        Assert.assertArrayEquals(Hex.decodeHex(hex), reader.getAttributesBlock());
    }

    @Test
    public void testExtractAttributes() throws DecoderException {
        byte[] packedAttributes = reader.getAttributesBlock();
        Attributes expected = new Attributes();
        expected.put(Attributes.STRENGTH, 15);
        expected.put(Attributes.ENERGY, 20);
        expected.put(Attributes.DEXTERITY, 20);
        expected.put(Attributes.VITALITY, 25);
        expected.put(Attributes.STAT_POINTS, 5);
        expected.put(Attributes.SKILL_POINTS, 1);
        expected.put(Attributes.HP, 56);
        expected.put(Attributes.MAX_HP, 56);
        expected.put(Attributes.MP, 22);
        expected.put(Attributes.MAX_MP, 22);
        expected.put(Attributes.SP, 85);
        expected.put(Attributes.MAX_SP, 85);
        expected.put(Attributes.LEVEL, 2);
        expected.put(Attributes.EXPERIENCE, 536);
        expected.put(Attributes.GOLD, 3);
        expected.put(Attributes.GOLD_IN_STASH, 4);
        AttributePacker packer = new AttributePacker();
        Attributes attributes = packer.unpackAttributes(packedAttributes);
        Assert.assertEquals(expected, attributes);
    }

    @Test
    public void testDataHeader() throws URISyntaxException, DecoderException, IOException {
        Character character = new Character();
        Path path = Paths.get(getClass().getResource("/test.d2s").toURI());
        character.load(path);
        DataHeader dataHeader = character.getCharacterData().getReader().readHeader();
        Assert.assertEquals("55aa55aa", dataHeader.getSignature());
        Assert.assertEquals(GameVersion.VERSION_1_10, dataHeader.getVersion());
        Assert.assertEquals(998, dataHeader.getFileSize());
        Assert.assertEquals(-667916153, dataHeader.getChecksum());
    }

}
