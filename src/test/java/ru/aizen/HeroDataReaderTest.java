package ru.aizen;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aizen.domain.HeroDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HeroDataReaderTest {
    private HeroDataReader reader;

    @Before
    public void getTestData() throws IOException {
        String fileName = "/test.d2s";
        String pathToFile = getClass().getResource(fileName).getFile().replaceFirst("/", "");
        reader = new HeroDataReader(Files.readAllBytes(Paths.get(pathToFile)));
    }

    @Test
    public void testFindAttributeSection() {
        String hex = "0014082092800C06FC41E09F02020600E6C20180B98000A0022400A8000A0080C1020060C080D0E0830200E03F";
        Assert.assertArrayEquals(HexBin.decode(hex), reader.getAttributesBlock());
    }

}
