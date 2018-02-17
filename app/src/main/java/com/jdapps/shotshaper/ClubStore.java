package com.jdapps.shotshaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

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

    public void storeClubs(Context context)
    {
        this.context = context;
        StringBuilder stringBuilder = new StringBuilder();
        for (int w = 0; w < woodArray.size(); w++)
        {
            stringBuilder.append(woodArray.get(w));
            stringBuilder.append(",");
        }
        SharedPreferences settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("woods", stringBuilder.toString());
        editor.apply();
    }

    public void loadClubs(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String woodsString = settings.getString("woods", "");
        String[] spWoods = woodsString.split(",");
        for (int i =0; i < spWoods.length; i++)
        {
            woodArray.add(spWoods[i]);
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
            Log.d("clubName", loadedWoodArray.get(i));
        }
    }

   public String randomClub()
   {
       return woodArray.get(0);
   }
}

