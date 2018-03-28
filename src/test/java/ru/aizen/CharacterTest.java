package ru.aizen;

import org.junit.Assert;
import org.junit.Test;
import ru.aizen.domain.character.Status;

public class CharacterTest {

    @Test
    public void testStatus(){
        Status status;
        status = new Status((byte)0); //00000000
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(false, status.isHardcode());
        Assert.assertEquals(false, status.isDead());
        status = new Status("20"); //00100000
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(false, status.isHardcode());
        Assert.assertEquals(false, status.isDead());
        status = new Status("24"); //00100100
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(true, status.isHardcode());
        Assert.assertEquals(false, status.isDead());
        status = new Status("28"); //00101000
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(false, status.isHardcode());
        Assert.assertEquals(true, status.isDead());
        status = new Status((byte)44); //00101100
        Assert.assertEquals(true, status.isExpansion());
        Assert.assertEquals(true, status.isHardcode());
        Assert.assertEquals(true, status.isDead());
        status = new Status("04"); //00000100
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(true, status.isHardcode());
        Assert.assertEquals(false, status.isDead());
        status = new Status("0C"); //00001100
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(true, status.isHardcode());
        Assert.assertEquals(true, status.isDead());
        status = new Status((byte)8); //00001000
        Assert.assertEquals(false, status.isExpansion());
        Assert.assertEquals(false, status.isHardcode());
        Assert.assertEquals(true, status.isDead());
    }
}
