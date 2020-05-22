package com.example.pixaflip.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_NAME
                + " TEXT, " + Params.KEY_URL + " TEXT" + ")";
        Log.d("dbPrabhat", "Query being run is : "+ create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    
    public boolean isExist(String name){
        boolean x = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
            while(cursor.moveToNext()){
                Favourite contact = new Favourite();
                System.out.println(cursor.getString(cursor.getColumnIndex("name")));
                if(cursor.getString(cursor.getColumnIndex("name")).equals(name)){
                    x = true;
                    break;}
            }
        return x;
    }
    
    public void addFavourite(Favourite contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_URL, contact.getUrl());


        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbPrabhat", "Successfully inserted");
        db.close();
    }
    public void deleteById(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_NAME +"=?", new String[]{String.valueOf(name)});
        db.close();
    }
    public List<Favourite> getAll(){
        List<Favourite> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Favourite contact = new Favourite();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setUrl(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
}
