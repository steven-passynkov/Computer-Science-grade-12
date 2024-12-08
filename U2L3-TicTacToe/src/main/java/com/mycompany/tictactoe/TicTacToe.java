package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * File Name:	Tic Tac Toe game 
 * Programmer:	Steven Passynkov 
 * Date:	9 Jun 2024
 * Description: Program allows you to run tic tac toe game manually X and 0 or 
 * using bot that will play 0. Will show details about possible wins and blocks.
 * Will play an audio on win. Allow to go back and forward to see history.
 */
public class TicTacToe extends JFrame {

    // Array of grid chars
    private char[][] grid;
    // current player
    private char currentPlayer = 'X';
    // Array of move coords in history {row, col}
    private List<int[]> moveHistory = new ArrayList<>();
    // Array of {current player char, string of potential missed wins (one move in future)}
    private List<String[]> potentialMissedMoves = new ArrayList<>();
    // Array of string of missed wins that describes a win move that is missed
    private List<String> missedWins = new ArrayList<>();
    // Array of string of missed blocks that describes a block move that is missed
    private List<String> missedBlocks = new ArrayList<>();
    // Is game in progress
    private boolean gameInProgress = false;
    // Current grid size
    private JTextField gridSizeInput;
    ///private JLabel currentPlayerLabel;
    private JLabel resultLabel;
    // New game button
    private JButton newGameButton;
    // show Misssed Win buttons
    private JButton missedWinsButton;
    // Back button to navigate in history
    private JButton backButton;
    // Forward button to navigate in history
    private JButton forwardButton;
    private int historyIndex = -1;
    // number of scoreX won
    private int scoreX = 0;
    // number of scoreO won
    private int scoreO = 0;
    // Is bot active
    private boolean botActive = false;
    // Generator for random move for bot
    private Random random = new Random();
    // Grid empty char
    private char emptySpace = '\u0000';

    /**
     * Construct a game
     */
    public TicTacToe() {
        // init swing components
        initComponents();
    }

