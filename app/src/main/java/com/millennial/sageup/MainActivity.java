package com.millennial.sageup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity{

    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
    }


    public void loginClick(View view) {
        JSONLoginTask loginTask = new JSONLoginTask();
        loginTask.execute(new Pair<String, String>(username.getText().toString(), password.getText().toString()));
    }

    public void infoClick(View v) {

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);
        adBuilder.setTitle("Login Help");
        adBuilder.setMessage(getResources().getString(R.string.home_help));
        adBuilder.setPositiveButton("Close", null);
        AlertDialog dialog = adBuilder.create();
        dialog.show();

    }

    public void createAccountClick(View v) {
        Intent myIntent = new Intent(v.getContext(), CreateAccount.class);
        startActivity(myIntent);
    }

    private class JSONLoginTask extends AsyncTask<Pair<String, String>, Void, String> {

        @Override
        protected String doInBackground(Pair<String, String>... params) {

            String serial = null;
            try {
                serial = SageParser.CheckLoginDetails(params[0].first, params[0].second);
            } catch (JSONException e) {
                serial = null;
            }

            return serial;
        }

        @Override
        protected void onPostExecute(String serial) {
            super.onPostExecute(serial);

            if(serial != null) {
                Toast.makeText(MainActivity.this, "Logging in...", LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("serial", serial);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Username and/or password is incorrect.", LENGTH_LONG).show();
            }

        }

    }
}
