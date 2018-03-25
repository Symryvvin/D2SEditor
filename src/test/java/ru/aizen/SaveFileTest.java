package ru.aizen;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.HeroData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void testCalculateFileSize() throws DecoderException, URISyntaxException {
        Assert.assertArrayEquals(Hex.decodeHex("E603"), getTestHeroData().calculateFileSize());
    }

    @Test
    public void  testCheckSum() throws URISyntaxException {
        HeroData heroData = getTestHeroData();
        heroData.calculateCheckSum();
        Assert.assertEquals(-667916153, heroData.getCheckSum());
    }

    @Test
    public void  testInputOutputArrays() throws IOException, URISyntaxException {
        HeroData heroData = getTestHeroData();
        heroData.calculateCheckSum();
        Assert.assertArrayEquals(heroData.getData(), heroData.getDataToSave());
    }

    @Test
    public void testHeroDataPaths() throws URISyntaxException {
        HeroData heroData = getTestHeroData();
        Assert.assertEquals("test.d2s", heroData.getInput().getFileName().toString());
        Assert.assertEquals("test.d2s.bak", heroData.getBackUp().getFileName().toString());
    }

    private HeroData getTestHeroData() throws URISyntaxException {
        String fileName = "/test.d2s";
        return new HeroData(Paths.get(getClass().getResource(fileName).toURI()));
    }

}
