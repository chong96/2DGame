package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TicTacToeHelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_help);

        View backButton = findViewById(R.id.help_back);
        backButton.setOnClickListener(this);
    }
    public void onClick(View v) {
        Intent mainScreenActivity = new Intent(this,TicTacToeActivity.class);
        finish();
        startActivity(mainScreenActivity);
    }

}
