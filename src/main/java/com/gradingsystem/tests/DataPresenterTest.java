package com.gradingsystem.tests;

import com.gradingsystem.utils.DataPresenter;
import org.junit.Assert;
import org.junit.Test;

public class DataPresenterTest {
    @Test
    public void testGetIDFromString_ValidInput() {
        String inputString = "ID: 123 \t Name: John \t Age: 25";
        String expectedID = "123";

        String actualID = DataPresenter.getIDFromString(inputString);

        Assert.assertEquals(expectedID, actualID);
    }

    @Test
    public void testGetIDFromString_NoIDFound() {
        String inputString = "Name: John \t Age: 25";

        String actualID = DataPresenter.getIDFromString(inputString);

        Assert.assertEquals("No ID found", actualID);
    }
}
