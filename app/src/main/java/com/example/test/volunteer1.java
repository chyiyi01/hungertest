package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class volunteer1 extends AppCompatActivity{
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer1);
        db = new DatabaseHelper(this);
    }
    public void goToThanks(View v15){
        //Set value of Free back to N as volunteer is free now
        db.updateVFreeY();
        Intent i6 = new Intent(this, thanks.class);
        startActivity(i6);
    }
}
