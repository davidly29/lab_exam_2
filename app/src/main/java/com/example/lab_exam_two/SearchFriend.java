package com.example.lab_exam_two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchFriend extends AppCompatActivity {
    String name, number;
    EditText editTextName, editTextNumber;
    Button buttonSearch;
    ArrayList<RetriveParams> data = new ArrayList<>();
    DbAdapter helper;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        helper = new DbAdapter(this);
        c = helper.getData();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    public void search() {
        name = editTextName.getText().toString().trim();
        number = editTextNumber.getText().toString().trim();

        if (name.length() > 0 && number.length() > 0) {
            Toast.makeText(this, "You Can Only search by one category", Toast.LENGTH_LONG).show();
        }
        else {
            if (name.length() > 0) {
                while (c.moveToNext()) {
                    if (c.getString(1).contains(editTextName.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SearchFriend.this);
                        builder.setTitle(editTextName.getText().toString());
                        builder.setMessage("Name: " + editTextName.getText().toString() + "\n" +
                                "Number: " + c.getString(2) + "\n" +
                                "Email: " + c.getString(3));
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }
            }


            if (number.length() > 0) {
                while (c.moveToNext()) {
                    if (c.getString(2).contains(editTextNumber.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SearchFriend.this);
                        builder.setTitle(c.getString(1));
                        builder.setMessage("Name: " + c.getString(1) + "\n" +
                                "Number: " + c.getString(2) + "\n" +
                                "Email: " + c.getString(3));
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                    }
                }
            }
        }
    }
}

