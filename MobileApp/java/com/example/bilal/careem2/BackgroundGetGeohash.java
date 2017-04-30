package com.example.bilal.careem2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bilal on 4/30/2017.
 */

public class BackgroundGetGeohash extends AsyncTask<String,Void,String> {

    private double tlat,tlong;
    private String georesponse="";
    public BackgroundGetGeohash(double tlat1,double tlong1)
    {
           this.tlat = tlat1;
           this.tlong = tlong1;
    }
    public String getGeohash()
    {
        this.execute(String.valueOf(tlat),String.valueOf(tlong));
        return georesponse;
    }
    @Override
    protected String doInBackground(String... params) {
        String response ="";
            try
            {
                URL url = new URL("http://192.168.8.101:8081/encodeGeohash?lat="+params[0]+"&lon="+params[1]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
//                Log.i("billal ->", response + "");
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject ;
        try
        {
            jsonObject = new JSONObject(s);
            georesponse = jsonObject.optString("GeoHash");
            Log.d("billal -> ",georesponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
