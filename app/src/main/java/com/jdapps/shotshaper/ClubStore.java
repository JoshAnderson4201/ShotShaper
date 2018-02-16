package com.jdapps.shotshaper;

import java.util.ArrayList;

/**
 * Created by retre on 2/7/2018.
 */

public class ClubStore
{
    private ArrayList<String> woodArray;
    private ArrayList<String> ironArray;
    private ArrayList<String> wedgeArray;

    public ClubStore()
    {
        woodArray = new ArrayList<String>(0);
        ironArray = new ArrayList<String>(0);
        wedgeArray = new ArrayList<String>(0);
    }

    public void AddWood(String woodName)
    {
        woodArray.add(woodName);
    }

    public void AddIron(String ironName)
    {
        ironArray.add(ironName);
    }

    public void AddWedge(String wedgeName)
    {
        wedgeArray.add(wedgeName);
    }

    //clubType values:
    //      1 = wood
    //      2 = iron
    //      3 = wedge
    public void AddClub(String clubName, int clubType)
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
}
