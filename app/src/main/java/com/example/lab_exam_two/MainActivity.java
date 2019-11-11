package com.example.lab_exam_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    Button button, buttonView;
    DbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        button = (Button) findViewById(R.id.button);
        buttonView = (Button) findViewById(R.id.buttonView);
        helper = new DbAdapter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);
            }
        });


    }


    public void addUser(View view)
    {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String number = editTextNumber.getText().toString();

            long id = helper.insertFriend(name, email, number);
            if(id<=0)
            {
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

    public void viewUser(View view){
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);
    }

    public int deleteUser(){
    return 0;
    }

}
