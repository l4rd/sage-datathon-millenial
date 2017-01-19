package com.millennial.sageup;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void loginClick(View view) {
        Intent intent = new Intent(this, UserDetails.class);
        startActivity(intent);
    }

    public void infoClick(View v) {

        /*
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);
        adBuilder.setTitle("Login Help");
        adBuilder.setMessage("This will contain the help for the login page.");
        adBuilder.setPositiveButton("Close", null);
        AlertDialog dialog = adBuilder.create();
        dialog.show();*/

        Intent accountCreation = new Intent(this, CreateAccount.class);
        startActivity(accountCreation);
    }
}
