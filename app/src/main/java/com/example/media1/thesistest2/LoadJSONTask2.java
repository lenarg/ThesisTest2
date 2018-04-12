package com.example.media1.thesistest2;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import  com.example.media1.thesistest2.model.TourStoring;
import  com.example.media1.thesistest2.model.Response2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LoadJSONTask2 extends AsyncTask<String, Void, Response2> {
    public LoadJSONTask2(Listener listener) {

        mListener = listener;
    }

    public interface Listener {

        void onLoaded(List<TourStoring> toursList);

        void onError();
    }

    private Listener mListener;

    @Override
    protected Response2 doInBackground(String... strings) {
        try {

            String stringResponse2 = loadJSON(strings[0]);
            Gson gson = new Gson();

            return gson.fromJson(stringResponse2, Response2.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response2 response2) {

        if (response2 != null) {

            mListener.onLoaded(response2.getTours());

        } else {

            mListener.onError();
        }
    }

    private String loadJSON(String jsonURL) throws IOException {

        URL url = new URL(jsonURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response2 = new StringBuilder();

        while ((line = in.readLine()) != null) {

            response2.append(line);
        }

        in.close();
        return response2.toString();
    }
}
