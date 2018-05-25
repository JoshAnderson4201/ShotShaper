package com.jdapps.shotshaper2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-1560133139405491~3554169263");
        AdView ad = (AdView)findViewById(R.id.adView);
        AdRequest re = new AdRequest.Builder().build();
        re.isTestDevice(this);
        ad.loadAd(re);
    }

    public void goToOptions(View v)
    {
        Intent goToOptions = new Intent(this, SelectOptions.class);
        startActivity(goToOptions);
    }

    public void editClubs(View v)
    {
        Intent goToEdit = new Intent(this, EditClubs.class);
        startActivity(goToEdit);
    }
}
