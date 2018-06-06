package com.scdm15.cecep.peminatan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "db_sistem_peminatan";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_MAHASISWA = "mahasiswa";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_NIM = "nim";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_ANGKATAN = "angkatan";
    public static final String KEY_PEMINATAN = "peminatan";
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_MAHASISWA = " CREATE TABLE " + TABLE_MAHASISWA
            + " ( "
            + KEY_NIM + " TEXT PRIMARY KEY, "
            + KEY_NAMA + " TEXT, "
            + KEY_ANGKATAN + " TEXT, "
            + KEY_PEMINATAN + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_MAHASISWA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
    }

    //using this method we can add users to user table
    public void addUser(Mahasiswa user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_NIM, user.nim);
        values.put(KEY_NAMA, user.nama);
        values.put(KEY_ANGKATAN, user.angkatan);
        values.put(KEY_PEMINATAN, user.peminatan);
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_MAHASISWA, null, values);
    }

    public Mahasiswa Authenticate(Mahasiswa user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAHASISWA,// Selecting Table
                new String[]{KEY_NIM, KEY_NAMA, KEY_ANGKATAN, KEY_PEMINATAN, KEY_PASSWORD},//Selecting columns want to query
                KEY_NIM + "=?",
                new String[]{user.nim},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            Mahasiswa user1 = new Mahasiswa(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),  cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String nim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAHASISWA,// Selecting Table
                new String[]{KEY_NIM, KEY_NAMA, KEY_ANGKATAN ,KEY_PEMINATAN, KEY_PASSWORD},//Selecting columns want to query
                KEY_NIM + "=?",
                new String[]{nim},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }


}