    /**
     * Init swing components
     */
    private void initComponents() {
        // set frame title
        setTitle("Tic Tac Toe");
        // on close btn click - close the game
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set width and high of frame
        setSize(600, 400);
        // set layout
        setLayout(new BorderLayout());

        // create top (header) panel
        JPanel topPanel = new JPanel(new BorderLayout());
        // create a label with with palyer role does the next turn
        ///currentPlayerLabel = new JLabel("Current Move: Player X", SwingConstants.CENTER);
        ///topPanel.add(currentPlayerLabel, BorderLayout.NORTH);

        ///JPanel labelsPanel = new JPanel(new GridLayout(2, 1));
        JPanel labelsPanel = new JPanel(new GridLayout(1, 1));
        ///labelsPanel.add(currentPlayerLabel);

        // create label with result of X and 0
        resultLabel = new JLabel("Result - Player X: 0 | Player O: 0", SwingConstants.CENTER);
        // add result label to labelsPanel
        labelsPanel.add(resultLabel);

        // add labelsPanel to topPanel
        topPanel.add(labelsPanel, BorderLayout.CENTER);

        // create panel for grid size
        JPanel gridSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // create label for gridSizeInput
        JLabel gridSizeLabel = new JLabel("Enter Grid Size:");
        // create input for grid size with 5 chars width
        gridSizeInput = new JTextField(5);
        // set default grid size to 3 
        gridSizeInput.setText("3");
        // add label to panel
        gridSizePanel.add(gridSizeLabel);
        // add input for panel
        gridSizePanel.add(gridSizeInput);
        // add gridSizePanel to panel
        topPanel.add(gridSizePanel, BorderLayout.SOUTH);
        // add to panel to main panel
        add(topPanel, BorderLayout.NORTH);

        // add handler for gridSizeInput
        gridSizeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // convert string to integer
                    int newSize = Integer.parseInt(gridSizeInput.getText());
                    // size can be between 3 and 10 
                    if (newSize >= 3 && newSize <= 10) {
                        // update boarding size with # columns and rows
                        updateBoardSize(newSize);
                        // reset the current game
                        resetGame();
                    } else {
                        // show the message if number is not between 3 and 10
                        JOptionPane.showMessageDialog(TicTacToe.this, "Grid size should be between 3 and 10", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // cacth error message
                    JOptionPane.showMessageDialog(TicTacToe.this, "Invalid input. Please enter a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // create board panel
        JPanel boardPanel = new JPanel();
        // add it to the center
        add(boardPanel, BorderLayout.CENTER);
        // update size to 3 cols*rows
        updateBoardSize(3);

        // add botton panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // create Reset game button
        JButton resetButton = new JButton("Reset Game");
        // add event listener for resetButton
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // call reset game method when button is clicked
                resetGame();
            }
        });
        // add resetButton to panel
        bottomPanel.add(resetButton);

        // add Show Missed Wins button
        missedWinsButton = new JButton("Show Missed Wins");
        // make it invisible on start
        missedWinsButton.setVisible(false);
        // add listener for missedWinsButton
        missedWinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show anlysis dialog when the button is clicked
                displayMatchAnalysisDialog();
            }
        });
        // add missedWinsButton to panel
        bottomPanel.add(missedWinsButton);

        // add back button - show that was in previous step
        backButton = new JButton("Back");
        // make it invisible on start
        backButton.setVisible(false);
        // add listener for backButton
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // navigate back when the button is clicked
                navigateBack();
            }
        });
        // add bottomPanel to panel
        bottomPanel.add(backButton);

        // add Forward button - show that was in next step
        forwardButton = new JButton("Forward");
        // make it invisible on start
        forwardButton.setVisible(false);
        // add listener for forwardButton
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // navigate forward when the button is clicked
                navigateForward();
            }
        });
        // add forwardButton to panel
        bottomPanel.add(forwardButton);

        // Add toggle button for Bot that will play with you
        JToggleButton botToggleButton = new JToggleButton("Bot: OFF");
        // Add listener for the button
        botToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if game is not running
                if (!gameInProgress || isEmptyGrid(grid)) {
                    // find if bot is selected save it in botActive
                    botActive = botToggleButton.isSelected();
                    // update Bot state button
                    botToggleButton.setText(botActive ? "Bot: ON" : "Bot: OFF");
                } else {
                    // keep botActive if game is running uisng botActive
                    botToggleButton.setSelected(botActive);
                }
            }
        });
        // add botToggleButton to panel
        bottomPanel.add(botToggleButton);

        // New Game button
        newGameButton = new JButton("New Game");
        // make it invisible on start
        newGameButton.setVisible(false);
        // add handler for button
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // New Game => Reset game
                resetGame();
            }
        });
        // add newGameButton to panel
        bottomPanel.add(newGameButton);
        // add bottomPanel to main frame
        add(bottomPanel, BorderLayout.SOUTH);
        // reset game
        resetGame();
    }

    /**
     * Handle button click
     *
     * @param row - row on grid
     * @param col - cell on grid
     */
    private void buttonClicked(int row, int col) {
        // create row, col pair
        int[] coords = {row, col};
        // if not win and game in progress
        if (!checkWinner() && gameInProgress) {
            // if empty cell
            if (grid[row][col] == emptySpace) {
                // set grid cell to currentPlayer
                grid[row][col] = currentPlayer;
                // get button of the cell
                JButton button = (JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(row * grid.length + col);
                // set button label to currentPlayer
                button.setText(String.valueOf(currentPlayer));
                // disable button
                button.setEnabled(false);
                // do not focus
                button.setFocusable(false);
                // add pair to history
                addToHistory(coords);

                // get move number as moveHistory length
                int moveNumber = moveHistory.size();

                // analyzeMove for move to find missed win (potential)
                int[] missedWinCoords = analyzeMove(row, col);
                // if exist missed win
                if (missedWinCoords != null) {
                    // create string that show missed move
                    String coordsString = "(" + (missedWinCoords[0] + 1) + "," + (missedWinCoords[1] + 1) + ")";
                    // create string that show on what move # it happen
                    String moveInfo = "Move " + moveNumber + ": " + coordsString;
                    // check if move not in list of potential missed moves
                    if (!hasDuplicatePotentialMove(String.valueOf(currentPlayer), moveInfo)) {
                        // add potential missed move to array
                        potentialMissedMoves.add(new String[]{String.valueOf(currentPlayer), moveInfo});
                    }
                }

                // check if win after this move
                if (checkWinner()) {
                    try {
                        // load audio stream
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("chear.wav"));
                        // get clip
                        Clip clip = AudioSystem.getClip();
                        // Opens the clip
                        clip.open(audioInputStream);
                        // Run the audio clip
                        clip.start();
                    } catch (Exception e) {
                        // handle exception if can't load file
                        e.printStackTrace();
                    }
                    // Show result who won
                    showResult("Player " + currentPlayer + " wins!");
                    // update the score
                    if (currentPlayer == 'X') {
                        // if X won -> increate its score
                        scoreX++;
                    } else {
                        // if ) won -> increate its score
                        scoreO++;
                    }
                    // update gameInProgress flag to false
                    gameInProgress = false;
                    // display match analysis dialog
                    displayMatchAnalysisDialog();
                    // show New Game button
                    newGameButton.setVisible(true);
                    // Show Misses Win game analisys button
                    missedWinsButton.setVisible(true);
                    // Show back button to move back in the game
                    backButton.setVisible(true);
                } else if (checkDraw()) { // if draw
                    // Show that it is draw
                    showResult("It's a draw!");
                    // update gameInProgress flag to false
                    gameInProgress = false;
                    // display match analysis dialog
                    displayMatchAnalysisDialog();
                    // show New Game button
                    newGameButton.setVisible(true);
                    // Show Misses Win game analisys button
                    missedWinsButton.setVisible(true);
                    // Show back button to move back in the game
                    backButton.setVisible(true);
                } else {
                    // game is still running - check missed wins
                    checkMissedWins(currentPlayer);
                    // check missed blocks
                    checkMissedBlocks(currentPlayer);
                    // switch player
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    // update result
                    updateResultLabel();
                }
            }
        }

        // If bot is ON and bot turn and game in progress
        if (botActive && !checkWinner() && gameInProgress && currentPlayer == 'O') {
            // do bot move
            botMove();
        }
    }

    /**
     * Check if win happen
     *
     * @return True if win, false if not
     */
    private boolean checkWinner() {
        //size of grid
        int size = grid.length;
        // iterate over size
        for (int i = 0; i < size; i++) {
            // if rows or column in the index win - return true
            if (checkLine(getRow(i)) || checkLine(getColumn(i))) {
                return true;
            }
        }

        // if diagonal left-right win or diagonal right-left win - return true
        if (checkLine(getDiagonal(true)) || checkLine(getDiagonal(false))) {
            return true;
        }

        // no lines that containe win
        return false;
    }

    /**
     * Get chars of row
     *
     * @param row - row number
     * @return - Get chars of row
     */
    private char[] getRow(int row) {
        // return chars of row
        return grid[row];
    }

    /**
     * Get chars of col
     *
     * @param col col number
     * @return - Get chars of col
     */
    private char[] getColumn(int col) {
        //create a new array
        char[] column = new char[grid.length];
        // go over grid
        for (int i = 0; i < grid.length; i++) {
            // copy from grid to column array values
            column[i] = grid[i][col];
        }
        // return column array
        return column;
    }

    /**
     *
     * @param isLeftRight Is Left to right
     * @return Get array of charts for diagonal
     */
    private char[] getDiagonal(boolean isLeftRight) {
        // create diagonal array
        char[] diagonal = new char[grid.length];
        // if Left to right
        if (isLeftRight) {
            // go over rows
            for (int i = 0; i < grid.length; i++) {
                // populate diagonal array with diagonal cell 
                diagonal[i] = grid[i][i];
            }
        } else {
            // go over rows
            for (int i = 0; i < grid.length; i++) {
                // populate diagonal array with diagonal cell in opposite diretion
                diagonal[i] = grid[i][grid.length - 1 - i];
            }
        }
        // return diagonal
        return diagonal;
    }

    /**
     * Check if line is winning
     *
     * @param line - array of char in line
     * @return True - win, false - not win
     */
    private boolean checkLine(char[] line) {
        // get first char fron array
        char firstChar = line[0];
        // go over all array
        for (char c : line) {
            // if char is not first one or empty - not winning line
            if (c != firstChar || c == emptySpace) {
                return false;
            }
        }
        // if we are here - line is winning
        return true;
    }

    /**
     * Check draw - if no empty cells - draw
     *
     * @return True draw, else false
     */
    private boolean checkDraw() {
        // iterate over rows
        for (int row = 0; row < grid.length; row++) {
            // iterate over columns
            for (int col = 0; col < grid[row].length; col++) {
                // if value of cell empty
                if (grid[row][col] == emptySpace) {
                    // return false
                    return false;
                }
            }
        }
        // draw - return true
        return true;
    }

    /**
     * Check if potentialMissedMoves has player and moveInfo
     *
     * @param player
     * @param moveInfo
     * @return True found, false not found
     */
    private boolean hasDuplicatePotentialMove(String player, String moveInfo) {
        for (String[] missedMove : potentialMissedMoves) {
            // check if missedMove[0] === player char and message === potentialMissedMove.missage
            if (missedMove[0].equals(player) && missedMove[1].equals(moveInfo)) {
                // found - return true
                return true;
            }
        }
        // not found - return false
        return false;
    }

    /**
     * Analyze Move to find a potential missed moved
     *
     * @param row row index
     * @param col col index
     * @return Return potential missed moved or null
     */
    private int[] analyzeMove(int row, int col) {
        // get currentPlayer
        char playerSymbol = currentPlayer;
        // grid size
        int gridSize = grid.length;
        // # of cell in row that player is already clicked before next win move
        int requiredCount = gridSize - 1;

        // # of empty counts in row
        int emptyCount = 0;
        // # of players counts in row
        int playerCount = 0;
        // iterate over grid columns
        for (int c = 0; c < gridSize; c++) {
            // if cell in col and row is empty
            if (grid[row][c] == emptySpace) {
                // increate # of empty counts
                emptyCount++;
            } else if (grid[row][c] == playerSymbol) {
                // if char is player char - increase play counts
                playerCount++;
            }
        }
        // only one cell is left in column and player has less less one move
        if (emptyCount == 1 && playerCount == requiredCount) {
            // iterate over grid columns
            for (int c = 0; c < gridSize; c++) {
                // if it is empty - return as next win
                if (grid[row][c] == emptySpace) {
                    return new int[]{row, c};
                }
            }
        }

        // # of empty counts in col
        emptyCount = 0;
        // # of players counts in col
        playerCount = 0;
        // iterate over grid rows
        for (int r = 0; r < gridSize; r++) {
            // if cell in col and row is empty
            if (grid[r][col] == emptySpace) {
                emptyCount++;
            } else if (grid[r][col] == playerSymbol) {
                // if char is player char - increase play counts
                playerCount++;
            }
        }
        // only one cell is left in row and player has less less one move
        if (emptyCount == 1 && playerCount == requiredCount) {
            // iterate over grid row
            for (int r = 0; r < gridSize; r++) {
                // if it is empty - return as next win
                if (grid[r][col] == emptySpace) {
                    return new int[]{r, col};
                }
            }
        }

        // this is diagonal cell - right to left
        if (row == col) {
            // # of empty counts in diagonal
            emptyCount = 0;
            // # of players counts in diagonal
            playerCount = 0;
            // iterate over diagonal
            for (int i = 0; i < gridSize; i++) {
                // if diagonal cell is empty
                if (grid[i][i] == emptySpace) {
                    // increase emptyCount
                    emptyCount++;
                } else if (grid[i][i] == playerSymbol) {
                    // if char is player char - increase play counts
                    playerCount++;
                }
            }
            // only one cell is left in diagonal and player has less less one move
            if (emptyCount == 1 && playerCount == requiredCount) {
                for (int i = 0; i < gridSize; i++) {
                    // if it is empty - return as next win
                    if (grid[i][i] == emptySpace) {
                        return new int[]{i, i};
                    }
                }
            }
        }

        // this is diagonal cell -  left to right
        if (row + col == gridSize - 1) {
            // # of empty counts in diagonal
            emptyCount = 0;
            // # of players counts in diagonal
            playerCount = 0;
            // iterate over grid
            for (int i = 0; i < gridSize; i++) {
                if (grid[i][gridSize - 1 - i] == emptySpace) {
                    // increase emptyCount
                    emptyCount++;
                } else if (grid[i][gridSize - 1 - i] == playerSymbol) {
                    // if char is player char - increase play counts
                    playerCount++;
                }
            }
            // only one cell is left in diagonal and player has less less one move
            if (emptyCount == 1 && playerCount == requiredCount) {
                for (int i = 0; i < gridSize; i++) {
                    // if it is empty - return as next win
                    if (grid[i][gridSize - 1 - i] == emptySpace) {
                        return new int[]{i, gridSize - 1 - i};
                    }
                }
            }
        }

        // not found - win
        return null;
    }

    /**
     * Make bot to execute move
     */
    private void botMove() {
        // get winning coord current player
        int[] moveCoords = analyzeMoveForWin(currentPlayer);
        // if no winning
        if (moveCoords == null) {
            // check another user winning coord
            moveCoords = analyzeMoveForWin((currentPlayer == 'X') ? 'O' : 'X');
        }

        // if still no wining move - try to find empty coord just to move
        if (moveCoords == null) {
            do {
                // generate move coord randomly
                moveCoords = new int[]{random.nextInt(grid.length), random.nextInt(grid.length)};
            } while (grid[moveCoords[0]][moveCoords[1]] != emptySpace);
        }

        // we have moveCoords
        if (moveCoords != null) {
            // emulate click button for this coordinate
            buttonClicked(moveCoords[0], moveCoords[1]);
        }
    }

    /**
     * Analyze player move
     *
     * @param player - player char
     * @return
     */
    private int[] analyzeMoveForWin(char player) {
        // grid size
        int size = grid.length;

        // iterate over size
        for (int i = 0; i < size; i++) {
            // get wining coordinate for row
            int[] rowCoords = analyzeRowForWin(player, i);
            // if return something - win
            if (rowCoords != null) {
                return rowCoords;
            }

            // get wining coordinate for col
            int[] colCoords = analyzeColForWin(player, i);
            // if return something - win
            if (colCoords != null) {
                return colCoords;
            }
        }

        // get wining coordinate for Diagonal right to left
        int[] diagCoords = analyzeDiagonalForWin(player, true);
        // if return something - win
        if (diagCoords != null) {
            return diagCoords;
        }

        // get wining coordinate for Diagonal left to right
        diagCoords = analyzeDiagonalForWin(player, false);
        // if return something - win
        if (diagCoords != null) {
            return diagCoords;
        }

        // no winning coord
        return null;
    }

    /**
     * Check if player won in specific row
     *
     * @param player - char of player
     * @param row - row index
     * @return Win coordinate (row, col) or null
     */
    private int[] analyzeRowForWin(char player, int row) {
        // grid size
        int size = grid.length;
        // empty cell counts
        int emptyCount = 0;
        // number of cells in the row with player chars
        int playerCount = 0;
        // empty Coords 
        int[] emptyCoords = null;

        // iterate over col
        for (int col = 0; col < size; col++) {
            // if cell is empty
            if (grid[row][col] == emptySpace) {
                // increase cell counts
                emptyCount++;
                // populate empty Coords with current cell
                emptyCoords = new int[]{row, col};
            } else if (grid[row][col] == player) {
                // if cell has player char - increase playerCount
                playerCount++;
            }
        }

        // only one cell left and # of player charts is less than max (grid size)
        if (emptyCount == 1 && playerCount == size - 1) {
            // return this empty coordinate
            return emptyCoords;
        }

        // otherwise - return null
        return null;
    }

    /**
     * Check if player won in specific column
     *
     * @param player - char of player
     * @param row - col index
     * @return Win coordinate (row, col) or null
     */
    private int[] analyzeColForWin(char player, int col) {
        // grid size
        int size = grid.length;
        // empty cell counts
        int emptyCount = 0;
        // number of cells in the col with player chars
        int playerCount = 0;
        // empty Coords 
        int[] emptyCoords = null;

        // iterate over rows
        for (int row = 0; row < size; row++) {
            // if cell is empty
            if (grid[row][col] == emptySpace) {
                // increase cell counts
                emptyCount++;
                // populate empty Coords with current cell
                emptyCoords = new int[]{row, col};
            } else if (grid[row][col] == player) {
                // if cell has player char - increase playerCount
                playerCount++;
            }
        }

        // only one cell left and # of player charts is less than max (grid size)
        if (emptyCount == 1 && playerCount == size - 1) {
            // return this empty coordinate
            return emptyCoords;
        }

        // otherwise - return null
        return null;
    }

    /**
     * Check if player won in diagonal
     *
     * @param player - char of player
     * @param isLrftRigt - direction right to left or left to right
     * @return Win coordinate (row, col) or null
     */
    private int[] analyzeDiagonalForWin(char player, boolean isLrftRigt) {
        // size of grid
        int size = grid.length;
        // empty cell counts
        int emptyCount = 0;
        // umber of cells in the col with player chars
        int playerCount = 0;
        // empty Coords 
        int[] emptyCoords = null;

        // iterate over size
        for (int i = 0; i < size; i++) {
            // if left to right diagonal
            if (isLrftRigt) {
                // check diagonal cell if it is empty
                if (grid[i][i] == emptySpace) {
                    // increase empty count
                    emptyCount++;
                    // assign emptyCoords to diagonal cell
                    emptyCoords = new int[]{i, i};
                } else if (grid[i][i] == player) {
                    // if cell value === player chars -> increase playerCount
                    playerCount++;
                }
            } else {
                // check diagonal cell if it is empty
                if (grid[i][size - 1 - i] == emptySpace) {
                    // increase empty count
                    emptyCount++;
                    // assign emptyCoords to diagonal cell
                    emptyCoords = new int[]{i, size - 1 - i};
                } else if (grid[i][size - 1 - i] == player) {
                    // if cell value === player chars -> increase playerCount
                    playerCount++;
                }
            }
        }

        // only one cell left and # of player charts is less than max (grid size)
        if (emptyCount == 1 && playerCount == size - 1) {
            // return this empty coordinate
            return emptyCoords;
        }

        // otherwise - return null
        return null;
    }

    /**
     * Go over potential misses and populate missedWins
     *
     * @param currentPlayer
     */
    private void checkMissedWins(char currentPlayer) {
        // iterate over potentialMissedMoves
        for (String[] missedWin : potentialMissedMoves) {
            // player - first in array
            String player = missedWin[0];
            // description of move - second in array
            String moveInfo = missedWin[1];
            // split move number and coordinate
            String[] moveInfoParts = moveInfo.split(": ");

            // if player char is currentPlayer
            if (player.charAt(0) == currentPlayer) {
                // get mover number as sub-string
                int moveNumber = Integer.parseInt(moveInfoParts[0].substring(5));
                // create missedWinMessage
                String missedWinMessage = "Player " + currentPlayer + " missed win at move " + moveInfo + " (Move " + moveNumber + ")";
                // Remove dublicates
                if (!hasDuplicateMissedWin(missedWinMessage)) {
                    // add missedWinMessage to array of missedWins
                    missedWins.add(missedWinMessage);
                }
            }
        }
    }

    /**
     * Return if message is already in missedWin array
     *
     * @param moveInfo - message
     * @return If message already there, otherwise false
     */
    private boolean hasDuplicateMissedWin(String moveInfo) {
        // iterate over missedWins
        for (String missedWin : missedWins) {
            // if message from array contains moveInfo
            if (missedWin.contains(moveInfo)) {
                // if contains - true
                return true;
            }
        }
        // not found - false
        return false;
    }

    /**
     * Check missed move to block another player
     *
     * @param currentPlayer - char of current player
     */
    private void checkMissedBlocks(char currentPlayer) {
        // iterate over potentialMissedMoves
        for (String[] missedMove : potentialMissedMoves) {
            // description of move - second in array
            String moveInfo = missedMove[1];
            // split move number and coordinate
            String[] moveInfoParts = moveInfo.split(": ");

            // get missed mover number as sub-string
            int missedMoveNumber = Integer.parseInt(moveInfoParts[0].substring(5));
            // 
            if (moveHistory.size() >= missedMoveNumber + 1) {
                // get mover number as sub-string
                int moveNumber = Integer.parseInt(moveInfoParts[0].substring(5));
                // create missedBlockMessage
                String missedBlockMessage = "Player " + currentPlayer + " missed block at move " + moveInfo + " (Move " + moveNumber + ")";
                // if no doublicates 
                if (!hasDuplicateMissedBlock(missedBlockMessage)) {
                    // add missedBlockMessage to missedBlocks
                    missedBlocks.add(missedBlockMessage);
                }
            }
        }
    }

    /**
     * check duplicates in missedBlock
     *
     * @param moveInfo messages to check
     * @return Return true if duplicate
     */
    private boolean hasDuplicateMissedBlock(String moveInfo) {
        // iterate over missedBlocks
        for (String missedBlock : missedBlocks) {
            // check missedBlock contain moveInfo message
            if (missedBlock.contains(moveInfo)) {
                // return true if duplicate
                return true;
            }
        }
        // not found - false
        return false;
    }

    /**
     * Display display Match Analysis Dialog
     */
    private void displayMatchAnalysisDialog() {
        // if go x down and 0 down - elminate extra miss
        if (!missedWins.isEmpty()) {
            // get message
            String moveInfo = missedWins.get(missedWins.size() - 1);
            // get move number message and coordinates
            String[] moveInfoParts = moveInfo.split(": ");
            // get only digists fro the message
            String digitsOnly = Arrays.toString(moveInfoParts).replaceAll("[^\\d]", "");
            // last move number
            int lastNumber = Integer.parseInt(String.valueOf(digitsOnly.charAt(digitsOnly.length() - 1)));

            // if history has lastNumber - remove it
            if (moveHistory.size() - 1 == lastNumber) {
                removeLastIndex(missedWins);
            }
        }

        // create string buffer to add messages
        StringBuilder missedInfoMessage = new StringBuilder();
        // if there are missedWins
        if (!missedWins.isEmpty()) {
            // add header
            missedInfoMessage.append("Missed Wins:\n");
            // iterate over missedWins
            for (String missedWinInfo : missedWins) {
                // add message and "\n"
                missedInfoMessage.append(missedWinInfo).append("\n");
            }
        }
        // if there are missed blocks
        if (!missedBlocks.isEmpty()) {
            // add header
            missedInfoMessage.append("\nMissed Blocks:\n");
            // iterate over missedBlocks
            for (String missedBlockInfo : missedBlocks) {
                // add message and "\n"
                missedInfoMessage.append(missedBlockInfo).append("\n");
            }
        }

        if (missedInfoMessage.length() > 0) {
            // there are missed messages - show them
            JOptionPane.showMessageDialog(this, missedInfoMessage.toString(), "Missed Wins and Blocks", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // show message - no missed wins
            JOptionPane.showMessageDialog(this, "No missed wins or blocks", "Missed Wins and Blocks", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Remove last index from list
     *
     * @param list
     */
    private void removeLastIndex(List list) {
        // if list if not empty
        if (!list.isEmpty()) {
            // remove last one
            list.remove(list.size() - 1);
        }
    }

    /**
     * Add coords pair to history
     *
     * @param coords
     */
    private void addToHistory(int[] coords) {
        // if historyIndex is not the last
        if (historyIndex < moveHistory.size() - 1) {
            // clear history slot
            moveHistory.subList(historyIndex + 1, moveHistory.size()).clear();
        }
        // add coordinate
        moveHistory.add(coords);
        // increate history index
        historyIndex++;
        // update history buttons
        updateHistoryButtons();
    }

    /**
     * Navigate back to see state of board
     */
    private void navigateBack() {
        // if still has history
        if (historyIndex > 0) {
            // descrease history index
            historyIndex--;
            // update board using history data
            updateBoardFromHistory();
            // update histort button
            updateHistoryButtons();
        }
    }

    /**
     * Navigate forward to see state of board
     */
    private void navigateForward() {
        // check if can more forward
        if (historyIndex < moveHistory.size() - 1) {
            // increase history index
            historyIndex++;
            // update board using history data
            updateBoardFromHistory();
            // update histort button
            updateHistoryButtons();
        }
    }

    /**
     * Update history buttons
     */
    private void updateHistoryButtons() {
        // if game is running
        if (gameInProgress) {
            // back button is not visible
            backButton.setVisible(false);
            // forward button is not visible
            forwardButton.setVisible(false);
        } else {
            // back button is visible if historyIndex positive
            backButton.setVisible(historyIndex > 0);
            // forward  button is visible if possible to move forward in history
            forwardButton.setVisible(historyIndex < moveHistory.size() - 1);
        }
    }

    /**
     * Update board with history
     */
    private void updateBoardFromHistory() {
        // reset board
        resetBoard();
        // iterate till end of index history
        for (int i = 0; i <= historyIndex; i++) {
            // get row, col pair
            int[] coords = moveHistory.get(i);
            // row
            int row = coords[0];
            // col
            int col = coords[1];
            // X always first and 0 next turn. 
            grid[row][col] = (i % 2 == 0) ? 'X' : 'O';
            // get button for cell
            JButton button = (JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(row * grid.length + col);
            // update button label
            button.setText(String.valueOf(grid[row][col]));
            // enable button label
            button.setEnabled(false);
        }
        // calculate current player  -  X always first and 0 next turn
        currentPlayer = (historyIndex % 2 == 0) ? 'O' : 'X';
        // update result button
        updateResultLabel();
    }

    /**
     * Reset board grid
     */
    private void resetBoard() {
        // iterate over rows
        for (int row = 0; row < grid.length; row++) {
            // terate over columns
            for (int col = 0; col < grid.length; col++) {
                // set cell to empty in grid array
                grid[row][col] = emptySpace;
                // get button for cell
                JButton button = (JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(row * grid.length + col);
                // set the button text to ""
                button.setText("");
                // enable button
                button.setEnabled(true);
            }
        }
    }

    /**
     * Reset board
     */
    private void resetGame() {
        // reset board
        resetBoard();
        // game again in progress
        gameInProgress = true;
        // current player X
        currentPlayer = 'X';
        // update result label
        updateResultLabel();
        // New game is not visible
        newGameButton.setVisible(false);
        // Missed wins is not visible
        missedWinsButton.setVisible(false);
        // Back is not visible
        backButton.setVisible(false);
        // forward is not visible
        forwardButton.setVisible(false);
        // Clear history
        moveHistory.clear();
        // 
        potentialMissedMoves.clear();
        missedWins.clear();
        // historyIndex is -1 - no history
        historyIndex = -1;
        // update history buttons
        updateHistoryButtons();
    }

    /**
     * Set board to new size and initialize it
     *
     * @param newSize
     */
    private void updateBoardSize(int newSize) {
        // get 1st (2 after header) panel where the board is 
        JPanel boardPanel = (JPanel) getContentPane().getComponent(1);
        // remove all buttons
        boardPanel.removeAll();
        // create a grid layout with rows=newSize and columsn=newSize
        boardPanel.setLayout(new GridLayout(newSize, newSize));
        // set grid to array of newSize*newSize
        grid = new char[newSize][newSize];
        // iterate over rows 
        for (int row = 0; row < newSize; row++) {
            // iterate over columns
            for (int col = 0; col < newSize; col++) {
                // make final row so can be used in addActionListener
                final int finalRow = row;
                // make final col so can be used in addActionListener
                final int finalCol = col;
                // create a button for cell
                JButton button = new JButton();
                // do not show border on focus
                button.setFocusable(false);
                // add listener for button
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // handle button click
                        buttonClicked(finalRow, finalCol);
                    }
                });
                // add button to board
                boardPanel.add(button);
            }
        }
        // validate and repain when layout panel is re-inserted
        validate();
        repaint();
    }

    /**
     * Update result label with scores
     */
    private void updateResultLabel() {
        // set # of X won and number of 0 won
        resultLabel.setText("Result Player X: " + scoreX + " | Player O: " + scoreO);
    }

    /**
     * Show result for game
     *
     * @param message
     */
    private void showResult(String message) {
        resultLabel.setText(message);
    }

    /**
     * Check if grid has data
     *
     * @param grid - grid array
     * @return True - empty, false - not empty
     */
    private boolean isEmptyGrid(char[][] grid) {
        // iterate over rows
        for (char[] row : grid) {
            // iterate over cols
            for (char cell : row) {
                // if cell is not empty
                if (cell != emptySpace) {
                    // return false
                    return false;
                }
            }
        }
        // return true if no cell empty
        return true;
    }

    /**
     * Start swing application - main method
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create TicTacToe class and make frame wisible
                new TicTacToe().setVisible(true);
            }
        });
    }
}
