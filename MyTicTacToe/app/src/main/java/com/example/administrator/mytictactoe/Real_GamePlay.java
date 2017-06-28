package com.example.administrator.mytictactoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Real_GamePlay extends Activity implements View.OnClickListener{

    private int counter;
    private int position;
    private boolean gameOver;
    private final Context gameContext = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real__game_play);

        //wire buttons/grid
        View backButton  = findViewById(R.id.back_Button);
        backButton.setOnClickListener(this);
        View restartButton = findViewById(R.id.restart_Button);
        restartButton.setOnClickListener(this);
        View quitButton = findViewById(R.id.quit_Button);
        quitButton.setOnClickListener(this);
        View user11Button = findViewById(R.id.button_1_1);
        user11Button.setOnClickListener(this);
        View user12Button = findViewById(R.id.button_1_2);
        user12Button.setOnClickListener(this);
        View user13Button = findViewById(R.id.button_1_3);
        user13Button.setOnClickListener(this);
        View user21Button = findViewById(R.id.button_2_1);
        user21Button.setOnClickListener(this);
        View user22Button = findViewById(R.id.button_2_2);
        user22Button.setOnClickListener(this);
        View user23Button = findViewById(R.id.button_2_3);
        user23Button.setOnClickListener(this);
        View user31Button = findViewById(R.id.button_3_1);
        user31Button.setOnClickListener(this);
        View user32Button = findViewById(R.id.button_3_2);
        user32Button.setOnClickListener(this);
        View user33Button = findViewById(R.id.button_3_3);
        user33Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){ //mark boxes with x's or o's

            case R.id.button_1_1:
                Button button_1_1 = (Button) v;
                setXorO(button_1_1);
                position = 1;
                break;

            case R.id.button_1_2:
                Button button_1_2 = (Button) v;
                setXorO(button_1_2);
                position = 2;
                break;

            case R.id.button_1_3:
                Button button_1_3 = (Button) v;
                setXorO(button_1_3);
                position = 3;
                break;

            case R.id.button_2_1:
                Button button_2_1 = (Button) v;
                setXorO(button_2_1);
                position = 4;
                break;

            case R.id.button_2_2:
                Button button_2_2 = (Button) v;
                setXorO(button_2_2);
                position = 5;
                break;

            case R.id.button_2_3:
                Button button_2_3 = (Button) v;
                setXorO(button_2_3);
                position = 6;
                break;

            case R.id.button_3_1:
                Button button_3_1 = (Button) v;
                setXorO(button_3_1);
                position = 7;
                break;

            case R.id.button_3_2:
                Button button_3_2 = (Button) v;
                setXorO(button_3_2);
                position = 8;
                break;

            case R.id.button_3_3:
                Button button_3_3 = (Button) v;
                setXorO(button_3_3);
                position = 9;
                break;

            //do selected button options
            case R.id.back_Button:
                Intent mainScreenActivity = new Intent(this,MainActivity.class);
                finish();
                startActivity(mainScreenActivity);
            break;

            case R.id.quit_Button:
                finish();
                break;

            case R.id.restart_Button:
                resetGame();
                break;

        }

        if (counter >= 5 && isGameOver(position) && !gameOver) { //check conditions for if the game is over

            gameOver = true;

            String player2 = ((TextView)findViewById(R.id.player_id2)).getText().toString();
            String player1 = ((TextView)findViewById(R.id.player_id1)).getText().toString();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameContext);

            alertDialogBuilder.setTitle("Game Over!");

            if (counter % 2 == 0) {
                alertDialogBuilder
                        .setMessage(player2 + " won the game!  \n \n \t Restart Game?")
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
            } else {
                alertDialogBuilder
                        .setMessage(player1 + " won the game!  \n \n \t Restart Game?")
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
            }
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }

        else if (counter == 9 && !gameOver) {
            gameOver = true;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameContext);

            alertDialogBuilder.setTitle("Game Over!");
            alertDialogBuilder
                    .setMessage("Game resulted in a draw!  \n Restart Game?")
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

    private void setPlayerName(final TextView v) { //change the name of the players
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Title");
        alertDialogBuilder.setMessage("Enter a Name");

        final EditText name = new EditText(this);
        alertDialogBuilder.setView(name);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String value = name.getText().toString();
                if (value != null && value.length() > 0) {
                    v.setText(value);
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void resetGame() { //clear the board
        counter = 0;
        gameOver = false;
        ((Button)findViewById(R.id.button_1_1)).setText("");
        ((Button)findViewById(R.id.button_1_2)).setText("");
        ((Button)findViewById(R.id.button_1_3)).setText("");
        ((Button)findViewById(R.id.button_2_1)).setText("");
        ((Button)findViewById(R.id.button_2_2)).setText("");
        ((Button)findViewById(R.id.button_2_3)).setText("");
        ((Button)findViewById(R.id.button_3_1)).setText("");
        ((Button)findViewById(R.id.button_3_2)).setText("");
        ((Button)findViewById(R.id.button_3_3)).setText("");
    }


    private void setXorO(Button b) { //set the boxes
        if (b.getText().toString().length() == 0){
            if (counter % 2 == 0) {
                b.setText("X");
            } else {
                b.setText("O");
            }
            counter++;
        }
    }

    private boolean isGameOver(int position){ //check all conditions for winning

        String data11 = ((Button)findViewById(R.id.button_1_1)).getText().toString();
        String data12 = ((Button)findViewById(R.id.button_1_2)).getText().toString();
        String data13 = ((Button)findViewById(R.id.button_1_3)).getText().toString();
        String data21 = ((Button)findViewById(R.id.button_2_1)).getText().toString();
        String data22 = ((Button)findViewById(R.id.button_2_2)).getText().toString();
        String data23 = ((Button)findViewById(R.id.button_2_3)).getText().toString();
        String data31 = ((Button)findViewById(R.id.button_3_1)).getText().toString();
        String data32 = ((Button)findViewById(R.id.button_3_2)).getText().toString();
        String data33 = ((Button)findViewById(R.id.button_3_3)).getText().toString();

        switch (position){
            case 1:
                if (data11.equals(data12) ? data11.equals(data13) : false) {
                    return true;
                }
                if (data11.equals(data21) ? data11.equals(data31) : false) {
                    return true;
                }
                if (data11.equals(data22) ? data11.equals(data33) : false) {
                    return true;
                }
                break;

            case 2:
                if (data12.equals(data11) ? data12.equals(data13) : false) {
                    return true;
                }
                if (data12.equals(data22) ? data12.equals(data32) : false) {
                    return true;
                }
                break;

            case 3:
                if (data13.equals(data12) ? data13.equals(data11) : false) {
                    return true;
                }
                if (data13.equals(data23) ? data13.equals(data33) : false) {
                    return true;
                }
                if (data13.equals(data22) ? data13.equals(data31) : false) {
                    return true;
                }
                break;

            case 4:
                if (data21.equals(data11) ? data21.equals(data31) : false) {
                    return true;
                }
                if (data21.equals(data22) ? data21.equals(data23) : false) {
                    return true;
                }
                break;

            case 5:
                if (data22.equals(data21) ? data22.equals(data23) : false) {
                    return true;
                }
                if (data22.equals(data12) ? data22.equals(data32) : false) {
                    return true;
                }
                if (data22.equals(data11) ? data22.equals(data33) : false) {
                    return true;
                }
                if (data22.equals(data31) ? data22.equals(data13) : false) {
                    return true;
                }
                break;

            case 6:
                if (data23.equals(data13) ? data23.equals(data33) : false) {
                    return true;
                }
                if (data23.equals(data22) ? data23.equals(data21) : false) {
                    return true;
                }
                break;

            case 7:
                if (data31.equals(data21) ? data31.equals(data11) : false) {
                    return true;
                }
                if (data31.equals(data32) ? data31.equals(data33) : false) {
                    return true;
                }
                if (data31.equals(data22) ? data31.equals(data13) : false) {
                    return true;
                }
                break;

            case 8:
                if (data32.equals(data31) ? data32.equals(data33) : false) {
                    return true;
                }
                if (data32.equals(data22) ? data32.equals(data12) : false) {
                    return true;
                }
                break;

            case 9:
                if (data33.equals(data32) ? data33.equals(data31) : false) {
                    return true;
                }
                if (data33.equals(data23) ? data33.equals(data13) : false) {
                    return true;
                }
                if (data33.equals(data22) ? data33.equals(data11) : false) {
                    return true;
                }
                break;
        }
        return false;
    }
    class inputclickListener implements android.content.DialogInterface.OnClickListener {

        TextView textView;

        public inputclickListener(TextView textView){
            this.textView = textView;
        }


        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub

        }

    }


}
