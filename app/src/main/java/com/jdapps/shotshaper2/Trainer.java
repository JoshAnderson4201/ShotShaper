package com.jdapps.shotshaper2;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class Trainer extends AppCompatActivity implements TextToSpeech.OnInitListener
{

    private TextToSpeech tts;
    int styleID = SelectOptions.styleID;
    int nextID = -1;
    boolean[] clubIDs = new boolean[3];
    TextView shotType, shotCY;
    int count = 0;
    String[] woodShotShapes = new String[]{};
    String[] ironShotShapes = new String[]{};
    String[] wedgeShotShapes = new String[]{};
    String yardageClubString = "";
    String shapeString = "";
    boolean play = true;

    DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        loadShotShapes(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            nextID = extras.getInt("Next");
            clubIDs = extras.getBooleanArray("Clubs");
        }
        Log.i("Style", "" + styleID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        shotType = findViewById(R.id.shotType);
        shotCY = findViewById(R.id.shotCY);

        MobileAds.initialize(this, "ca-app-pub-1560133139405491~3554169263");
        AdView ad = (AdView)findViewById(R.id.adView);
        AdRequest re = new AdRequest.Builder().build();
        re.isTestDevice(this);
        ad.loadAd(re);

        tts = new TextToSpeech(this, this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        cycle();
    }

    public void cycle()
    {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(!play)
                {
                    handler.removeCallbacks(this);
                }
                else
                {
                    filterShots(clubIDs, styleID);
                    shotType.setText(shapeString);
                    shotCY.setText(yardageClubString);
                    speakOut();
                    handler.postDelayed(this, 60000);
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onInit(int status)
    {
        if(status == TextToSpeech.SUCCESS)
        {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "This Language is not supported");
            }
            else
            {
                speakOut();
            }
        }
    }

    public void speakOut()
    {
        tts.speak(yardageClubString + shapeString, TextToSpeech.QUEUE_FLUSH, null);
    }


    public void loadShotShapes(Context context)
    {
        //Split the shapes file and add them all to appropriate arrays
        BufferedReader reader;
        try
        {
            final InputStream file = context.getAssets().open("shotshapes.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            count = 0;
            while(line != null)
            {
                if(count == 0)
                {
                    woodShotShapes = line.split(",");
                }
                else if(count == 1)
                {
                    ironShotShapes = line.split(",");
                }
                else if(count == 2)
                {
                    wedgeShotShapes = line.split(",");
                }
                count++;
                line = reader.readLine();
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

    }


    public void generateYardsShotShape(int type, int yards)
    {
        if(type == 1 && yards < 50)
        {
            Random r = new Random();
            int index = r.nextInt((6-3) + 1) + 3;
            shapeString = wedgeShotShapes[index];
        }
        else if(type == 1 && yards > 50)
        {
            Random r = new Random();
            int index = r.nextInt(3);
            shapeString = wedgeShotShapes[index];
        }
        else if(type == 2)
        {
            Random r = new Random();
            int index = r.nextInt(ironShotShapes.length-1);
            shapeString = ironShotShapes[index];
        }
        else
        {
            Random r = new Random();
            int index = r.nextInt(woodShotShapes.length);
            shapeString = woodShotShapes[index];
        }

    }

    public void generateClubShotShape(int type)
    {
        Random r = new Random();
        if(type == 1)
        {
            int index = r.nextInt((6-3) + 1) + 3;
            shapeString = wedgeShotShapes[index];
        }
        else if(type == 2)
        {
            int index = r.nextInt(ironShotShapes.length);
            shapeString = ironShotShapes[index];
        }
        else
        {
            int index = r.nextInt(woodShotShapes.length);
            shapeString = woodShotShapes[index];
        }
    }

    public void generateWedgeShot(int styleID)
    {
        Random r = new Random();
        // Yards mode selected
        if(styleID == 1)
        {
            int yards = r.nextInt((130) + 1);
            yards = (yards + 4) / 5 * 5;
            yardageClubString = Integer.toString(yards);
            generateYardsShotShape(1, yards);
        }
        //Club mode selected
        else if(styleID == 0)
        {
            ArrayList<String> names = db.getWedgeNames();
            int index = r.nextInt(names.size());
            String name = names.get(index);
            yardageClubString = name;
            generateClubShotShape(1);
        }
    }

    public void generateIronShot(int styleID)
    {
        Random r = new Random();
        // Yards mode selected
        if(styleID == 1)
        {
            int yards = r.nextInt((189 - 131) + 1) + 131;
            yards = (yards + 4) / 5 * 5;
            yardageClubString = Integer.toString(yards);
            generateYardsShotShape(2, yards);
        }
        //Club mode selected
        else if(styleID == 0)
        {
            ArrayList<String> names = db.getIronNames();
            int index = r.nextInt(names.size());
            String name = names.get(index);
            yardageClubString = name;
            generateClubShotShape(2);
        }
    }

    public void generateWoodShot(int styleID)
    {
        Random r = new Random();
        // Yards mode selected
        if(styleID == 1)
        {
            int yards = r.nextInt((275 - 190) + 1) + 190;
            yards = (yards + 4) / 5 * 5;
            yardageClubString = Integer.toString(yards);
            generateYardsShotShape(3, yards);
        }
        //Club mode selected
        else if(styleID == 0)
        {
            ArrayList<String> names = db.getWoodNames();
            int index = r.nextInt(names.size());
            String name = names.get(index);
            yardageClubString = name;
            generateClubShotShape(3);
        }
    }



    public void filterShots(boolean[] clubIDs, int styleID)
    {
        // Wedges, nothing else
        if (clubIDs[0] && !clubIDs[1] && !clubIDs[2])
        {
            generateWedgeShot(styleID);
        }
        //Irons, nothing else
        else if (!clubIDs[0] && clubIDs[1] && !clubIDs[2])
        {
            generateIronShot(styleID);
        }
        // Woods, nothing else
        else if (!clubIDs[0] && !clubIDs[1] && clubIDs[2])
        {
            generateWoodShot(styleID);
        }
        // Irons and Woods
        else if (!clubIDs[0] && clubIDs[1] && clubIDs[2])
        {
            Random r = new Random();
            int type = r.nextInt((2 - 1) + 1) + 1;
            if(type == 1)
            {
                generateIronShot(styleID);
            }
            else
            {
                generateWoodShot(styleID);
            }
        }
        // Wedges and Woods
        else if (clubIDs[0] && !clubIDs[1] && clubIDs[2])
        {
            Random r = new Random();
            int type = r.nextInt((2 - 1) + 1) + 1;
            if(type == 1)
            {
                generateWedgeShot(styleID);
            }
            else
            {
                generateWoodShot(styleID);
            }
        }
        // Wedges and Irons
        else if (clubIDs[0] && clubIDs[1] && !clubIDs[2])
        {
            Random r = new Random();
            int type = r.nextInt((2 - 1) + 1) + 1;
            if(type == 1)
            {
                generateIronShot(styleID);
            }
            else
            {
                generateWedgeShot(styleID);
            }
        }
        // Everything
        else
        {
            Random r = new Random();
            int type = r.nextInt((3 - 1) + 1) + 1;
            if(type == 1)
            {
                generateIronShot(styleID);
            }
            else if(type == 2)
            {
                generateWoodShot(styleID);
            }
            else
            {
                generateWedgeShot(styleID);
            }
        }
    }

    public void nextShot(View v)
    {
        filterShots(clubIDs, styleID);
        shotType.setText(shapeString);
        shotCY.setText(yardageClubString);
        speakOut();
    }

    public void playPause(View v)
    {
        if(play)
            play = false;
        else
        {
            play = true;
            cycle();
        }

    }

    @Override
    protected void onDestroy()
    {
        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void exit(View v)
    {
        finish();
    }

    public void info(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Shots are automatically generated every minute. You can either leave the trainer in auto mode, or pause the trainer" +
                " and then use the skip button to generate new shots. If you wish to return to auto mode, re-press the play/pause button.");
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
