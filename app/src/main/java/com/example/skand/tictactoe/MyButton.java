package com.example.skand.tictactoe;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Skand on 1/29/2017.
 */

public class MyButton extends Button {

   private int player;

    public MyButton(Context context) {
        super(context);
    }


    public void setPlayer(int p)
    {
        player=p;
    }
    public int getPlayer()
    {
        return player;
    }


}
