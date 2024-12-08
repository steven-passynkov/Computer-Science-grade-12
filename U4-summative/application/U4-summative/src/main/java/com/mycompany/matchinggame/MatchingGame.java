package com.mycompany.matchinggame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * File Name: MatchingGame.java 
 * Person Programmer: Steven Passynkov
 * Date: 28 Nov 2024
 * Description: The program defines Matching Game
 */
public class MatchingGame extends JFrame {

    // panel for displaying the game board
    private JPanel boardPanel;
    //  array of buttons on the game board
    private JButton[][] buttons;
    // array of colors fo the buttons
    private Color[][] colors;
    // Tracks matched buttons
    private boolean[][] grid;

    // row for the first button clicked
    private int firstButtonRow = -1;
    // col for the first button clicked
    private int firstButtonCol = -1;
    // Reference for the first button clicked
    private JButton firstButton = null;
    // Reference for the second button clicked
    private JButton secondButton = null;

    // grid size
    private int gridSize = 3;
    // flag if the game started?
    private boolean gameStarted = false;

    //  panels for control display
    private final JPanel controlPanel;
    // Panels for score display
    private final JPanel scorePanel;
    // counter for the number of moves
    private int moveCount = 0;
    // label for the score
    private final JLabel scoreLabel;
    // current level
    private String currentLevel = "Easy";
    // create
    private final LeaderBoard leaderboard;

    /**
     * Constructor
     */
    public MatchingGame() {
        // set title of frame
        setTitle("Matching Game");
        // exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame szie
        setSize(500, 500);
        // use BorderLayout for the frame
        setLayout(new BorderLayout(0, 0));

        // Create leaderboard 
        leaderboard = new LeaderBoard(this);

        // init score panel
        scorePanel = new JPanel();
        // add score label
        scoreLabel = new JLabel("Score: 0");
        // set font on score 
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // center label
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(scoreLabel);
        // hide score panel initially
        scorePanel.setVisible(false);

        // init control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        // align horizontally
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //add padding
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combine score and control panels in the top panel
        JPanel topPanel = new JPanel();
        // set BorderLayout to topPanel
        topPanel.setLayout(new BorderLayout());
        // add scorePanel to north
        topPanel.add(scorePanel, BorderLayout.NORTH);
        // add controlPanel to center
        topPanel.add(controlPanel, BorderLayout.CENTER);

        // add topPanel
        add(topPanel, BorderLayout.NORTH);

        // Set up the main menu
        initializeMainMenu();

        // create boardPanel 
        boardPanel = new JPanel();
        // add boardPanel
        add(boardPanel, BorderLayout.CENTER);

        // set visible frame
        setVisible(true);
    }

    /**
     * Initializes the main menu for selecting level and viewing leaderboard.
     */
    private void initializeMainMenu() {
        // Clear any existing components in the control 
        controlPanel.removeAll();
        controlPanel.setLayout(new BorderLayout());

        // Add Name label
        JLabel nameLabel = new JLabel("Matching game");
        // align horizontally
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // set bold
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // add north
        controlPanel.add(nameLabel, BorderLayout.NORTH);

        // Create buttons for each difficulty level
        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        // create Easy button
        JButton easyButton = new JButton("Easy");
        // create Medium button
        JButton mediumButton = new JButton("Medium");
        // create Hard button
        JButton hardButton = new JButton("Hard");

        // add Easy button
        levelPanel.add(easyButton);
        // add Medium button
        levelPanel.add(mediumButton);
        // add Hard button
        levelPanel.add(hardButton);
        // add level panel
        controlPanel.add(levelPanel, BorderLayout.CENTER);

        // Add leaderboard button
        JButton leaderboardButton = new JButton("View Leaderboard");
        // add listener 
        leaderboardButton.addActionListener(e -> leaderboard.showLeaderboard());
        // add button to controlPanel
        controlPanel.add(leaderboardButton, BorderLayout.SOUTH);

        // Set event listener to start game for Easy
        easyButton.addActionListener(e -> startGame(2, "Easy"));
        // Set event listener to start game for Medium
        mediumButton.addActionListener(e -> startGame(4, "Medium"));
        // Set event listener to start game for Hard
        hardButton.addActionListener(e -> startGame(6, "Hard"));

        // Update the panel layout
        revalidate();
        // Redraw the panel
        repaint();
    }

