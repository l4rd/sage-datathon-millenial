package com.millennial.sageup;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TehLe on 19/01/2017.
 */

public class SageParser {

    private static String BASE_URL = "http://ec2-52-56-92-81.eu-west-2.compute.amazonaws.com:3000";

    private static String GetJsonData(String endpoint) {
        HttpURLConnection con = null;
        InputStream is = null;

        // Append the users favourite currency to get a URL string.
        StringBuilder apiString = new StringBuilder();
        apiString.append(BASE_URL);
        apiString.append(endpoint);
        String urlString = apiString.toString();

        try {

            con = (HttpURLConnection) (new URL(urlString)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            //con.setDoOutput(true); This converts the request into a POST, occasionally fixer.io will reject it.
            con.setConnectTimeout(5000);
            con.connect();
            StringBuffer buffer = new StringBuffer();

            // Get the status code and check whether it is an error.
            // Trying to access the input stream from a 400+ error results in a crash.
            int status = con.getResponseCode();
            if(status > 400) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            // Read the JSON data into a string buffer.
            while ((line = br.readLine()) != null) {
                buffer.append(line + "rn");
            }

            // Close the connection to the API.
            is.close();
            con.disconnect();

            // Return the string of JSON data.
            return buffer.toString();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        // If the request fails for any reason then return null.
        return null;
    }

    private static String PostJsonData(String endpoint, String json) {
        HttpURLConnection con = null;
        InputStream is = null;

        // Append the users favourite currency to get a URL string.
        StringBuilder apiString = new StringBuilder();
        apiString.append(BASE_URL);
        apiString.append(endpoint);
        String urlString = apiString.toString();

        try {

            con = (HttpURLConnection) (new URL(urlString)).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Accept", "application/json");
            //con.setDoInput(true);
            con.setConnectTimeout(5000);
            con.setDoOutput(true); //This converts the request into a POST, occasionally fixer.io will reject it.


            String body = json;

            OutputStream os = con.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            StringBuffer buffer = new StringBuffer();

            // Get the status code and check whether it is an error.
            // Trying to access the input stream from a 400+ error results in a crash.
            int status = con.getResponseCode();

            Log.d("status", String.valueOf(status));
            if(status >= 400) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            // Read the JSON data into a string buffer.
            while ((line = br.readLine()) != null) {
                buffer.append(line + "rn");
            }

            // Close the connection to the API.
            is.close();
            con.disconnect();

            // Return the string of JSON data.
            return buffer.toString();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        // If the request fails for any reason then return null.
        return null;
    }

    public static Boolean CheckUserAndSerial(String user, String serial) throws JSONException {
        String endpoint = "/checkSerial/" + user.toLowerCase() + "/" + serial;
        String json = GetJsonData(endpoint);

        Log.d("json", json);
        // We create our JSONObject from the data
        JSONObject jObj = new JSONObject(json);

        // Get the rates object from the JSON.

        Boolean userExists = Boolean.parseBoolean(getString("userexists", jObj));

        Boolean serialExists = Boolean.parseBoolean(getString("serialexists", jObj));

        Log.d("exists", userExists.toString());
        Log.d("exists", serialExists.toString());

        // We want the username to be unique and the serial should be a known serial to Sage.
        if(!userExists && serialExists) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean PostNewAccount(String username, String password, String serial) throws JSONException {

        //String json = "{ 'username': '" + username + "', 'password': '" + password + "', 'serial': '" + serial + "'}";
        //String json = "{ \'username\': \'" + username + "\', \'password\': \'" + password + "\', \'serial\': \'" + serial + "\'}";
        String json = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"serial_number\": \"" + serial + "\"}";
        String returned = PostJsonData("/User", json);

        // We create our JSONObject from the data
        JSONObject jObj = new JSONObject(returned);

        // Get the rates object from the JSON.

        Boolean userCreated = Boolean.parseBoolean(getString("created", jObj));

        return userCreated;
    }

    public static SectorData GetSectorData() throws JSONException {
        String endpoint = "/sectors";
        String json = GetJsonData(endpoint);
        JSONArray jArr = new JSONArray(json);
        SectorData sectorData = new SectorData();

        ArrayList<Industry> industries = new ArrayList<>();
        for(int i = 0; i < jArr.length(); i++) {
            JSONObject sectorObj = jArr.getJSONObject(i);
            String sectorId = getString("sectorid", sectorObj);
            String sectorName = getString("sectorname", sectorObj);
            Log.d("sector", sectorId);
            Log.d("sector", sectorName);
            Industry ind = new Industry(sectorId, sectorName);
            industries.add(ind);

        }

        ArrayList<Industry> indSub = GetSubSectors();
        sectorData.setMajorIndustry(industries);
        sectorData.setSubIndustry(indSub);
        return sectorData;
    }

    public static ArrayList<Mentor> GetUserMentors(String sector, int revenue) throws JSONException {
        //  /getmontors/sector/reventue
        //  /sectors returns array of objects


            String endpoint = "/getmentors/" + sector + "/" + revenue;
            String json = GetJsonData(endpoint);

            JSONArray jArr = new JSONArray(json);


            ArrayList<Mentor> mentorlist = new ArrayList<>();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject sectorObj = jArr.getJSONObject(i);
                Mentor mentor = new Mentor();
                mentor.serial = getString("serial", sectorObj);
                mentor.sector = getString("MajorIndustrySector", sectorObj);

                mentorlist.add(mentor);
            }

            Log.d("single entity serial: ", mentorlist.get(1).serial);
            Log.d("JSON: ", json);

            return mentorlist;

    }

    public static ArrayList<Industry> GetSubSectors() throws JSONException {
        String endpoint = "/2dsectors/";
        String json = GetJsonData(endpoint);
        Log.d("HERE6", json);
        JSONArray jArr = new JSONArray(json);

        ArrayList<Industry> industries = new ArrayList<>();

        for(int i = 0; i < jArr.length(); i++) {
            JSONObject sectorObj = jArr.getJSONObject(i);
            String sectorId = getString("sectorid", sectorObj);
            String sectorName = getString("sectorname", sectorObj);
            String sectorMain = getString("major_sector", sectorObj);
            Log.d("sector", sectorId);
            Log.d("sector", sectorName);
            Industry ind = new Industry(sectorId, sectorName);
            ind.setSectorMain(sectorMain);
            industries.add(ind);
        }

        return industries;
    }

    public static String CheckLoginDetails(String username, String password) throws JSONException {
        String json = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        String returned = PostJsonData("/Login", json);

        // We create our JSONObject from the data
        JSONObject jObj = new JSONObject(returned);

        // Get the rates object from the JSON.

        String serial = getString("serial", jObj);

        return serial;
    }

// new comment
    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static Double getDouble(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getDouble(tagName);
    }
    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
