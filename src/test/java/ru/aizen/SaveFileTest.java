package ru.aizen;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.HeroData;
import ru.aizen.domain.util.BinHexUtils;

import java.io.IOException;
import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void testFileSize(){
        Assert.assertArrayEquals(HexBin.decode("C203"), BinHexUtils.calculateFileSize(getTestHeroData()));
    }

    @Test
    public void  testCheckSum() {
        Assert.assertEquals(-564645869, BinHexUtils.calculateCheckSum(getTestHeroData()));
    }

    @Test
    public void  testInputOutputArrays() throws IOException {
        HeroData heroData = getTestHeroData();
        heroData.setCheckSum(BinHexUtils.calculateCheckSum(heroData));
        Assert.assertArrayEquals(heroData.getData(), BinHexUtils.getResultBytes(heroData));
    }

    @Test
    public void testHeroDataPaths(){
        HeroData heroData = getTestHeroData();
        Assert.assertEquals("test.d2s", heroData.getInput().getFileName().toString());
        Assert.assertEquals("test.d2s.bak", heroData.getBackUp().getFileName().toString());
    }

    private HeroData getTestHeroData(){
        String fileName = "/test.d2s";
        String pathToFile = getClass().getResource(fileName).getFile().replaceFirst("/", "");
        return new HeroData(Paths.get(pathToFile));
    }

}
