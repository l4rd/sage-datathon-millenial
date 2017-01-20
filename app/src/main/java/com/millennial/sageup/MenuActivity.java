package com.millennial.sageup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    public static String serial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // grab the id from the master activity
        Intent intent = getIntent();
        serial = intent.getStringExtra("serial");
    }
// new test
    public void myaccountClick(View view) {
        Intent intent = new Intent(this, UserDetails.class);
        startActivity(intent);
    }

    public void mentorsClick(View view) {
        Intent intent = new Intent(this, MasterMentorsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sort) {
            // go back an activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            NavUtils.navigateUpTo(this, intent );
            return true;
        }
        return false;
    }
}
