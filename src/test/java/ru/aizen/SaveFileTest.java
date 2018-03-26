package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.CharacterData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void testCalculateFileSize() throws DecoderException, URISyntaxException, IOException {
        Assert.assertArrayEquals(Hex.decodeHex("E603"), getTestHeroData().calculateFileSize());
    }

    @Test
    public void  testCheckSum() throws URISyntaxException, IOException {
        CharacterData characterData = getTestHeroData();
        characterData.calculateCheckSum();
        Assert.assertEquals(-667916153, characterData.getCheckSum());
    }

    @Test
    public void  testInputOutputArrays() throws IOException, URISyntaxException {
        CharacterData characterData = getTestHeroData();
        characterData.calculateCheckSum();
        Assert.assertArrayEquals(characterData.getBytes(), characterData.getDataToSave());
    }

    @Test
    public void testHeroDataPaths() throws URISyntaxException, IOException {
        CharacterData characterData = getTestHeroData();
        Assert.assertEquals("test.d2s", characterData.getInput().getFileName().toString());
        Assert.assertEquals("test.d2s.bak", characterData.getBackUp().getFileName().toString());
    }

    private CharacterData getTestHeroData() throws URISyntaxException, IOException {
        String fileName = "/test.d2s";
        CharacterData characterData = new CharacterData(Paths.get(getClass().getResource(fileName).toURI()));
        characterData.read();
        return characterData;
    }

}
