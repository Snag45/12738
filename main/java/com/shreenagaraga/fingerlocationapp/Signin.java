package com.shreenagaraga.fingerlocationapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Signin extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseUsers;
    DatabaseReference databaseList;
    private FirebaseDatabase mData;
    DatabaseReference mDataReferences;

    EditText editTextEmail;
    EditText editTextPass;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPass=(EditText)findViewById(R.id.password);

        findViewById(R.id.notuser).setOnClickListener(this);
        findViewById(R.id.buttonSignin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin(){

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPass.getText().toString().trim();

        if(email.isEmpty() ||  password.isEmpty())
        {
            Toast.makeText(this,"one of the fields is empty",Toast.LENGTH_LONG).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email not valid");
            return;
        }

        if(password.length()<6){
            editTextPass.setError("min 6 chars");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(Signin.this, Fingerprint.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.notuser :
                startActivity(new Intent(this,Signup.class));

            case R.id.buttonSignin :
                userLogin();
                break;

        }
    }
}