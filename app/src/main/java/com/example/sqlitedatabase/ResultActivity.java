package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView textView;
    ArrayList<String> dataList;
    ListView listView;
    String nameGetStr;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = findViewById(R.id.name_text);
        listView = findViewById(R.id.list);
        dataList = new ArrayList<>();

        // initialize MyDataBaseHelper
        dbHelper = new MyDatabaseHelper(this);

        // open database for writing and reading
        database = dbHelper.getReadableDatabase();

        // Method 1 : read data from the database, using query() and display item in a textview
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_NAME, new String[]{
                        MyDatabaseHelper.UID, MyDatabaseHelper.COLUMN_USER_NAME},
                null, // column WHERE
                null, // value WHERE
                null, // not grouping rows
                null, // not filtering group rows
                null // sort order
        );
        while (cursor.moveToNext()) {
            // get indexes and column values
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.UID));

            nameGetStr = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_USER_NAME));
        }

        textView.setText(nameGetStr);

        cursor.close();

        // Method 2 : read data from the database, using  rawQuery() and display the items in a list
        Cursor dataCursor = database.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_NAME, null);

        if (dataCursor.getCount() == 0) {
            Toast.makeText(ResultActivity.this, "Empty database!", Toast.LENGTH_SHORT).show();
        } else {
            while (dataCursor.moveToNext()) {
                dataList.add(dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_USER_NAME)));
            }
            ArrayAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(listAdapter);
        }
        dataCursor.close();

    }

    @Override
    protected void onDestroy() {
        // close DB connection
        database.close();
        dbHelper.close();
        super.onDestroy();
    }
}