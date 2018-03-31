package com.shreenagaraga.fingerlocationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;
import java.util.Locale;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( getIntent().getBooleanExtra("Exit me", false)) {
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }

            loadLocale();

        button= (Button) findViewById(R.id.buttonNextPage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next= new Intent(MainActivity.this, Instruction.class);
                startActivity(next);
            }
        });

        Button changelang = findViewById(R.id.change_lang);
        changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showChangeLanguageDialog();
            }
        });

        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                goOnSkip();
            }
        });
    }

    private void showChangeLanguageDialog() {
        final String[] listItems={"English","हिंदी"};
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Select language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0) {
                    Log.i("pressed button","english");
                    setLocale("en");
                    recreate();
                }

                else if(which==1) {
                    Log.i("pressed button","hindi");
                    setLocale("hi");
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog= mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String Lang)
    {
        Locale locale= new Locale(Lang);
        Locale.setDefault(locale);
        Configuration config= new Configuration();
        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", Lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs= getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);

    }
    public void goOnSkip(){
        Intent intent=new Intent(MainActivity.this,Signin.class);
        startActivity(intent);
    }
}