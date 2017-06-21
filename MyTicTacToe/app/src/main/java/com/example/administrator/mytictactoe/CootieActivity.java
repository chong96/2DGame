package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CootieActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cootie);

        View startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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