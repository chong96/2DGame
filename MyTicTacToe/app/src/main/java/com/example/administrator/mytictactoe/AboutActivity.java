package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Wire button
        View backButton = findViewById(R.id.about_back);
        backButton.setOnClickListener(this);
    }
    public void onClick(View v) { //go back to main menu
        Intent mainScreenActivity = new Intent(this,MainActivity.class);
        finish();
        startActivity(mainScreenActivity);
    }
}
