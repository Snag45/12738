package com.shreenagaraga.fingerlocationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText editTextUser;
    EditText editTextEmail;
    EditText editTextEmpID;
    EditText editTextPhone;
    Button submit;
    String UID;


    DatabaseReference databaseUsers;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        editTextUser= (EditText)findViewById(R.id.username);
        editTextEmail= (EditText)findViewById(R.id.emailID);
        editTextEmpID= (EditText)findViewById(R.id.empID);
        editTextPhone= (EditText)findViewById(R.id.mobNum);

        submit=(Button)findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUSer();
            }
        });


    }

    private void addUSer(){

        FirebaseUser userData = FirebaseAuth.getInstance().getCurrentUser();
        if (userData != null) {
            // User is signed in
            UID=userData.getUid();
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child("UID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // use "username" already exists
                    Intent intent= new Intent(Register.this, Signin.class);
                    startActivity(intent);
                    // Let the user know he needs to pick another username.
                    }
                    else
                    {
                    // User does not exist. NOW call createUserWithEmailAndPassword
                    String name=editTextUser.getText().toString().trim();
                    String mailID=editTextEmail.getText().toString().trim();
                    String emplID=editTextEmpID.getText().toString().trim();
                    String mobno=editTextPhone.getText().toString().trim();


                    if(!TextUtils.isEmpty(emplID)){
                        databaseUsers.push().getKey();
                        User user= new User(name, mailID, emplID, mobno,UID);
                        databaseUsers.child(UID).setValue(user);
                        Intent intent= new Intent(Register.this, Fingerprint.class);
                        startActivity(intent);
                        }
                    // Your previous code here
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
