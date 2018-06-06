package com.scdm15.cecep.peminatan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout linearLayout;
    AnimationDrawable animationDrawable;
    public static final String UserNim = "";
    public static final String UserNama = "";
    public static final String UserPeminatan = "";
    public static final String UserAngkatan = "";

    //Declaration EditTexts
    EditText editTextNim;
    EditText editTextPassword;
    TextView lblCopyright;

    //Declaration Button
    Button btnLogin;
    Button btnRegister;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);

        sqliteHelper = new SqliteHelper(this);
        db = sqliteHelper.getReadableDatabase();

        initViews();
        lblCopyright.setText("Cecep S-3411151021");


        //set click event of login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get values from EditText fields
                String Nim = editTextNim.getText().toString();
                String Password = editTextPassword.getText().toString();

                //Authenticate user
                Mahasiswa currentUser = sqliteHelper.Authenticate(new Mahasiswa(Nim, null, null, null,Password));

                //Check Authentication is successful or not
                if (currentUser != null) {

                    cursor = db.rawQuery("SELECT *FROM "+sqliteHelper.TABLE_MAHASISWA+" WHERE "+sqliteHelper.KEY_NIM+"=? AND "+sqliteHelper.KEY_PASSWORD+"=?",new String[] {Nim,Password});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                            String _nim= cursor.getString(cursor.getColumnIndex(sqliteHelper.KEY_NIM));
                            String _nama= cursor.getString(cursor.getColumnIndex(sqliteHelper.KEY_NAMA));
                            String _peminatan= cursor.getString(cursor.getColumnIndex(sqliteHelper.KEY_PEMINATAN));
                            String _angkatan= cursor.getString(cursor.getColumnIndex(sqliteHelper.KEY_ANGKATAN));

                            //User Logged in Successfully Launch You home screen activity
                            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("UserNim", _nim);
                            intent.putExtra("UserNama",_nama);
                            intent.putExtra("UserPeminatan", _peminatan);
                            intent.putExtra("UserAngkatan", _angkatan);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {

                    //User Logged in Failed
                    Snackbar.make(btnLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                }
            }
        });

        //set click event of login button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextNim = (EditText) findViewById(R.id.editNim);
        editTextPassword = (EditText) findViewById(R.id.editPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        lblCopyright = (TextView) findViewById(R.id.lblCopyright);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Yakin kamu mau ninggalin aku? :( ");
        builder.setPositiveButton("Iyalah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("Gaakan dong :)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
