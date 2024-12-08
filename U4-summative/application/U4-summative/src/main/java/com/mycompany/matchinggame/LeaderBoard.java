package com.mycompany.matchinggame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * File Name: LeaderBoard.java  
 * Person Programmer: Steven Passynkov
 * Date: 28 Nov 2024
 * Description: LeadeBoard functionality
 */
public class LeaderBoard {

    // list of player scores
    private ArrayList<PlayerScore> playerScoreList;
    // file that is used to save results
    private static final String LEADERBOARD_FILE = "leaderboard.json";
    // parent frame
    private JFrame frame;

    /**
     * Constructor
     *
     * @param frame Reference to parent JFrame
     */
    public LeaderBoard(JFrame frame) {
        this.frame = frame;
        // Load leaderboard from file
        this.loadLeaderboard();
        // sort leaderboard
        this.sortLeaderBoard();
    }

    /**
     * Sort by composite key: "name_level"
     */
    public static void sortByNameAndLevel(ArrayList<PlayerScore> players) {
        // sort combined name_level
        Collections.sort(players, Comparator.comparing(player -> (player.getName() + "_" + player.getLevel()).toLowerCase()));
    }

    /**
     * Binary search by composite key: "name_level"
     */
    public PlayerScore searchByNameAndLevel(String name, String level) {
        // clone for search
        ArrayList<PlayerScore> clonedList = (ArrayList<PlayerScore>) playerScoreList.clone();
        // Ensure the list is sorted by the composite key before searching
        sortByNameAndLevel(clonedList);

        // search by name_level
        String searchKey = (name + "_" + level).toLowerCase();

        // init the starting index
        int left = 0;
        // ending index of the search range
        int right = clonedList.size() - 1;

        //  while the search range is valid
        while (left <= right) {
            // middle index of the current search range to avoid overflow
            int mid = left + (right - left) / 2;

            // key "name_level" for the middle element 
            String midKey = (clonedList.get(mid).getName() + "_" + clonedList.get(mid).getLevel()).toLowerCase();

            //  if the midKey matches  searchKey
            if (midKey.equals(searchKey)) {
                // Found the matching player, return it
                return clonedList.get(mid);
            } // If midKey is less than searchKey -> right half
            else if (midKey.compareTo(searchKey) < 0) {
                // Update the left 
                left = mid + 1;

            } // If midKey is greater than searchKey -> left half
            else {
                // Update the right 
                right = mid - 1;
            }
        }

        // Return null if no match is found
        return null;
    }

    /**
     * Save results to JSON file
     *
     * @frame frame Parent frame
     * @param score Score
     * @param currentLevel Level
     */
    public void saveScoreToLeaderboard(int score, String currentLevel) {
        // ask the player to enter their name 
        String name = JOptionPane.showInputDialog(frame, "Enter your name:");
        // Check if the player provided a valid name 
        if (name != null && !name.trim().isEmpty()) {
            // search PlayerScore by name and currentLevel
            PlayerScore playerScore = searchByNameAndLevel(name, currentLevel);
            // if found - update score
            if (playerScore != null) {
                playerScore.setScore(score);
            } else {
                // Create a new PlayerScore object  and add it
                playerScoreList.add(new PlayerScore(name, score, currentLevel));
            }

            // Sort  that the highest scores
            sortLeaderBoard();
            // leaderboard only keeps the top 5 scores
            if (playerScoreList.size() > 5) {
                // Remove the lowest score
                playerScoreList.remove(playerScoreList.size() - 1);
            }
            // Save the leaderboard to json
            saveLeaderboard();
        }
    }

