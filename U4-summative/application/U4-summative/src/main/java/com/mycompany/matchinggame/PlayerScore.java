package com.mycompany.matchinggame;

/**
 * File Name: PlayerScore.java  
 * Person Programmer: Steven Passynkov
 * Date: 28 Nov 2024
 * Description: The class represent PlayerScore
 */
public class PlayerScore {
// name

    private String name;
    // score
    private int score;
    // level
    private String level;

    /**
     * Constructor
     *
     * @param name
     * @param score
     * @param level
     */
    public PlayerScore(String name, int score, String level) {
        this.name = name;
        this.score = score;
        this.level = level;
    }

    /**
     * Update score for name and level
     *
     * @param score New score
     */
    void setScore(int score) {
        this.score = score;
    }

    /**
     * Get name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get score
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Get level
     *
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     * Return representation of PlayerScore
     *
     * @return
     */
    @Override
    public String toString() {
        return name + ": " + score;
    }
}
