package com.example.lab_exam_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextName, editTextNumber, editTextEmail;
    Button button, buttonView, buttonSearchUser;
    DbAdapter helper;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        button = (Button) findViewById(R.id.button);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonSearchUser = (Button) findViewById(R.id.buttonSearchUser);

        helper = new DbAdapter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().trim().isEmpty() || editTextNumber.getText().toString().trim().isEmpty() || editTextEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
                }else {
                    addUser(v);
                }

            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);
            }
        });
        buttonSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchFriend.class);
                startActivity(intent);
            }
        });


    }


    public void addUser(View view)
    {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();

                if (authUser(name, number, email) > 0)
                {
                Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
                } else
                    {
                        long id = helper.insertFriend(name, email, number);
                        if (id <= 0) {
                            Toast.makeText(this, "Error inserting data", Toast.LENGTH_LONG).show();
                            editTextName.setText("");
                            editTextNumber.setText("");
                            editTextEmail.setText("");
                        } else
                            {
                                Toast.makeText(this, "Data inserted", Toast.LENGTH_LONG).show();
                                editTextName.setText("");
                                editTextNumber.setText("");
                                editTextEmail.setText("");
                }
            }

    }

    public void viewUser(View view){
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);
    }

    public int authUser(String name, String number, String email){
        int result = 0;
        c = helper.getData();

        while(c.moveToNext()){
            if(name.equalsIgnoreCase(c.getString(1)) || number.equalsIgnoreCase(c.getString(2)) || email.equalsIgnoreCase(c.getString(3))){
                result = 1;
                Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
            }

        }
        return result;

    }

}
