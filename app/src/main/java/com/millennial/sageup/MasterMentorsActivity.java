package com.millennial.sageup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;


// new comment
public class MasterMentorsActivity extends AppCompatActivity {
    public static ArrayList<Mentor> mentor;
    ArrayAdapter<Mentor> arrayAdapter;
    ListView item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_mentors);


        JSONGetMentorsTask task = new JSONGetMentorsTask();
        Pair<String, Integer> pair = new Pair<>("E963869", 10000);
        task.execute(pair);

        item = (ListView) findViewById(R.id.listofmentors);
        setupListClicks();


    }
    public void setupListClicks() {
        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // once an item in the master list is clicked...
                Intent intent = new Intent(view.getContext(), DetailMentorsActivity.class);
                String newid = Long.toString(id);
                // send the id data of the place selected over to the
                // detail activity.
                intent.putExtra("selectedmentorid", newid);
                startActivity(intent);
            }
        });
    }

    private class JSONGetMentorsTask extends AsyncTask<Pair<String, Integer>, Void, Mentor> {

        @Override
        protected Mentor doInBackground(Pair<String, Integer>... params) {
            try {
                mentor = SageParser.GetUserMentors(params[0].first, params[0].second);
                Log.d("serial", mentor.get(1).serial);

            } catch(JSONException e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Mentor mentor) {
            super.onPostExecute(mentor);
            loadmentors();
        }

    }

    public void loadmentors() {
        // Create the adapter to convert the array to views
        MentorsCursorAdapter adapter = new MentorsCursorAdapter(getApplicationContext(), mentor);
        item.setAdapter(adapter);
    }
}
