package com.example.administrator.mytictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Cootie_GamePlay extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cootie__game_play);

        //initialize cootie objects
        final Cootie player1 = new Cootie();
        final Cootie player2 = new Cootie();
        final int[] count = {0};

        //wire buttons
        final CheckBox cbBody1 = (CheckBox)findViewById(R.id.cbBody1);
        final CheckBox cbBody2 = (CheckBox)findViewById(R.id.cbBody2);
        final CheckBox cbHead1 = (CheckBox)findViewById(R.id.cbHead1);
        final CheckBox cbHead2 = (CheckBox)findViewById(R.id.cbHead2);
        final CheckBox cbLegs1 = (CheckBox)findViewById(R.id.cbLegs1);
        final CheckBox cbLegs2 = (CheckBox)findViewById(R.id.cbLegs2);
        final CheckBox cbAntennae1 = (CheckBox)findViewById(R.id.cbAntennae1);
        final CheckBox cbAntennae2 = (CheckBox)findViewById(R.id.cbAntennae2);
        final CheckBox cbTeeth1 = (CheckBox)findViewById(R.id.cbTeeth1);
        final CheckBox cbTeeth2 = (CheckBox)findViewById(R.id.cbTeeth2);
        final CheckBox cbEyes1 = (CheckBox)findViewById(R.id.cbEyes1);
        final CheckBox cbEyes2 = (CheckBox)findViewById(R.id.cbEyes2);

        View rollButton = findViewById(R.id.realRollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (!player1.isDone() && !player2.isDone()) { //check to see if game is over after every roll
                    int roll = (int) roll(); //roll the die
                    if (count[0] % 2 == 0) { //determine who's turn it is
                        switch (roll) {
                            case 1:
                                if (!player1.hasBody) {
                                    player1.hasBody = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou now have a body!", Toast.LENGTH_SHORT).show();
                                    cbBody1.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 2:
                                if (player1.hasBody && !player1.hasHead) {
                                    player1.hasHead = true;
                                    player1.hasBoth = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou now have a head!", Toast.LENGTH_SHORT).show();
                                    cbHead1.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 3:
                                if (player1.hasBoth && !player1.antennae) {
                                    player1.antennae = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou now have an antennae!", Toast.LENGTH_SHORT).show();
                                    cbAntennae1.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 4:
                                if (player1.hasBoth && player1.eyes != 2) {
                                    player1.eyes++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou just added an eye! You now have: " + player1.eyes +" eye(s).", Toast.LENGTH_SHORT).show();
                                    if (player1.eyes == 2) {
                                        cbEyes1.toggle();
                                    }
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 5:
                                if (player1.hasBoth && !player1.teeth) {
                                    player1.teeth = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou just got teeth!", Toast.LENGTH_SHORT).show();
                                    cbTeeth1.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 6:
                                if (player1.hasBoth && player1.legs != 6) {
                                    player1.legs++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nYou just added a leg! You now have: " + player1.legs + " leg(s) out of 6.", Toast.LENGTH_SHORT).show();
                                    if (player1.legs == 6){
                                        cbLegs1.toggle();
                                    }
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 1: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                        }
                    } else {
                        switch (roll) {
                            case 1:
                                if (!player2.hasBody) {
                                    player2.hasBody = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou now have a body!", Toast.LENGTH_SHORT).show();
                                    cbBody2.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 2:
                                if (player2.hasBody && !player2.hasHead) {
                                    player2.hasHead = true;
                                    player2.hasBoth = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou now have a head!", Toast.LENGTH_SHORT).show();
                                    cbHead2.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 3:
                                if (player2.hasBoth && !player2.antennae) {
                                    player2.antennae = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou now have an antennae!", Toast.LENGTH_SHORT).show();
                                    cbAntennae2.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 4:
                                if (player2.hasBoth && player2.eyes != 2) {
                                    player2.eyes++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou just added an eye! \nYou now have: " + player2.eyes +" eye(s).", Toast.LENGTH_SHORT).show();
                                    if (player2.eyes == 2) {
                                        cbEyes2.toggle();
                                    }
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 5:
                                if (player2.hasBoth && !player2.teeth) {
                                    player2.teeth = true;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou just added teeth!", Toast.LENGTH_SHORT).show();
                                    cbTeeth2.toggle();
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            case 6:
                                if (player2.hasBoth && player2.legs != 6) {
                                    player2.legs++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nYou just added a leg!\nYou now have: " + player2.legs + " leg(s) out of 6.", Toast.LENGTH_SHORT).show();
                                    if (player2.legs == 6){
                                        cbLegs2.toggle();
                                    }
                                    break;
                                } else {
                                    count[0]++;
                                    Toast.makeText(Cootie_GamePlay.this, "Player 2: You rolled a " + roll + ".\nSwitching turns", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                        }
                    }
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Cootie_GamePlay.this);
                if (player1.isDone() || player2.isDone()) { //check to see if game is over after current roll
                    if (player1.isDone()) {
                        alertDialogBuilder
                                .setMessage("Player 1 won!\nRestart Game?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        resetGame();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        alertDialogBuilder
                                .setMessage("Player 2" +
                                        " won!\nRestart Game?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        resetGame();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            }
        });
    }


    private double roll(){

        return((6.0 * Math.random()) + 1);
    }

    private void resetGame() { //make a new game
        Intent newGameActivity = new Intent(this,Cootie_GamePlay.class);
        finish();
        startActivity(newGameActivity);
    }

    private class Cootie {
        boolean hasBody;
        boolean hasHead;
        boolean hasBoth;
        boolean antennae;
        boolean teeth;
        int legs;
        int eyes;


        Cootie() {
            hasBody = false;
            hasHead = false;
            hasBoth = false;
            antennae = false;
            teeth = false;
            legs = 0;
            eyes = 0;
        }

        boolean isDone() { //check to see if the bug is completely built
            boolean isDone = false;
            if (hasBoth && antennae && teeth && legs == 6 && eyes == 2){
                isDone = true;
            }
            return isDone;
        }

    }
}
