package com.millennial.sageup;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
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
    ArrayList<Industry> subGlobalList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        username = (EditText) findViewById(R.id.editUsername);
        serial = (EditText) findViewById(R.id.editUsername);
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

    public void createClick(View v) {
        String usernameParam = username.getText().toString();
        String passwordParam = password.getText().toString();
        String serialParam = serial.getText().toString();

        String[] params = new String[3];
        params[0] = usernameParam;
        params[1] = passwordParam;
        params[2] = serialParam;
        JSONCreateAccountTask task = new JSONCreateAccountTask();
        task.execute(params);
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
                Log.d("try", valid.toString());
            } catch(JSONException e) {
                valid = false;
            } finally {
                Log.d("finally", valid.toString());
                return valid;
            }
        }

        @Override
        protected void onPostExecute(Boolean valid) {
            super.onPostExecute(valid);
            Log.d("postExecute", valid.toString());
            if(valid) {
                create.setEnabled(true);
                check.setEnabled(false);

            }
        }

    }

    private class JSONCreateAccountTask extends AsyncTask<String[], Void, Boolean> {
        @Override
        protected Boolean doInBackground(String[]... params) {
            Boolean valid = false;

            try {
                valid = SageParser.PostNewAccount(params[0][0], params[0][1], params[0][2]);
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
                Toast.makeText(CreateAccount.this, "The account was created!", LENGTH_LONG).show();
                JSONGetSectorTask sectors = new JSONGetSectorTask();
                sectors.execute();
            } else {
                Toast.makeText(CreateAccount.this, "The account could not be created.", LENGTH_LONG).show();
            }
        }
    }

    private class JSONGetSectorTask extends AsyncTask<Void, Void, SectorData> {

        @Override
        protected SectorData doInBackground(Void... params) {

            SectorData sectorData = new SectorData();

            try {
                Log.d("sector", "Worth a try");
                sectorData = SageParser.GetSectorData();

            } catch(JSONException e) {
                Log.d("sector", "big fail");
                sectorData = null;
            } finally {
                return sectorData;
            }

        }

        @Override
        protected void onPostExecute(SectorData sectorData) {
            super.onPostExecute(sectorData);

            AlertDialog.Builder adBuild = new AlertDialog.Builder(CreateAccount.this);
            LayoutInflater inflater = LayoutInflater.from(CreateAccount.this);
            View cusDetails = inflater.inflate(R.layout.company_details, null);

            adBuild.setTitle("Update Details");
            adBuild.setView(cusDetails);

            final Spinner spinMajor = (Spinner) cusDetails.findViewById(R.id.spinnerMajor);
            final Spinner spinSub = (Spinner) cusDetails.findViewById(R.id.spinnerSub);

            ArrayAdapter<Industry> adapter = new ArrayAdapter<Industry>(CreateAccount.this, android.R.layout.simple_spinner_item, sectorData.getMajorIndustry());

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            subGlobalList = sectorData.getSubIndustry();

            ArrayList<Industry> filterList = new ArrayList<>();

            for(Industry i : subGlobalList) {
                if(i.sectorMain.equals("A1")) {
                    filterList.add(i);
                }
            }
            ArrayAdapter<Industry> adapterTwo = new ArrayAdapter<Industry>(CreateAccount.this, android.R.layout.simple_spinner_item, filterList);
            Log.d("sectorData", String.valueOf(sectorData.getSubIndustry().size()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinMajor.setAdapter(adapter);
            adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinSub.setAdapter(adapterTwo);
            spinMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Industry ind = (Industry) parent.getSelectedItem();

                    ArrayList<Industry> chosenList = new ArrayList<Industry>();
                    for(Industry i : subGlobalList) {
                        if(i.getSectorMain().equals(ind.getSectorId())) {
                            chosenList.add(i);
                        }
                    }

                    ArrayAdapter<Industry> adapterThree = new ArrayAdapter<Industry>(parent.getContext(), android.R.layout.simple_spinner_item, chosenList);
                    spinSub.setAdapter(adapterThree);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            adBuild.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CreateAccount.this, "Details updated!", LENGTH_SHORT).show();
                    finish();
                }
            });
            adBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = adBuild.create();

            dialog.show();
        }
    }
}

