package com.jdapps.shotshaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LaunchScreen extends AppCompatActivity
{
    ClubStore clubStore = new ClubStore();

    EditText woodInput;
    EditText ironInput;
    EditText wedgeInput;

    Button addWood;
    Button addIron;
    Button addWedge;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        setup();
    }

    private void setup()
    {
        woodInput = findViewById(R.id.inputWood);
        addWood = findViewById(R.id.addWoodButton);
        addWood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String woodInputString = woodInput.getText().toString();
                clubStore.addClub(woodInputString, 1);
                woodInput.setText("");
            }
        });

        ironInput = findViewById(R.id.inputIron);
        addIron = findViewById(R.id.addIronButton);
        addIron.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String ironInputString = ironInput.getText().toString();
                clubStore.addClub(ironInputString, 2);
                ironInput.setText("");
            }
        });

        wedgeInput = findViewById(R.id.inputWedge);
        addWedge = findViewById(R.id.addWedgeButton);
        addWedge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String wedgeInputString =  wedgeInput.getText().toString();
                clubStore.addClub(wedgeInputString, 3);
                wedgeInput.setText("");
            }
        });

        finish = findViewById(R.id.finishButton);
        finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clubStore.storeClubs(LaunchScreen.this);
                clubStore.displayClubs(LaunchScreen.this);
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
                Intent openMain = new Intent(LaunchScreen.this, MainActivity.class);
                startActivity(openMain);
                finish();
            }
        });
    }
}
