package com.jdapps.shotshaper2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SelectOptions extends AppCompatActivity
{
    public static int styleID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_options);

        MobileAds.initialize(this, "ca-app-pub-1560133139405491~3554169263");
        AdView ad = (AdView)findViewById(R.id.adView);
        AdRequest re = new AdRequest.Builder().build();
        re.isTestDevice(this);
        ad.loadAd(re);
    }

    public void startTraining(View v)
    {
        RadioGroup styleGroup = findViewById(R.id.styleGroup);
        int styleButtonID = styleGroup.getCheckedRadioButtonId();
        View styleButton = styleGroup.findViewById(styleButtonID);
        styleID = styleGroup.indexOfChild(styleButton);

        /*RadioGroup nextGroup = findViewById(R.id.nextShotGroup);
        int nextButtonID = nextGroup.getCheckedRadioButtonId();
        View nextButton = nextGroup.findViewById(nextButtonID);
        int nextIndex = nextGroup.indexOfChild(nextButton);*/

        RadioGroup clubGroup = findViewById(R.id.clubGroup);
        int count = clubGroup.getChildCount();
        boolean[] clubIDs = new boolean[3];
        for(int i = 0; i < count; i++)
        {
            View box = clubGroup.getChildAt(i);
            CheckBox checkBox = (CheckBox) box;
            if (checkBox.isChecked())
            {
                clubIDs[i] = true;
            }
            else
            {
                clubIDs[i] = false;
            }
        }

        for(int i =0; i < 3; i++)
        {
            Log.i("Club IDs", Boolean.toString(clubIDs[i]));
        }
        Intent startTraining = new Intent(this, Trainer.class);
        startTraining.putExtra("Clubs", clubIDs);
        startActivity(startTraining);
    }

    public void exit(View v)
    {
        finish();
    }

    public void info(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Selecting the clubs style will generate shots using specific clubs. The yards style will generate shots for specific yardages. Yardages are capped for certain club types.");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Got It",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
