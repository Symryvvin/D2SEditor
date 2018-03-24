package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.HeroData;
import ru.aizen.domain.util.BinHexUtils;

import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void  testCheckSum() {
        String fileName = "/test.d2s";
        String pathToFile = getClass().getResource(fileName).getFile().replaceFirst("/", "");
        HeroData heroData = new HeroData(Paths.get(pathToFile));
        Assert.assertEquals(-564645869, BinHexUtils.calculateCheckSum(heroData));
    }

}
