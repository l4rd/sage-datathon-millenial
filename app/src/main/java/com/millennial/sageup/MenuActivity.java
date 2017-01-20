package com.millennial.sageup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    public static String serial;
    private ArrayList<String> alertMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // grab the id from the master activity
        Intent intent = getIntent();
        serial = intent.getStringExtra("serial");

        Log.d("menuSerial", serial);

        JSONAlertTask alertTask = new JSONAlertTask();
        alertTask.execute(serial);
    }
// new test
    public void myaccountClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, UserDetails.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 0);

    }

    public void mentorsClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MasterMentorsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 0);
    }

    public void alertsClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Alerts");
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < alertMessages.size(); i++) {
            sb.append(String.valueOf(i+1) + ". " + alertMessages.get(i) + "\n\n");
        }

        String alerts = sb.toString();

        builder.setMessage(alerts);

        builder.setPositiveButton("Close", null);
        builder.setNegativeButton("Call Sage", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uri = "tel:+44800336633";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }

        });

        AlertDialog dialog;

        dialog = builder.create();

        dialog.show();
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

    private class JSONAlertTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            ArrayList<String> alerts;

            try {
                alerts = SageParser.GetAlertMessage(serial);
            } catch(JSONException e) {
                alerts = null;
            }

            return alerts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);

            if(s != null) {
                alertMessages = s;
                Integer count = alertMessages.size();

                if(count != 0) {
                    Button alerts = (Button) findViewById(R.id.alertButton);
                    alerts.setText("Alerts (" + String.valueOf(count) + ")");
                    alerts.setBackgroundColor(Color.parseColor("#BBFF5400"));
                    alerts.setEnabled(true);
                }
            }
        }
    }
}
