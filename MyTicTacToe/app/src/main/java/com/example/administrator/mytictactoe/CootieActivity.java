package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CootieActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cootie);

        //wire buttons
        View startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) { //make menu
        getMenuInflater().inflate(R.menu.menu_cootie,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){ //do selected menu option
            case R.id.action_about:
                Intent helpActivity = new Intent(this,CootieHelpActivity.class);
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
        switch (v.getId()) { //do selected button

            case R.id.start_button:
                Intent startCootie = new Intent(this,Cootie_GamePlay.class);
                finish();
                startActivity(startCootie);
                break;

            case R.id.exit_button:
                Intent mainActivity = new Intent(this,MainActivity.class);
                finish();
                startActivity(mainActivity);
                break;
        }
    }
}
