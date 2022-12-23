package com.example.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "users";
    public static String COLUMN_USER_NAME = "uname";
    public static final String UID = "_ID";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //creating the table and its columns
    @Override
    public void onCreate(SQLiteDatabase database) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT NOT NULL);";

        //execution of SQL operator
        database.execSQL(SQL_CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //on update, delete the previous table
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //create new table
        onCreate(database);

    }
}
