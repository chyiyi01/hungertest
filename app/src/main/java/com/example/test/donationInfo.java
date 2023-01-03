package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class donationInfo extends AppCompatActivity{
    DatabaseHelper db; // create a database helper object
    EditText name, mobile,address,type, quantity;
    Button buttonDonate;
    String nameDonor, mobileDonor,addressDonor,typeDonor,quantityDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_info);
        db= new DatabaseHelper(this); // initialize the db
    }
    public void donor2(View v6) {
        name = findViewById(R.id.nameDonor);
        mobile =  findViewById(R.id.mobileDonor);
        address = findViewById(R.id.addressDonor);
        type =  findViewById(R.id.foodTypeDonor);
        quantity =findViewById(R.id.quantityDonor);
        buttonDonate =findViewById(R.id.buttonDonor);
        nameDonor = name.getText().toString();
        mobileDonor = mobile.getText().toString();
        addressDonor = address.getText().toString();
        typeDonor = type.getText().toString();
        quantityDonor= quantity.getText().toString();

        Boolean insertTry=db.insertDonor(nameDonor, mobileDonor,addressDonor,typeDonor,quantityDonor); //Insertdonor  function will insert into table
        if (insertTry) {
            Toast.makeText(getApplicationContext(), "Donation Saved", Toast.LENGTH_SHORT).show();
            Intent i6 = new Intent(this, pickupinfo.class);
            startActivity(i6);
        }
    }
}
