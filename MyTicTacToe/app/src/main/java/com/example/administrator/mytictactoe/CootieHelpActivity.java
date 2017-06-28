package com.example.administrator.mytictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CootieHelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cootie_help);

        //wire button
        View backButton = findViewById(R.id.help_back);
        backButton.setOnClickListener(this);
    }
    public void onClick(View v) { //go back to main screen
        Intent mainScreenActivity = new Intent(this,CootieActivity.class);
        finish();
        startActivity(mainScreenActivity);
    }

}