    /**
     * Start game
     *
     * @param size Size of the board
     * @param level Level of game
     */
    private void startGame(int size, String level) {
        // Set grid size
        gridSize = size;
        // Update the current  level
        currentLevel = level;
        // Mark the game as started
        gameStarted = true;
        // Reset move count
        moveCount = 0;

        // Show the score panel
        scorePanel.setVisible(true);
        // Update the score display
        updateScoreLabel();

        // Clear control panel
        controlPanel.removeAll();
        // Initialize the game board
        initializeGame(gridSize);
        // Add control buttons (Back, Restart, Leaderboard)
        addControlButtons();
    }

    /**
     * Adds control buttons (Back to Menu, Restart, View Leaderboard) to the
     * control panel
     */
    private void addControlButtons() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Menu");
        JButton restartButton = new JButton("Restart");
        JButton viewButton = new JButton("View Leaderboard");

        // Button to go back to the main menu
        backButton.addActionListener(e -> {
            // if game started
            if (moveCount > 0) {
                // ask for confirmation
                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Your progress will be lost. Are you sure you want to go back to the menu?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                // if yes - go main
                if (choice == JOptionPane.YES_OPTION) {
                    resetToMainMenu();
                }
            } else {
                // go main
                resetToMainMenu();
            }
        });

        // Button to restart the game
        restartButton.addActionListener(e -> {
            if (moveCount > 0) {
                // ask for confirmation
                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Your progress will be lost. Are you sure you want to restart?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                // if yes - reinit grid size
                if (choice == JOptionPane.YES_OPTION) {
                    initializeGame(gridSize);
                }
            } else {
                // reinit grid size
                initializeGame(gridSize);
            }
        });

        // Button to view leaderboard
        viewButton.addActionListener(e -> leaderboard.showLeaderboard());
        //add back button
        buttonsPanel.add(backButton);
        //add restart button
        buttonsPanel.add(restartButton);
        //add view button
        buttonsPanel.add(viewButton);
        //add buttonsPanel to control
        controlPanel.add(buttonsPanel);

