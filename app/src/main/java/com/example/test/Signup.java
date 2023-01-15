package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText fullName,edtemail,pwd,phoneno;
    Button registerBtn;
    TextView loginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.name);
        edtemail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        phoneno = findViewById(R.id.phone);
        registerBtn=findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() !=null){
            Intent intent = new Intent(Signup.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        registerBtn.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                String email = edtemail.getText().toString().trim();
                String password= pwd.getText().toString().trim();
                String name= fullName.getText().toString().trim();
                String phone= phoneno.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edtemail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    pwd.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6)
                {
                    pwd.setError("Password Must be >=6 Characters");
                    return;
                }
                Boolean tryInsert = db.insert(email, password, phone, name);
                if (tryInsert){
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this, "User Created.", Toast.LENGTH_SHORT) .show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("name",name);
                                user.put("email",email);
                                user.put("phone",phone);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                                        Toast.makeText(Signup.this, "Registered Successfully.", Toast.LENGTH_SHORT) .show();
                                    }
                                });
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Signup.this, "Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}