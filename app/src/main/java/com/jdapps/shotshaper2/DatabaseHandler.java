package com.jdapps.shotshaper2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by retre on 4/5/2018.
 */


public class DatabaseHandler extends SQLiteOpenHelper
{
    public DatabaseHandler(Context context)
    {
        super(context, "clubs", null, 1);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String sqlCreate = "CREATE table clubs ( id integer primary key autoincrement, name text, type text)";
        Log.i("SQL Create", sqlCreate);
        db.execSQL(sqlCreate);
    }

    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists clubs" );
        // Re-create tables
        onCreate( db );
    }

    public void insertClub(String name, String type)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT into clubs values (null, '" + name + "', '" + type + "' )";
        db.execSQL(sqlInsert);
    }

    public void deleteClubByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] { name };
        db.delete("clubs", "name = ?", whereArgs);
    }

    public ArrayList<String> getNames( ) {
        String sqlQuery = "select * from clubs";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> ids = new ArrayList();
        while(cursor.moveToNext()) {
            ids.add(cursor.getString(1));
        }
        db.close();
        return ids;
    }

    public ArrayList<String> getWedgeNames()
    {
        String sqlQuery = "select * from clubs where type = 'Wedge'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> names = new ArrayList();
        while(cursor.moveToNext()) {
            names.add(cursor.getString(1));
        }
        db.close();
        return names;
    }

    public ArrayList<String> getIronNames()
    {
        String sqlQuery = "select * from clubs where type = 'Iron'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> names = new ArrayList();
        while(cursor.moveToNext()) {
            names.add(cursor.getString(1));
        }
        db.close();
        return names;
    }

    public ArrayList<String> getWoodNames()
    {
        String sqlQuery = "select * from clubs where type = 'Wood'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> names = new ArrayList();
        while(cursor.moveToNext()) {
            names.add(cursor.getString(1));
        }
        db.close();
        return names;
    }
}