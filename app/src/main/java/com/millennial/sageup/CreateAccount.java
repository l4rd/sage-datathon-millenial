package com.millennial.sageup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by TehLe on 19/01/2017.
 */

public class CreateAccount extends AppCompatActivity {

    EditText username;
    EditText serial;
    EditText password;
    EditText repeat;
    Button create;
    Button check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        username = (EditText) findViewById(R.id.editUsername);
        serial = (EditText) findViewById(R.id.editSerial);
        password = (EditText) findViewById(R.id.editPassword);
        repeat = (EditText) findViewById(R.id.editRepeat);
        create = (Button) findViewById(R.id.buttonCreate);
        check = (Button) findViewById(R.id.buttonCheck);

        initialiseTextWatchers();
    }

    public void checkClick(View v) {

        if(password.getText().length() > 5) {

            if(password.getText().toString().equals(repeat.getText().toString())) {
                Pair<String, String> pair = new Pair<>(username.getText().toString(), serial.getText().toString());
                JSONCheckUserTask task = new JSONCheckUserTask();
                task.execute(pair);
            } else {
                Toast.makeText(this, "Passwords do not match.", LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Password is too short.", LENGTH_SHORT).show();
        }

    }

    private void initialiseTextWatchers() {
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                create.setEnabled(false);
                check.setEnabled(true);
            }
        });

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                create.setEnabled(false);
                check.setEnabled(true);
            }
        });

        repeat.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                create.setEnabled(false);
                check.setEnabled(true);
            }

        });

        serial.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                create.setEnabled(false);
                check.setEnabled(true);
            }
        });
    }

    private class JSONCheckUserTask extends AsyncTask<Pair<String, String>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Pair<String, String>... params) {
            Boolean valid = false;

            try {
                valid = SageParser.CheckUserAndSerial(params[0].first, params[0].second);
            } catch(JSONException e) {
                valid = false;
            } finally {
                return valid;
            }
        }

        @Override
        protected void onPostExecute(Boolean valid) {
            super.onPostExecute(valid);

            if(valid) {
                create.setEnabled(true);
                check.setEnabled(false);
            }
        }

    }
}

