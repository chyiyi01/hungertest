package com.example.test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, password text, number int,name text,free text)"); //registration+login
        db.execSQL("Create table donor(donor_name text, number int, address text, type text, quantity int, vEmail text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists donor");
    }

    public boolean insert(String email, String password, Integer number, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("number",number);
        contentValues.put("name",name);
        //Initially we are no taking location value at registration time so ''
        contentValues.put("free","y");

        long ins = db.insert("user", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }

    public boolean insertDonor(String donor_name, String number, String address, String type, String quantity){
        SQLiteDatabase dbd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("donor_name", donor_name);
        contentValues.put("number", number);
        contentValues.put("address",address);
        contentValues.put("type",type);
        contentValues.put("quantity",quantity);
        contentValues.put("vEmail","");

        long ins = dbd.insert("donor", null, contentValues);
        if (ins == 1)
            return false;
        else {
            Log.d("data",contentValues.toString());
            return true;
        }
    }

    public List<String> displayDforV() //returns a string with donor details
    {
        List<String> data = new ArrayList<>();
        SQLiteDatabase dbd = this.getReadableDatabase();
        Cursor cursor = dbd.rawQuery("SELECT * FROM donor", null);
        if(cursor.getCount()>0){
            cursor.moveToLast();
            data.add(cursor.getString(0));
            data.add(cursor.getString(1));
            data.add(cursor.getString(2));
            data.add(cursor.getString(5));
            Log.d("data", data.toString());
            return data;
        }
        else {
            cursor.close();
            dbd.close();
            return null;
        }
    }

    public boolean updateVFreeY(){
        SQLiteDatabase dbd=this.getWritableDatabase();
        List<String> n1=displayDforV();
        String vEmail=n1.get(3);
        Log.d("email update y ", vEmail);
        String qUpdateEmail= "UPDATE user SET free='y' WHERE TRIM(email)='"+vEmail.trim()+"'";
        dbd.execSQL(qUpdateEmail);
        return true;
    }
}
