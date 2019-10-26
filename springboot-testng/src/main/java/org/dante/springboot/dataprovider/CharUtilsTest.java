package org.dante.springboot.dataprovider;

import org.dante.springboot.util.CharUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CharUtilsTest {
	
	@DataProvider
    public Object[][] validDataProvider() {
        return new Object[][]{
            { 'A', 65 },{ 'a', 97 },
            { 'B', 66 },{ 'b', 98 },
            { 'C', 67 },{ 'c', 99 },
            { 'D', 68 },{ 'd', 100 },
            { 'Z', 90 },{ 'z', 122 },
            { '1', 49 },{ '9', 57 }
        };
    }

	@Test(dataProvider = "validDataProvider")
    public void charToASCIITest(final char character, final int ascii) {
           int result = CharUtils.CharToASCII(character);
           Assert.assertEquals(result, ascii);
    }


}
