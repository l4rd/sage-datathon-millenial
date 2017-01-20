package com.millennial.sageup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailMentorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mentors);

        // grab the id from the master activity
        Intent intent = getIntent();
        String message = intent.getStringExtra("selectedmentorid");
        int placelong = Integer.parseInt(message);

        Mentor mentor = MasterMentorsActivity.mentor.get(placelong);

        TextView name = (TextView) findViewById(R.id.textViewMentorName);
        name.setText(mentor.serial);


    }
}
