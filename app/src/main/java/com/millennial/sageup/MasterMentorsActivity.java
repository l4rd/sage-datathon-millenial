package com.millennial.sageup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;


// new comment
public class MasterMentorsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_mentors);


        JSONGetMentorsTask task = new JSONGetMentorsTask();
        Pair<String, Integer> pair = new Pair<>("E963869", 10000);
        task.execute(pair);
    }


    private class JSONGetMentorsTask extends AsyncTask<Pair<String, Integer>, Void, Mentor> {

        @Override
        protected Mentor doInBackground(Pair<String, Integer>... params) {
            ArrayList<Mentor> mentor;

            try {
                mentor = SageParser.GetUserMentors(params[0].first, params[0].second);

            } catch(JSONException e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Mentor mentor) {
            super.onPostExecute(mentor);
           // Log.d("mentor: ", mentor.name);
        }

    }
}
