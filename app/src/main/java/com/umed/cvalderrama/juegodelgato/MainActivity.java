package com.umed.cvalderrama.juegodelgato;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.umed.cvalderrama.juegodelgato.views.BoardView;

/**
 * MainActivity that handles the game
 */
public class MainActivity extends AppCompatActivity {
    private BoardView boardView;
    private Board gameBoard;

    /**
     * Overrides the onCreate to create a new game
     *
     * @param savedInstanceState The savedInstance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardView = (BoardView) findViewById(R.id.board);
        gameBoard = new Board();
        boardView.setBoard(gameBoard);
        boardView.setMainActivity(this);
    }

    /**
     * Overrides the onCreateOptions callback to draw the menu
     *
     * @param menu The menu from the window
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Callback when a menu option is selected
     *
     * @param item The menu Item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the game is finished
     * @param c The game mark that won or tie
     */
    public void gameEnded(char c) {
        int msgCode = (c == Board.MARK_TIE) ? R.string.empate : R.string.ganador;
        String message = String.format(getString(msgCode), c);

        new AlertDialog.Builder(this).setTitle(R.string.app_name)
                .setMessage(message)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        newGame();
                    }
                }).show();
    }

    /**
     * Creates a newGame
     */
    private void newGame() {
        gameBoard.newGame();
        boardView.invalidate();
    }
}
