package com.mycompany.matchinggame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * File Name: CompleteGame.java  
 * Person Programmer: Steven Passynkov
 * Date: 28 Nov 2024
 * Description: Complete Game screen
 */
public class CompleteGame {

    /**
     * Show Complete Screen
     * 
     * @param frame Reference to original frame
     * @param moveCount Number of moves
     */
    static void show(JFrame frame, int moveCount) {
        JOptionPane.showMessageDialog(frame, "Congratulations! You've completed the game in " + moveCount + " moves.");

    }
}
