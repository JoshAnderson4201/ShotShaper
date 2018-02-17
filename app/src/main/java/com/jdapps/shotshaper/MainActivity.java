package com.jdapps.shotshaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    ImageView challenge;

    public void init()
    {
        //Link to challenge image
        challenge = findViewById(R.id.challenge);
        challenge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent openChallenge = new Intent(MainActivity.this, Challenge.class);
                startActivity(openChallenge);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if(isFirstRun)
        {
            startActivity(new Intent(this, LaunchScreen.class));
        }
        setContentView(R.layout.activity_main);
        init();
    }
}
