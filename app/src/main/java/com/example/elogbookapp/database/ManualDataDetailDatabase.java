package com.example.elogbookapp.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.elogbookapp.model.ManualDataDetail;

import java.util.ArrayList;
import java.util.List;

public class ManualDataDetailDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Elogbook.db";
    //table name
    private static final String TABLE_Name = "ManualDataDetail";
    private static final String manualDataDetailId = "manualDataDetailId";
    private static final String androidId = "androidId";
    private static final String dateAndTime = "dateAndTime";
    private static final String templateId = "templateId";
    private static final String sectionId = "sectionId";
    private static final String parameterId = "parameterId";
    private static final String value = "value";


    // create table sql query
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_Name + "("
            + manualDataDetailId + " INTEGER PRIMARY KEY AUTOINCREMENT," + manualDataDetailId + " INTEGER,"
            + androidId + " INTEGER," + dateAndTime + " TEXT," + templateId + " INTEGER,"
            + sectionId + " INTEGER," + parameterId + " INTEGER," + value + " TEXT"
            + ")";

    // drop table sql query
    private String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_Name;

    /**
     * Constructor
     *
     * @param context
     */
    public ManualDataDetailDatabase(Context context) {
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

    public void addManualEntry(ManualDataDetail manualDataDetail) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(androidId, manualDataDetail.getAndroidId());
        values.put(dateAndTime, manualDataDetail.getDateAndTime());
        values.put(templateId, manualDataDetail.getTemplateId());
        values.put(sectionId, manualDataDetail.getSectionId());
        values.put(parameterId, manualDataDetail.getParameterId());
        values.put(value, manualDataDetail.getValue());


        // Inserting Row
        db.insert(TABLE_Name, null, values);
        db.close();
    }


    public void deleteEntry(ManualDataDetail manualDataDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_Name, manualDataDetailId + " = ?",
                new String[]{String.valueOf(manualDataDetail.getManualDataDetailId())});
        db.close();
    }

    @SuppressLint("Range")
    public List<ManualDataDetail>  getAllEntry() {

        List<ManualDataDetail> manualDatalist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Select_data = " Select * From " + TABLE_Name;
        Cursor cursor = db.rawQuery(Select_data, null);
        while (cursor.moveToNext()) {
            ManualDataDetail manualDataDetail = new ManualDataDetail(Integer.parseInt(cursor.getString(cursor.getColumnIndex(manualDataDetailId))), cursor.getString(cursor.getColumnIndex(androidId)), cursor.getString(cursor.getColumnIndex(dateAndTime)), Integer.parseInt(cursor.getString(cursor.getColumnIndex(templateId))), Integer.parseInt(cursor.getString(cursor.getColumnIndex(sectionId))), Integer.parseInt(cursor.getString(cursor.getColumnIndex(parameterId))), cursor.getString(cursor.getColumnIndex(value)));
            manualDatalist.add(manualDataDetail);
        }
        cursor.close();
        db.close();
        return manualDatalist;
    }

}

