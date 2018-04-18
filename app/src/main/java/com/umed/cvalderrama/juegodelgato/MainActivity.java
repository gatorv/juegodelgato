package com.umed.cvalderrama.juegodelgato;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.umed.cvalderrama.juegodelgato.views.BoardView;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;
    private Board gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardView = (BoardView) findViewById(R.id.board);
        gameBoard = new Board();
        boardView.setBoard(gameBoard);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

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

    private void newGame() {
        gameBoard.newGame();
        boardView.invalidate();
    }
}
