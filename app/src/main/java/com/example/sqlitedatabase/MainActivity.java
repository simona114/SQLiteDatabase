package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button insertButton;
    String nameSaveStr;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        insertButton = findViewById(R.id.insert_button);

        // initialize MyDataBaseHelper
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);

        // open database for writing and reading
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length() != 0) {
                    String nameSaveStr;
                    nameSaveStr = editText.getText().toString().trim();

                    // Method 1 : Creating ContentValues, where the column names are keys , and the items attributes are the values
                    ContentValues values = new ContentValues();
                    values.put(MyDatabaseHelper.COLUMN_USER_NAME, nameSaveStr);
                    database.insert(MyDatabaseHelper.TABLE_NAME, null, values);

                    // Method 2 : add via INSERT, riskier
                    //String insertQuery = "INSERT INTO " + MyDatabaseHelper.TABLE_NAME + " (" + MyDatabaseHelper.COLUMN_USER_NAME + ") VALUES ('" + nameSaveStr + "')";
                    //database.execSQL(insertQuery);

                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, R.string.enter_name, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // close DB connection
        database.close();
        dbHelper.close();
        super.onDestroy();
    }

}
