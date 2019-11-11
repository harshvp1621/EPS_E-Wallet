package com.journaldev.iitbhilaieps;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetJsonResponse {
    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException{
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setRequestProperty("Accept-Encoding","identity");
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
//        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    public static String getBalance(String urlString, String auth_token) throws IOException{
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setRequestProperty("Content-Type","application/json");
        urlConnection.setRequestProperty("Accept-Encoding","identity");
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        JSONObject jsonParam = new JSONObject();
        try{
            jsonParam.put("auth_token",auth_token);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        DataOutputStream data = new DataOutputStream(urlConnection.getOutputStream());

        data.write(jsonParam.toString().getBytes());

        data.flush();

        data.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }
}
