package com.example.customerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "customer.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_SERIAL = "serial";
    public static final String COLUMN_BATTERY = "battery";
    public static final String COLUMN_SIM = "sim";
    public static final String COLUMN_SLOT = "slot";
    public static final String COLUMN_DOOR = "door";
    public static final String COLUMN_MEMORY = "memory";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PATTERN = "pattern";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CODE + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_SERIAL + " TEXT, " +
                    COLUMN_BATTERY + " INTEGER, " +
                    COLUMN_SIM + " INTEGER, " +
                    COLUMN_SLOT + " INTEGER, " +
                    COLUMN_DOOR + " INTEGER, " +
                    COLUMN_MEMORY + " INTEGER, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PATTERN + " TEXT " +
                    ");";

    public CustomerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        onCreate(db);
    }
}
