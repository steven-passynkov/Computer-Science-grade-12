/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ovs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * File Name:	HeightTest.java 
 * Person Programmer: Steven Passynkov 
 * Date: 23 Feb 2024 
 * Description: Junit for Height.java
 */
public class HeightTest {

    public HeightTest() {
    }

    @Test
    public void testSetHeightInInch() {
        // create height object
        Height height = new Height();
        // set 60 inches
        height.setHeightInInch(60);
        // assert  it converts min correctly (add room to double compare)
        assertEquals(74.01, height.getMaxHeight(), 0.1);
        // asset  it converts min correctly (add room to double compare)
        assertEquals(48.03, height.getMinHeight(), 0.1);
        // assert height is allowed
        assertTrue(height.isHightAllowed());
    }

    @Test
    public void testSetHeightInCm() {
        // create height object
        Height height = new Height();
        // set 170 cm
        height.setHeightInCm(170);
        // assert  min in cm
        assertEquals(188.0, height.getMaxHeight());
        // assert  max in cm 
        assertEquals(122.0, height.getMinHeight());
        // assert height is allowed
        assertTrue(height.isHightAllowed());
    }

    @Test
    public void testIsHeightNotAllowed() {
        // create height object
        Height height = new Height();
        // set height is cm that is higther than allowed
        height.setHeightInCm(200);
        // assert height is not allowed
        assertFalse(height.isHightAllowed());
    }

    @Test
    public void testIsHeightAllowedMin() {
        // create height object
        Height height = new Height();
        // set min allowed height
        height.setHeightInCm(122);
        // assert height is allowed
        assertTrue(height.isHightAllowed());
    }

    @Test
    public void testIsHeightAllowedMax() {
        // create height object
        Height height = new Height();
        // set max allowed height
        height.setHeightInCm(188);
        // assert height is  allowed
        assertTrue(height.isHightAllowed());
    }

}
