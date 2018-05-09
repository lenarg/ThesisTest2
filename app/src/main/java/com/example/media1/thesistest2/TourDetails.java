package com.example.media1.thesistest2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.media1.thesistest2.ToursActivity.KEY_TNAME;
import static com.example.media1.thesistest2.ToursActivity.KEY_TDESC;
import static com.example.media1.thesistest2.ToursActivity.KEY_TSAO;

public class TourDetails extends AppCompatActivity{

    String passedVarTN = null;
    private TextView passedViewTN = null;
    String passedVarTD = null;
    private TextView passedViewTD = null;
    String passedVarTSAO = null;
    private TextView passedViewTSAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        passedVarTN = getIntent().getStringExtra(KEY_TNAME);
        passedViewTN = (TextView)findViewById(R.id.tname);
        passedViewTN.setText(passedVarTN);

        passedVarTD = getIntent().getStringExtra(KEY_TDESC);
        passedViewTD = (TextView)findViewById(R.id.tdescription);
        passedViewTD.setText(passedVarTD);

        passedVarTSAO = getIntent().getStringExtra(KEY_TSAO);

        //new TourDetails.SendHttpRequestTask3().execute();
        //new TourDetails.SendHttpRequestTask4().execute();

    }

    private class SendHttpRequestTask3 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                //URL url = new URL("http://www.catster.com/wp-content/uploads/2017/08/A-fluffy-cat-looking-funny-surprised-or-concerned.jpg");
                //URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/cat.jpg");
                URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/"); //+passedVarI);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d("msg7",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imageView = (ImageView) findViewById(R.id.imgid);
            imageView.setImageBitmap(result);
        }
    }
}
