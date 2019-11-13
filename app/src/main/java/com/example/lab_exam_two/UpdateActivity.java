package com.example.lab_exam_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText editTextName, editTextNumber, editTextEmail;
    Button buttonUpdateFriend;
    DbAdapter helper;
    ImageView imageView;

    private static String friendToUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonUpdateFriend = (Button) findViewById(R.id.buttonUpdateFriend);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonUpdateFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFriend();
            }
        });
        helper = new DbAdapter(this);

        friendToUpdate = getIntent().getStringExtra("name");
        RetriveParams params = new RetriveParams(getIntent().getStringExtra("name"), getIntent().getStringExtra("number"), getIntent().getStringExtra("email"));
        editTextName.setText(params.getName());
        editTextNumber.setText(params.getNumber());
        editTextEmail.setText(params.getEmail());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        });
    }


    public void updateFriend(){
        String name = editTextName.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if(name.isEmpty()){
            Toast.makeText(this, "Name must be populated", Toast.LENGTH_LONG).show();
        }

        if(number.isEmpty()){
            Toast.makeText(this, "number must be populated", Toast.LENGTH_LONG).show();
        }

        if(email.isEmpty()){
            Toast.makeText(this, "Email must be populated", Toast.LENGTH_LONG).show();
        }

        RetriveParams p = new RetriveParams(name, number, email);
        helper.updatePersonRecord(friendToUpdate, this, p);
        startActivity(new Intent(this, DisplayActivity.class));

    }
}