    /**
     * Load loadLeaderboard from file
     */
    public void loadLeaderboard() {
        // create a list 
        ArrayList<PlayerScore> aList = new ArrayList<>();

        // Create a File
        File file = new File(LEADERBOARD_FILE);

        // check if the file exists
        if (file.exists()) {
            // open file and make sure the BufferedReader is closed automatically
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Read the file content line by line
                StringBuilder jsonData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
                // Parse the JSON data into JSONArray
                JSONArray jsonArray = new JSONArray(jsonData.toString());
                // Iterate through the JSONArray 
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Create a PlayerScore
                    PlayerScore score = new PlayerScore(
                            // name
                            jsonObject.getString("name"),
                            // score
                            jsonObject.getInt("score"),
                            // level
                            jsonObject.getString("level")
                    );
                    // Add the PlayerScore object to the leaderboard list
                    aList.add(score);
                }
            } catch (IOException e) {
                // Print stack trace 
                e.printStackTrace();
            }
        }

        playerScoreList = aList;
    }

    /**
     * Saves the leaderBoard to a JSON file
     */
    public void saveLeaderboard() {
        // Create a new JSON array to store leaderboard data
        JSONArray jsonArray = new JSONArray();
        // Iterate over the list of player 
        for (PlayerScore playerScore : playerScoreList) {
            // Create a new JSON object for layer's score
            JSONObject jsonObject = new JSONObject();
            // Add the player's name 
            jsonObject.put("name", playerScore.getName());
            // Add the player's score 
            jsonObject.put("score", playerScore.getScore());
            // Add the player's level
            jsonObject.put("level", playerScore.getLevel());
            // Add the JSON object to array
            jsonArray.put(jsonObject);
        }

        // Try block to handle  file writing exception
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            // Write the JSON array as a string to file
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            // Print the stack trace
            e.printStackTrace();
        }
    }

    /**
     * Sort players in descending order using bubble sort
     */
    public void sortLeaderBoard() {
        int n = playerScoreList.size();
        boolean swapped;

        // Outer loop for passes
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // Inner loop for comparing adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                // Compare to sort in descending order
                if (playerScoreList.get(j).getScore() < playerScoreList.get(j + 1).getScore()) {
                    // Swap elements using a temporary variable
                    PlayerScore temp = playerScoreList.get(j);
                    playerScoreList.set(j, playerScoreList.get(j + 1));
                    playerScoreList.set(j + 1, temp);
                    swapped = true;
                }
            }

            // If no two elements were swapped, the list is sorted
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Displays the leaderboard categorized by difficulty level
     */
    public void showLeaderboard() {
        // StringBuilder to accumulate text
        StringBuilder leaderboardText = new StringBuilder();

        //  for "Easy" level scores
        boolean easyLevelExist = false;
        // Flag to track if any "Easy" level scores are found
        leaderboardText.append("Leaderboard - Easy Level:\n");
        for (PlayerScore pScore : playerScoreList) {
            // Check if the player's is "Easy" level
            if (pScore.getLevel().equals("Easy")) {
                // Append the score  text
                leaderboardText.append(pScore).append("\n");
                easyLevelExist = true;
            }
        }
        // If no scores found for "Easy" level, add a message
        if (!easyLevelExist) {
            leaderboardText.append("No scores available for Easy level.\n");
        }

        //  for "Medium" level scores
        boolean mediumLevelExist = false;
        // Flag to track if any "Medium" level scores are found
        leaderboardText.append("\nLeaderboard - Medium Level:\n");
        for (PlayerScore pScore : playerScoreList) {
            // Check if the player's is "Medium" level
            if (pScore.getLevel().equals("Medium")) {
                // Append the score  text
                leaderboardText.append(pScore).append("\n");
                mediumLevelExist = true;
            }
        }
        // If no scores found for "Medium" level, add a message
        if (!mediumLevelExist) {
            leaderboardText.append("No scores available for Medium level.\n");
        }

        //  for "Hard" level scores
        boolean hardLevelExist = false;
        // Flag to track if any "Hard" level scores are found
        leaderboardText.append("\nLeaderboard - Hard Level:\n");
        for (PlayerScore pScore : playerScoreList) {
            // Check if the player's is "Hard" level
            if (pScore.getLevel().equals("Hard")) {
                leaderboardText.append(pScore).append("\n");
                hardLevelExist = true;
            }
        }
        // If no scores found for "Gard" level, add a message
        if (!hardLevelExist) {
            leaderboardText.append("No scores available for Hard level.\n");
        }

        // Display the leaderboard in dialog
        JOptionPane.showMessageDialog(frame, leaderboardText.toString());
    }
}
