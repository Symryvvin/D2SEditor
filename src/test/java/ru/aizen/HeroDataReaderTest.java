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
        String hex = "001E084081000A066440A0800201060071C001401C8000C0022400B0000A00AAC002802AC040D000430000C0C100008007040000FE03";
        Assert.assertArrayEquals(HexBin.decode(hex), reader.getAttributesBlock());
    }

}
