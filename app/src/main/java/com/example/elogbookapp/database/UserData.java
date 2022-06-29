package com.example.elogbookapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserData extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Elogbook.db";
    //table name
    private static final String TABLE_Name = "User";
    private static final String userToken = "userToken";



    // create table sql query
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_Name + "("
            + userToken + " TEXT"
            + ")";

    // drop table sql query
    private String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_Name;

    /**
     * Constructor
     *
     * @param context
     */
    public UserData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_TABLE);

        // Create tables again
        onCreate(db);

    }

    public int getcount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select count(*) From " + TABLE_Name, null);
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;

    }

    public void adduserToken(String userTokens) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(userToken,userTokens);


        // Inserting Row
        db.insert(TABLE_Name, null, values);
        db.close();
    }


    public void deleteEntry(String userToken) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_Name, userToken + " = ?",
                new String[]{userToken});
        db.close();
    }

    @SuppressLint("Range")
    public String getUserToken() {

        String userToken = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String Select_data = " Select $userToken From " + TABLE_Name;
        Cursor cursor = db.rawQuery(Select_data, null);
        while (cursor.moveToNext()) {

            userToken= cursor.getString(cursor.getColumnIndex(userToken));

        }
        cursor.close();
        db.close();
        System.out.println("userTokenDatabase"+userToken);
        return userToken;
    }

}
