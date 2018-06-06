package com.scdm15.cecep.peminatan;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextNim;
    EditText editTextNama;
    EditText editTextAngkatan;
    EditText editTextPeminatan;
    EditText editTextPassword;

    //Declaration Button
    Button buttonRegister;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String Nim = editTextNim.getText().toString();
                    String Nama = editTextNama.getText().toString();
                    String Angkatan = editTextAngkatan.getText().toString();
                    String Peminatan = editTextPeminatan.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Nim)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new Mahasiswa(Nim, Nama, Angkatan, Peminatan, Password));
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_INDEFINITE).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, Snackbar.LENGTH_INDEFINITE);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextNim = (EditText) findViewById(R.id.textnim);
        editTextNama= (EditText) findViewById(R.id.textnama);
        editTextAngkatan= (EditText) findViewById(R.id.textangkatan);
        editTextPeminatan= (EditText) findViewById(R.id.textpeminatan);
        editTextPassword= (EditText) findViewById(R.id.textpass);
        buttonRegister = (Button) findViewById(R.id.btnRegister);

    }


}