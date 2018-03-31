package com.shreenagaraga.fingerlocationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail;
    EditText editTextPass;
    Button submit;
    String UID;

    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail=(EditText)findViewById(R.id.emailID);
        editTextPass=(EditText)findViewById(R.id.pass);
        submit=(Button)findViewById(R.id.buttonSignup);

        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(this);
    }

    private void registerUser(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            UID=user.getUid(); }

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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(Signup.this, Register.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(Signup.this, "User registered successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        ref.child("users").child("UID").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Intent intent= new Intent(Signup.this, Signin.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                        Intent intent= new Intent(Signup.this, Register.class);
                                        startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSignup:
                registerUser();
                break;
        }
    }
}
