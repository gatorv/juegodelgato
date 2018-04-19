package com.umed.cvalderrama.juegodelgato;

/**
 * Manages the overall state of the game
 */
public class Board {
    /**
     * Constants for game marks
     */
    public static final char MARK_BLANK = ' ';
    public static final char MARK_O = 'O';
    public static final char MARK_X = 'X';
    public static final char MARK_TIE = 'E';

    /**
     * Board status
     */
    private char[] boardStatus;
    private char currentPlayer;
    private boolean ended;

    /**
     * Constructor
     */
    public Board() {
        boardStatus = new char[9];
        newGame();
    }

    /**
     * Returns whether the game has ended
     *
     * @return boolean
     */
    public boolean isEnded() {
        return ended;
    }

    /**
     * Marks a play in the board
     *
     * @param x The x position on the board
     * @param y The y position on the board
     * @return char
     */
    public char play(int x, int y) {
        if (!ended && getStatus(x, y) == MARK_BLANK) {
            setStatus(currentPlayer, x, y);
            changePlayer();
        }

        return checkEnd();
    }

    /**
     * Changes the current player
     */
    private void changePlayer() {
        currentPlayer = (currentPlayer == MARK_X ? MARK_O : MARK_X);
    }

    /**
     * Gets the status of the board
     *
     * @param x The x position on the board
     * @param y The y position on the board
     * @return char
     */
    public char getStatus(int x, int y) {
        return boardStatus[3 * y + x];
    }

    /**
     * Sets the status on the board
     *
     * @param c The players mark
     * @param x The x position on the board
     * @param y The y position on the board
     */
    private void setStatus(char c, int x, int y) {
        boardStatus[3 * y + x] = c;
    }

    /**
     * Creates a new game
     */
    public void newGame() {
        for (int i = 0; i < boardStatus.length; i++) {
            boardStatus[i] = MARK_BLANK;
        }

        currentPlayer = MARK_X;
        ended = false;
    }

    /**
     * Checks if the game is finished
     *
     * @return char
     */
    private char checkEnd() {
        for (int i = 0; i < 3; i++) {
            // Search for a Horizontal Winner
            if (getStatus(i, 0) != MARK_BLANK &&
                    getStatus(i, 0) == getStatus(i, 1) &&
                    getStatus(i, 1) == getStatus(i, 2)) {
                ended = true;
                return getStatus(i, 0);
            }

            // Search for a Vertical Winner
            if (getStatus(0, i) != MARK_BLANK &&
                    getStatus(0, i) == getStatus(1, i) &&
                    getStatus(1, i) == getStatus(2, i)) {
                ended = true;
                return getStatus(0, i);
            }
        }

        // Check first diagonal
        if (getStatus(0, 0) != MARK_BLANK &&
                getStatus(0, 0 ) == getStatus(1,1) &&
                getStatus(1, 1) == getStatus(2, 2)) {
            ended = true;
            return getStatus(0,0);
        }

        // Check second diagonal
        if (getStatus(2, 0) != MARK_BLANK &&
                getStatus(2, 0) == getStatus(1, 1) &&
                getStatus(1,1) == getStatus(0, 2)) {
            ended = true;
            return getStatus(2, 0);
        }

        // Find a blank line
        for (int i = 0; i < 9; i++) {
            if (boardStatus[i] == MARK_BLANK) {
                return MARK_BLANK;
            }
        }

        // Else is a tie
        return MARK_TIE;
    }
}
