package com.millennial.sageup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

    }

    public void facebookClick(View view){}
    public void twitterClick(View view){}
    public void linkedinClick(View view){}
    public void instagramClick(View view){}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // go back an activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        NavUtils.navigateUpTo(this, intent );
        return true;
    }
}
