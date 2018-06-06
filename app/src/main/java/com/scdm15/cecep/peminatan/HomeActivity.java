package com.scdm15.cecep.peminatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    //Declaration TextView
    TextView labelNim;
    TextView labelNama;
    TextView labelAngkatan;
    TextView labelPeminatan;
    String NimHolder;
    String NamaHolder;
    String PeminatanHolder;
    String AngkatanHolder;
    //Declaration SqliteHelper

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        Intent intent = getIntent();
        // Receiving User Email Send By MainActivity.
        NimHolder = intent.getStringExtra("UserNim");
        NamaHolder = intent.getStringExtra("UserNama");
        PeminatanHolder = intent.getStringExtra("UserPeminatan");
        AngkatanHolder = intent.getStringExtra("UserAngkatan");
        // Setting up received email to TextView.
        labelNim.setText(NimHolder);
        labelNama.setText(NamaHolder);
        labelPeminatan.setText(PeminatanHolder);
        labelAngkatan.setText(AngkatanHolder);

    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        labelNim = (TextView) findViewById(R.id.lblnim);
        labelNama= (TextView) findViewById(R.id.lblnama);
        labelAngkatan= (TextView) findViewById(R.id.lblangkatan);
        labelPeminatan = (TextView) findViewById(R.id.lblpeminatan);

    }
}
