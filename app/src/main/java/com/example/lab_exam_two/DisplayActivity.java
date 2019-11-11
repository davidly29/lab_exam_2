package com.example.lab_exam_two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    RecyclerView recyclerView;
    String name, number, email;
    EditText editTextRemove;
    Button buttonDeleteUser;
    ArrayList<RetriveParams> data = new ArrayList<>();

    DbAdapter helper;

    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.rev);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        helper = new DbAdapter(this);
        final ExtraAdapter adp = new ExtraAdapter(this, data, recyclerView);
        recyclerView.setAdapter(adp);

        editTextRemove = (EditText) findViewById(R.id.editTextRemove);
        buttonDeleteUser = (Button) findViewById(R.id.buttonDeleteUser);

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.delete(editTextRemove.getText().toString(), DisplayActivity.this);
                data.remove(editTextRemove.getText().toString());
                adp.notifyDataSetChanged();
                recyclerView.setAdapter(adp);
            }
        });

        c=helper.getData();

            while (c.moveToNext()) {
               // name = c.getString(1) + " " +  c.getString(2) + " " + c.getString(3);
                name = c.getString(1);
                number = c.getString(2);
                email = c.getString(3);

                RetriveParams pr = new RetriveParams(name, number, email);
                data.add(pr);
            }
    }

    public void remove(int position){

    }
}