        //revalidate the board 
        revalidate();
        //repaint the board 
        repaint();
    }

    /**
     * Init the game board with given grid size
     *
     * @param size The size of grid
     */
    private void initializeGame(int size) {
        // no first button selected
        firstButton = null;
        // no second button selected
        secondButton = null;
        // reset  row index of first button
        firstButtonRow = -1;
        // reset  column index of first button
        firstButtonCol = -1;
        // reset move count
        moveCount = 0;
        // update score label
        updateScoreLabel();

        // remove any existing components on board
        boardPanel.removeAll();
        // set the grid layout for board
        boardPanel.setLayout(new GridLayout(size, size, 0, 0));
        // init the grid to keep track 
        grid = new boolean[size][size];

        // generate color pairs for the grid based on the board size
        ArrayList<Color> colorList = generateColorPairs(size * size / 2);

        // init arrays for buttons 
        buttons = new JButton[size][size];
        // init arrays for colors
        colors = new Color[size][size];

        // Loop through each row 
        for (int row = 0; row < size; row++) {
            // Loop through each column
            for (int col = 0; col < size; col++) {
                //   row for use in  lambda expression
                final int finalRow = row;
                // column for use in  lambda expression
                final int finalCol = col;

                // Create  button for  cell
                JButton button = new JButton();
                // set  background to  gray
                button.setBackground(Color.LIGHT_GRAY);
                // make the button opaque
                button.setOpaque(true);
                // add border
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // delete focusable behavior 
                button.setFocusable(false);

                // assign color from  color list to the button
                if (!colorList.isEmpty()) {
                    // get and assign  color pair
                    colors[row][col] = colorList.remove(0);
                } else {
                    // fallback colo
                    colors[row][col] = Color.BLACK;
                }

                // add  action listener to handle  click
                button.addActionListener(e -> handleButtonClick(button, finalRow, finalCol));

                // store the button and color in array
                buttons[row][col] = button;
                // add the button to board panel
                boardPanel.add(button);
            }
        }

        //revalidate the board 
        boardPanel.revalidate();
        //repaint the board 
        boardPanel.repaint();
    }

    /**
     * Updates the score label with move count
     */
    private void updateScoreLabel() {
        // Update the text of the score label 
        scoreLabel.setText("Score: " + moveCount);
    }

    /**
     * Generates list of paired colors
     *
     * @param pairCount the number of colors
     * @return an ArrayList pairs of colors
     */
    private ArrayList<Color> generateColorPairs(int pairCount) {
        // Create a list for pairs
        ArrayList<Color> colorList = new ArrayList<>();
        // Generate pairs
        for (int i = 0; i < pairCount; i++) {
            // Generate a random color 
            Color color = new Color((int) (Math.random() * 0x1000000));

            // Add the color twice to the list so there is pair
            colorList.add(color);
            colorList.add(color);
        }
        // randomize the colors
        Collections.shuffle(colorList);
        // return list
        return colorList;
    }

    /**
     * Handles game button is clicked: flipping cards, checking for matches,
     * tracking moves, and determining game completion.
     *
     * @param button button that was clicked
     * @param row row index of the clicked button
     * @param col column index of the clicked button
     */
    private void handleButtonClick(JButton button, int row, int col) {
        // if the game hasn't started or  grid cell is already matched,button is the same as the first button
        if (!gameStarted || grid[row][col] || button == firstButton) {
            return;
        }

        // Flip clicked button to show its color
        button.setBackground(colors[row][col]);

        // If no button has been previously clicked set this button as the first button
        if (firstButton == null) {
            firstButton = button;
            firstButtonRow = row;
            firstButtonCol = col;
        } else {
            // Set  button as  second button
            secondButton = button;

            // Increment the move count and update the score label
            moveCount++;
            scoreLabel.setText("Score: " + moveCount);

            // Disable all buttons to prevent clicks while processing
            disableAllButtons(true);

            // Check if the colors of the first and second buttons match
            if (colors[firstButtonRow][firstButtonCol].equals(colors[row][col])) {
                // Mark the matching cells as completed in the grid
                grid[firstButtonRow][firstButtonCol] = true;
                grid[row][col] = true;

                // Reset the first and second button references
                firstButton = null;
                secondButton = null;

                // enable all buttons
                disableAllButtons(false);
                // Check if the game is complete
                if (isGameComplete()) {
                    // Show complete screen
                    CompleteGame.show(this, moveCount);
                    // save result
                    leaderboard.saveScoreToLeaderboard(moveCount, currentLevel);

                    // end the game and return to the main menu
                    gameStarted = false;
                    resetToMainMenu();
                }
            } else {
                // If the buttons don't match
                // create  timer to reset them after a delay
                Timer timer = new Timer(500, e -> {
                    // Flip the buttons back to their default state
                    firstButton.setBackground(Color.LIGHT_GRAY);
                    secondButton.setBackground(Color.LIGHT_GRAY);

                    // Reset  first and second button references
                    firstButton = null;
                    secondButton = null;

                    // enable all buttons
                    disableAllButtons(false);
                });

                // ensure the timer runs only once
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    /**
     * Enables or disables all buttons in array of buttons.
     *
     * @param disable true to disable all buttons, false to enable them.
     */
    private void disableAllButtons(boolean disable) {
        // Iterate over each row 
        for (JButton[] row : buttons) {
            //  over each button in the row
            for (JButton button : row) {
                // Set enable/disable flag
                button.setEnabled(!disable);
            }
        }
    }

    /**
     * Checks if the game is complete - all elements in the grid are marked as
     * matched.
     *
     * @return true if matched all
     */
    private boolean isGameComplete() {
        // Iterate over each row in the grid
        for (boolean[] row : grid) {
            // Iterate over each element in the row
            for (boolean matched : row) {
                // If any element is not matched (false), the game is not complete
                if (!matched) {
                    return false;
                }
            }
        }
        // If all  matched, return true
        return true;
    }

    /**
     * Resets the application to the main menu screen.
     */
    private void resetToMainMenu() {
        // Mark the game as not started
        gameStarted = false;
        // Hide the score panel
        scorePanel.setVisible(false);
        // clean controlPanel
        controlPanel.removeAll();
        // clean boardPanel
        boardPanel.removeAll();
        // initializeMainMenu
        initializeMainMenu();
        controlPanel.revalidate();
        //Redraw the components
        controlPanel.repaint();
        // Update the layout
        boardPanel.revalidate();
        // Redraw the components
        boardPanel.repaint();
    }

    /**
     * start main
     *
     * @param args
     */
    public static void main(String[] args) {
        // run thread with new MatchingGame
        SwingUtilities.invokeLater(MatchingGame::new);
    }
}
