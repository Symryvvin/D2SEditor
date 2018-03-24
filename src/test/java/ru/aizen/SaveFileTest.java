package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.Hero;
import ru.aizen.domain.util.BinHexUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveFileTest {

    @Test
    public void  testCheckSum() throws IOException{
        String fileName = "/test.d2s";
        String pathToFile = getClass().getResource(fileName).getFile().replaceFirst("/", "");
        System.out.println(pathToFile);
        byte[] data = Files.readAllBytes(Paths.get(pathToFile));
        Hero hero = new Hero(data, fileName);
        hero.setOutputData(data);
        Assert.assertEquals(-564645869, BinHexUtils.calculateCheckSum(hero));
    }

}
