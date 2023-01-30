package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class donationInfo extends AppCompatActivity{
    DatabaseHelper db; // create a database helper object
    EditText name, mobile,address,type, quantity;
    Button buttonDonate;
    String nameDonor, mobileDonor,addressDonor,typeDonor,quantityDonor;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public static final String TAG = "TAG";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_info);
        db= new DatabaseHelper(this); // initialize db
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

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        Boolean insertTry=db.insertDonor(nameDonor, mobileDonor,addressDonor,typeDonor,quantityDonor); //insertDonor function will insert into table
        if (insertTry) {
            userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
            CollectionReference collectionReference = fStore.collection("donor data");
            Map<String,Object> user = new HashMap<>();
            user.put("timestamp", FieldValue.serverTimestamp());
            user.put("name",nameDonor);
            user.put("phone",mobileDonor);
            user.put("address",addressDonor);
            user.put("foodType", typeDonor);
            user.put("quantity", quantityDonor);
            collectionReference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(donationInfo.this, "Success.", Toast.LENGTH_SHORT) .show();
                    Log.d(TAG,"Success!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Error!", e);
                }
            });
            Toast.makeText(getApplicationContext(), "Donation Saved", Toast.LENGTH_SHORT).show();
            Intent i6 = new Intent(this, thanks.class);
            startActivity(i6);
        }
    }
}
