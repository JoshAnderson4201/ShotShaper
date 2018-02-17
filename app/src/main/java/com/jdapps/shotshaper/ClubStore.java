package com.jdapps.shotshaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by retre on 2/7/2018.
 */

public class ClubStore
{
    Context context;
    public ArrayList<String> woodArray;
    private ArrayList<String> ironArray;
    private ArrayList<String> wedgeArray;

    public ClubStore()
    {
        woodArray = new ArrayList<String>(0);
        ironArray = new ArrayList<String>(0);
        wedgeArray = new ArrayList<String>(0);
    }

    //clubType values:
    //      1 = wood
    //      2 = iron
    //      3 = wedge
    public void addClub(String clubName, int clubType)
    {
        switch(clubType)
        {
            case 1: woodArray.add(clubName);
                    break;
            case 2: ironArray.add(clubName);
                break;
            case 3: wedgeArray.add(clubName);
                break;
        }
    }

    // Stores clubs from arrays in shared prefs
    public void storeClubs(Context context)
    {
        this.context = context;

        //Store woods
        StringBuilder woodsSB = new StringBuilder();
        for (int i = 0; i < woodArray.size(); i++)
        {
            woodsSB.append(woodArray.get(i));
            woodsSB.append(",");
        }

        //Store irons
        StringBuilder ironsSB = new StringBuilder();
        for (int i = 0; i < ironArray.size(); i++)
        {
            ironsSB.append(ironArray.get(i));
            ironsSB.append(",");
        }

        //Store wedges
        StringBuilder wedgesSB = new StringBuilder();
        for (int i = 0; i < wedgeArray.size(); i++)
        {
            wedgesSB.append(wedgeArray.get(i));
            wedgesSB.append(",");
        }

        // Send all to shared prefs
        SharedPreferences settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("woods", woodsSB.toString());
        editor.putString("irons", ironsSB.toString());
        editor.putString("wedges", wedgesSB.toString());
        editor.apply();
    }

    // Loads clubs from shared prefs and adds them
    public void loadClubs(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String woodsSP = settings.getString("woods", "");
        String ironsSP = settings.getString("irons", "");
        String wedgesSP = settings.getString("wedges", "");
        String[] woodsStrings = woodsSP.split(",");
        String[] ironsStrings = ironsSP.split(",");
        String[] wedgesStrings = wedgesSP.split(",");
        for (int i =0; i < woodsStrings.length; i++)
        {
            woodArray.add(woodsStrings[i]);
        }
        for (int i =0; i < ironsStrings.length; i++)
        {
            ironArray.add(ironsStrings[i]);
        }
        for (int i =0; i < wedgesStrings.length; i++)
        {
            wedgeArray.add(wedgesStrings[i]);
        }
    }


    public void displayClubs(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String woodsString = settings.getString("woods", "");
        String[] itemsWoods = woodsString.split(",");
        ArrayList<String> loadedWoodArray = new ArrayList<String>();
        for (int i =0; i < itemsWoods.length; i++)
        {
            loadedWoodArray.add(itemsWoods[i]);
        }

        for (int i = 0; i < loadedWoodArray.size(); i++)
        {
            Log.d("clubNameWo", loadedWoodArray.get(i));
        }

        String ironsString = settings.getString("irons", "");
        String[] itemsIrons = ironsString.split(",");
        ArrayList<String> loadedIronArray = new ArrayList<String>();
        for (int i =0; i < itemsIrons.length; i++)
        {
            loadedIronArray.add(itemsIrons[i]);
        }

        for (int i = 0; i < loadedIronArray.size(); i++)
        {
            Log.d("clubNameI", loadedIronArray.get(i));
        }

        String wedgesString = settings.getString("wedges", "");
        String[] itemsWedges = wedgesString.split(",");
        ArrayList<String> loadedWedgeArray = new ArrayList<String>();
        for (int i =0; i < itemsWedges.length; i++)
        {
            loadedWedgeArray.add(itemsWedges[i]);
        }

        for (int i = 0; i < loadedWedgeArray.size(); i++)
        {
            Log.d("clubNameWe", loadedWedgeArray.get(i));
        }
    }

   public String randomClub()
   {
       //displayClubs(this.context);
       double randomNum = new Random().nextFloat() * 3;
       if (randomNum <= .45)
       {
           int randomWood = new Random().nextInt(woodArray.size());
           return woodArray.get(randomWood);
       }
       else if (randomNum >= 2.55)
       {
           int randomWedge = new Random().nextInt(wedgeArray.size());
           return wedgeArray.get(randomWedge);
       }
       else
       {
           int randomIron = new Random().nextInt(ironArray.size());
           return ironArray.get(randomIron);
       }
   }
}

