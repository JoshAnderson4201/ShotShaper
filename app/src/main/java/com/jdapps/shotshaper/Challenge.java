package com.jdapps.shotshaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Challenge extends AppCompatActivity
{
    ClubStore clubStore = new ClubStore();
    int minInteger = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        displayStreak(0);
        displayClubName();
    }

    public void increaseScore(View view)
    {
        minInteger += 1;
        displayStreak(minInteger);
    }

    public void displayStreak(int number)
    {
        TextView displayScore = findViewById(R.id.streakScore);
        displayScore.setText(Integer.toString(number));
    }

    public void displayClubName()
    {
        clubStore.loadClubs(this);
        TextView displayClubName = findViewById(R.id.clubName);
        displayClubName.setText(clubStore.randomClub());
    }
}
