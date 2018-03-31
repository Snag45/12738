package com.shreenagaraga.fingerlocationapp;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shreenagaraga.fingerlocationapp.LocationUtil.LocationHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.util.Calendar;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationFetch extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {

    Button submit,siout;

    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
//    @BindView(R.id.rlPickLocation)
//    RelativeLayout rlPick;

    private Location mLastLocation;

    double latitude;
    double longitude;

    LocationHelper locationHelper;

    DatabaseReference databaseUsers;
    DatabaseReference databaseList;
    private FirebaseDatabase mData;
    DatabaseReference mDataReferences;

    static Date currentTime = Calendar.getInstance().getTime();
    Date current=currentTime;


    public String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_fetch);

        mDataReferences = FirebaseDatabase.getInstance().getReference();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseList= FirebaseDatabase.getInstance().getReference("locationList");

        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();

        ButterKnife.bind(this);

        submit= findViewById(R.id.buttonMA);
        siout= findViewById(R.id.button3);


//        rlPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        submit.setOnClickListener(new View.OnClickListener(){
            String mAuth=FirebaseAuth.getInstance().getCurrentUser().getUid();
            //String mAuth="2bX7uCEVO8PdqGq30THLM3loy793";
            @Override
            public void onClick(View v) {
                DatabaseReference mUserData=FirebaseDatabase.getInstance().getReference().child("users").child(mAuth);
                mUserData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post post= dataSnapshot.getValue(Post.class);
                        String empIDD= post.getEmp_ID();
                        //Toast.makeText(LocationFetch.this, " "+empidd, Toast.LENGTH_SHORT).show();
                            markattendance(empIDD);
                        //Toast.makeText(LocationFetch.this, " "+empIDD, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });

        siout.setOnClickListener(new View.OnClickListener(){
            String mAuth=FirebaseAuth.getInstance().getCurrentUser().getUid();
            //String mAuth="2bX7uCEVO8PdqGq30THLM3loy793";
            @Override
            public void onClick(View v) {
                DatabaseReference mUserData=FirebaseDatabase.getInstance().getReference().child("users").child(mAuth);
                mUserData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post post= dataSnapshot.getValue(Post.class);
                        String empIDD= post.getEmp_ID();
                        //Toast.makeText(LocationFetch.this, " "+empidd, Toast.LENGTH_SHORT).show();
                        signout(empIDD);
                        //Toast.makeText(LocationFetch.this, " "+empIDD, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });



//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                String mUserData = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth).child("emp_ID").toString();
//                Toast.makeText(LocationFetch.this, "mdata " + mAuth, Toast.LENGTH_SHORT).show();
//            }
//        });





        if (locationHelper.checkPlayServices()) {
            locationHelper.buildGoogleApiClient();
        }

    }


    public void getAddress() {
        Address locationAddress;

        locationAddress = locationHelper.getAddress(latitude, longitude);

        if (locationAddress != null) {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;

                tvEmpty.setVisibility(View.GONE);
                tvAddress.setText(currentLocation);
                tvAddress.setVisibility(View.VISIBLE);

            }

        } else
            showToast("Something went wrong");
    }

    public void markattendance(String empid) {

        mLastLocation = locationHelper.getLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            getAddress();

        } else {

            // if(btnProceed.isEnabled())
            //   btnProceed.setEnabled(false);

            showToast("Couldn't get the location. Make sure location is enabled on the device");
        }

//        DatabaseReference lUserData=FirebaseDatabase.getInstance().getReference().child("locationList").child(mAuth);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            DatabaseReference database = FirebaseDatabase.getInstance().getReference();

            if (!TextUtils.isEmpty(empid)) {
                databaseList.push().getKey();
                Loc loc = new Loc(empid, currentLocation, current, currentTime);
                databaseList.child(empid).setValue(loc);
                Toast.makeText(this, "data added and attendance marked " +empid, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void signout(String empid) {
//        DatabaseReference lUserData=FirebaseDatabase.getInstance().getReference().child("locationList").child(mAuth);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final Date currentTime2 = Calendar.getInstance().getTime();

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();

            if (!TextUtils.isEmpty(empid)) {
                databaseList.push().getKey();
                Loc loc = new Loc(empid, currentLocation, current, currentTime2);
                databaseList.child(empid).setValue(loc);

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        locationHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume () {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult result){
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected (Bundle arg0){
        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended ( int arg0){
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

