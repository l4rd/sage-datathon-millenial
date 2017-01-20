package com.millennial.sageup;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


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



    public static Boolean CheckUserAndSerial(String user, String serial) throws JSONException {
        String endpoint = "/checkSerial/" + user.toLowerCase() + "/" + serial;
        String json = GetJsonData(endpoint);

        // We create our JSONObject from the data
        JSONObject jObj = new JSONObject(json);

        // Get the rates object from the JSON.

        Boolean userExists = Boolean.parseBoolean(getString("userexists", jObj));

        Boolean serialExists = Boolean.parseBoolean(getString("serialexists", jObj));

        if(userExists && serialExists) {
            return true;
        } else {
            return false;
        }

    }

    public static void GetUserMentors(String user, String serial) throws JSONException {
        // todo: make this parse

    }



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
