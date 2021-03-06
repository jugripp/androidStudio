package com.example.quiroz.googlemaps2;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by andre.rosa on 30/05/2017.
 */

public class ConnectionPol {

    public List<MarkerPol> getData() throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://api.myjson.com/bins/102ra1");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                result.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        List<MarkerPol> finalResult = generateJSON(new JSONArray(result.toString()));

        return finalResult;
    }

    public List<MarkerPol> generateJSON(JSONArray json){

        List<MarkerPol> found = new LinkedList<MarkerPol>();

        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                found.add(new MarkerPol(obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0),obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1), obj.getJSONObject("properties").getString("name")));
            }
        } catch (JSONException e) {
            // handle exception
        }
        return found;

    }
}
