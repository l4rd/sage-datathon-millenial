package com.millennial.sageup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UserDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // setting Toolbar as Action Bar for the App
        setSupportActionBar(mToolbar);
        mToolbar.setElevation(1);
    }
}
