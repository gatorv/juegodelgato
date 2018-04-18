package com.umed.cvalderrama.juegodelgato;

public class Board {
    private char[] boardStatus;
    private char currentPlayer;
    private boolean ended;

    public static final char MARK_BLANK = ' ';
    public static final char MARK_O = 'O';
    public static final char MARK_X = 'X';
    public static final char MARK_TIE = 'E';

    public Board() {
        boardStatus = new char[9];
        newGame();
    }

    public boolean isEnded() {
        return ended;
    }

    public char play(int x, int y) {
        if (!ended && getStatus(x, y) == MARK_BLANK) {
            setStatus(currentPlayer, x, y);
            changePlayer();
        }

        return checkEnd();
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == MARK_X ? MARK_O : MARK_X);
    }

    public char getStatus(int x, int y) {
        return boardStatus[3 * y + x];
    }

    public void setStatus(char c, int x, int y) {
        boardStatus[3 * y + x] = c;
    }

    public void newGame() {
        for (int i = 0; i < boardStatus.length; i++) {
            boardStatus[i] = MARK_BLANK;
        }

        currentPlayer = MARK_X;
        ended = false;
    }

    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (getStatus(i, 0) != MARK_BLANK &&
                    getStatus(i, 0) == getStatus(i, 1) &&
                    getStatus(i, 1) == getStatus(i, 2)) {
                ended = true;
                return getStatus(i, 0);
            }

            if (getStatus(0, i) != MARK_BLANK &&
                    getStatus(0, i) == getStatus(i, 1) &&
                    getStatus(1, i) == getStatus(2, i)) {
                ended = true;
                return getStatus(0, i);
            }
        }

        if (getStatus(0, 0) != MARK_BLANK &&
                getStatus(0, 0 ) == getStatus(1,1) &&
                getStatus(1, 1) == getStatus(2, 2)) {
            ended = true;
            return getStatus(0,0);
        }

        if (getStatus(2, 0) != MARK_BLANK &&
                getStatus(2, 0) == getStatus(1, 1) &&
                getStatus(1,1) == getStatus(0, 2)) {
            ended = true;
            return getStatus(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (boardStatus[i] == MARK_BLANK) {
                return MARK_BLANK;
            }
        }

        return MARK_TIE;
    }
}
