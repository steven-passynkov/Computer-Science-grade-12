/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ovs;

/**
 * File Name:	Height.java
 * Person Programmer:	Steven Passynkov 
 * Date:	20 Feb 2024 
 * Description: Height class to keep height and functions to translate inches to cm
 * 
 * See Test in "Test Packages"/ovs/HeightTest.java
 */
public class Height {

    // min allowed height in cm. Static and public to use as constant
    public static double MIN_HEIGT = 122.0;
    // max allowed height in cm.  Static and public to use as constant
    public static double MAX_HEIGT = 188.0;
    // min height allowed height in unit selected by user
    private double minHeight = 0;
    // max height allowed height in unit selected by user
    private double maxHeight = 0;
    // current height in cm
    private double heightCm = 0;

    /**
     * Get min height allowed in unit selected by user
     *
     * @return min height allowed in unit selected by user
     */
    public double getMinHeight() {
        return this.minHeight;
    }

    /**
     * Get max height allowed in unit selected by user
     *
     * @return max height allowed in unit selected by user
     */
    public double getMaxHeight() {
        return this.maxHeight;
    }

    /**
     * Check if height is between MIN_HEIGT and MAX_HEIGT
     *
     * @return
     */
    public boolean isHightAllowed() {
        // heigh in cm should be bigger or equal MIN_HEIGT and less or equal MAX_HEIGT
        return this.heightCm >= MIN_HEIGT && this.heightCm <= MAX_HEIGT;
    }

    /**
     * Set height in inches
     *
     * @param height height in inches
     */
    public void setHeightInInch(double height) {
        // height in cm = height in inches * 2.54
        this.heightCm = height * 2.54;
        // minHeight in inches = MIN_HEIGT in cm / 2.54
        this.minHeight = MIN_HEIGT / 2.54;
        // maxHeight in inches = MAX_HEIGT in cm / 2.54
        this.maxHeight = MAX_HEIGT / 2.54;
    }

    /**
     * Set height in cm
     *
     * @param height height in cm
     */
    public void setHeightInCm(double height) {
        // // height in cm (no conversion)
        this.heightCm = height;
        // minHeight is MIN_HEIGT (no conversion)
        this.minHeight = MIN_HEIGT;
        // maxHeight is MAX_HEIGT (no conversion)
        this.maxHeight = MAX_HEIGT;
    }
}
