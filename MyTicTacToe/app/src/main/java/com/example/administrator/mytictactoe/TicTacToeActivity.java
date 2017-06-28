package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        //wire buttons
        View startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) { //make menu
        getMenuInflater().inflate(R.menu.menu_tictactoe,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //do selected menu option
        switch (item.getItemId()){
            case R.id.action_about:
                Intent helpActivity = new Intent(this,TicTacToeHelpActivity.class);
                startActivity(helpActivity);
                break;

            case R.id.action_app:
                Toast.makeText(this, "App icon clicked!", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) { //do selected button option

            case R.id.start_button:
                Intent gameActivity = new Intent(this,Real_GamePlay.class);
                finish();
                startActivity(gameActivity);
                break;

            case R.id.exit_button:
                Intent mainActivity = new Intent(this,MainActivity.class);
                finish();
                startActivity(mainActivity);
                break;
        }
    }
}
