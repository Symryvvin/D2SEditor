package ru.aizen;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.HeroData;
import ru.aizen.domain.HeroDataReader;

import java.nio.file.Paths;

public class HeroDataReaderTest {

    @Test
    public void testFindAttributeSection(){
        HeroDataReader reader = new HeroDataReader(getTestHeroData().getData());
        String hex = "14082092800C06FC41E09F02020600E6C20180B98000A0022400A8000A0080C1020060C080D0E0830200E03F";
        Assert.assertArrayEquals(HexBin.decode(hex), reader.getAttributesBlock());
    }

    private HeroData getTestHeroData(){
        String fileName = "/test.d2s";
        String pathToFile = getClass().getResource(fileName).getFile().replaceFirst("/", "");
        return new HeroData(Paths.get(pathToFile));
    }
}
