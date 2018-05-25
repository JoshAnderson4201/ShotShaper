package com.jdapps.shotshaper2;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;

public class EditClubs extends AppCompatActivity
{

    DatabaseHandler db = new DatabaseHandler(this);
    RadioGroup woodList;
    RadioGroup ironList;
    RadioGroup wedgeList;
    private InterstitialAd ad;
    boolean adShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clubs);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {getResources().getColor(R.color.colorAccent) }
        );

        Random r = new Random();
        int index = r.nextInt(3);

        if(index == 1)
        {
            ad = new InterstitialAd(this);
            //Test Ad
            ad.setAdUnitId("ca-app-pub-1560133139405491/7511873373");
            AdRequest re = new AdRequest.Builder().build();
            re.isTestDevice(this);
            ad.loadAd(re);
            ad.setAdListener(new AdListener(){
                public void onAdLoaded()
                {
                    ad.show();
                }
            });
            adShown = true;
        }





        woodList = findViewById(R.id.woodList);
        ArrayList<String> woodNames = db.getWoodNames();
        for(int i = 0; i < woodNames.size(); i++)
        {
            RadioButton club = new RadioButton(this);
            club.setText(woodNames.get(i));
            int color = getResources().getColor(R.color.colorAccent);
            club.setTextColor(color);
            club.setButtonTintList(colorStateList);
            woodList.addView(club);
        }

        ironList = findViewById(R.id.ironList);
        ArrayList<String> ironNames = db.getIronNames();
        for(int i = 0; i < ironNames.size(); i++)
        {
            RadioButton club = new RadioButton(this);
            club.setText(ironNames.get(i));
            int color = getResources().getColor(R.color.colorAccent);
            club.setTextColor(color);
            club.setButtonTintList(colorStateList);
            ironList.addView(club);
        }

        wedgeList = findViewById(R.id.wedgeList);
        ArrayList<String> wedgeNames = db.getWedgeNames();
        for(int i = 0; i < wedgeNames.size(); i++)
        {
            RadioButton club = new RadioButton(this);
            club.setText(wedgeNames.get(i));
            int color = getResources().getColor(R.color.colorAccent);
            club.setTextColor(color);
            club.setButtonTintList(colorStateList);
            wedgeList.addView(club);
        }
    }

    public void addWood(View v)
    {
        EditText nameET = findViewById(R.id.woodName);
        String name = nameET.getText().toString();
        db.getWritableDatabase();
        db.insertClub(name, "Wood");
        finish();
        startActivity(getIntent());
    }

    public void addIron(View v)
    {
        EditText nameET = findViewById(R.id.ironName);
        String name = nameET.getText().toString();
        db.getWritableDatabase();
        db.insertClub(name, "Iron");
        finish();
        startActivity(getIntent());
    }

    public void addWedge(View v)
    {
        EditText nameET = findViewById(R.id.wedgeName);
        String name = nameET.getText().toString();
        db.getWritableDatabase();
        db.insertClub(name, "Wedge");
        finish();
        startActivity(getIntent());
    }



    public void deleteClub(View v) {
        int woodButtonId = woodList.getCheckedRadioButtonId();
        if(woodButtonId != -1) {
            View radioButton = woodList.findViewById(woodButtonId);
            int index = woodList.indexOfChild(radioButton);
            RadioButton r = (RadioButton) woodList.getChildAt(index);
            String clubName = r.getText().toString();
            db.getWritableDatabase();
            db.deleteClubByName(clubName);
        }
        int ironButtonId = ironList.getCheckedRadioButtonId();
        if(ironButtonId != -1) {
            View radioButton = ironList.findViewById(ironButtonId);
            int index = ironList.indexOfChild(radioButton);
            RadioButton r = (RadioButton) ironList.getChildAt(index);
            String clubName = r.getText().toString();
            db.getWritableDatabase();
            db.deleteClubByName(clubName);
        }
        int wedgeButtonId = wedgeList.getCheckedRadioButtonId();
        if(wedgeButtonId != -1) {
            View radioButton = wedgeList.findViewById(wedgeButtonId);
            int index = wedgeList.indexOfChild(radioButton);
            RadioButton r = (RadioButton) wedgeList.getChildAt(index);
            String clubName = r.getText().toString();
            db.getWritableDatabase();
            db.deleteClubByName(clubName);
        }
        finish();
        startActivity(getIntent());
    }

    public void exit(View v)
    {
        finish();
    }

    public void info(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Add or delete your clubs. Adding clubs to the correct section will allow you train that specific section later on.");
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
