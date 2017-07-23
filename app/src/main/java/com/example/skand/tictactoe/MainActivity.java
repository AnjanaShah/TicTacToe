package com.example.skand.tictactoe;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int n = 3;
    boolean player1Turn;
    MyButton button[][];
    LinearLayout rows[];
    LinearLayout mainLayout;
    boolean gameOver;
    public final static int NO_PLAYER = 0;
    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;
    public final static int PLAYER_1_WINS = 1;
    public final static int PLAYER_2_WINS = 2;
    public final static int DRAW = 3;
    public final static int INCOMPLETE = 4;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);

        setUpBoard();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void setUpBoard() {
        player1Turn = true;
        mainLayout.removeAllViews();
        button = new MyButton[n][n];
        rows = new LinearLayout[n];
        gameOver = false;
        mainLayout.removeAllViews();
        for (int i = 0; i < n; i++) {

            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            params.setMargins(5, 5, 5, 5);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);


        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(5, 5, 5, 5);
                button[i][j].setLayoutParams(params);
                button[i][j].setText("");
                button[i][j].setPlayer(NO_PLAYER);
                button[i][j].setOnClickListener(this);
                rows[i].addView(button[i][j]);

            }
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.newGame) {
            resetBoard();
        } else if (id == R.id.boardSize_3) {

            if (n != 3) {
                n = 3;

                setUpBoard();
            } else {
                resetBoard();
            }


        } else if (id == R.id.boardSize_4) {
            if (n != 4) {
                n = 4;

                setUpBoard();
            } else {
                resetBoard();
            }

        } else if (id == R.id.boardSize_5) {
            if (n != 5) {
                n = 5;
                setUpBoard();
            } else {
                resetBoard();
            }
        }
        return true;
    }

    private void resetBoard() {
        gameOver = false;
        player1Turn = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j].setPlayer(NO_PLAYER);
                button[i][j].setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        MyButton b = (MyButton) v;
        if (gameOver) {
            return;
        }
        if (b.getPlayer() != NO_PLAYER) {

            Toast.makeText(this, "INVALID MOVE", Toast.LENGTH_SHORT).show();
            return;

        }
        if (player1Turn) {
            b.setPlayer(PLAYER1);
            b.setText("0");

        } else {
            b.setPlayer(PLAYER2);
            b.setText("X");
        }
        int status = gameStatus();
        if (status == PLAYER_1_WINS || status == PLAYER_2_WINS) {
            gameOver = true;
            Toast.makeText(this, "PLAYER " + status + " WINS!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (status == DRAW) {
            gameOver = true;
            Toast.makeText(this, "DRAW!", Toast.LENGTH_SHORT).show();
            return;

        }
        player1Turn = !player1Turn;

    }

    private int gameStatus() {

        int i, j;
        boolean f;
        for (i = 0; i < n; i++) {
            f = true;
            for (j = 0; j < n; j++) {
                if (button[i][j].getPlayer() != button[i][0].getPlayer() || button[i][j].getPlayer() == NO_PLAYER) {
                    f = false;
                    break;
                }
            }
            if (f) {
                gameOver = true;
                if (button[i][0].getPlayer() == PLAYER1) {
                    return PLAYER_1_WINS;
                } else {
                    return PLAYER_2_WINS;
                }
            }

        }

        //columms
        for (j = 0; j < n; j++) {
            f = true;
            for (i = 0; i < n; i++) {
                if (button[i][j].getPlayer() != button[0][j].getPlayer() || button[i][j].getPlayer() == NO_PLAYER) {
                    f = false;
                    break;
                }
            }
            if (f) {
                gameOver = true;
                if (button[0][j].getPlayer() == PLAYER1) {
                    return PLAYER_1_WINS;
                } else {
                    return PLAYER_2_WINS;
                }
            }

        }
        //diagnal
        f = true;
        for (i = 0; i < n; i++) {
            if (button[i][i].getPlayer() != button[0][0].getPlayer() || button[i][i].getPlayer() == NO_PLAYER) {
                f = false;
                break;
            }

        }
        if (f) {
            gameOver = true;
            if (button[0][0].getPlayer() == PLAYER1) {
                return PLAYER_1_WINS;
            } else {
                return PLAYER_2_WINS;
            }
        }
        //diagnal 2
        f = true;
        for (i = 0; i < n; i++) {
            if (button[i][n - i - 1].getPlayer() != button[0][n - 1].getPlayer() || button[i][n - 1 - i].getPlayer() == NO_PLAYER) {
                f = false;
                break;
            }

        }
        if (f) {
            gameOver = true;
            if (button[0][n - 1].getPlayer() == PLAYER1) {
                return PLAYER_1_WINS;
            } else {
                return PLAYER_2_WINS;
            }

        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (button[i][j].getPlayer() == NO_PLAYER) {
                    return INCOMPLETE;
                }
            }
        }
        return DRAW;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
