package com.example.media1.thesistest2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.lang.Math;
import java.util.HashMap;
import java.util.List;

import static com.example.media1.thesistest2.PlacesActivity.mPlacesMapListS;
import static com.example.media1.thesistest2.TourDetails.KEY_PID8;
import static com.example.media1.thesistest2.TourDetails.mTPlacesMapListS;

import static com.example.media1.thesistest2.PlacesActivity.KEY_PID;
import static com.example.media1.thesistest2.PlacesActivity.KEY_NAME;
import static com.example.media1.thesistest2.PlacesActivity.KEY_DESC;
import static com.example.media1.thesistest2.PlacesActivity.KEY_TYPE;
import static com.example.media1.thesistest2.PlacesActivity.KEY_COO;
import static com.example.media1.thesistest2.PlacesActivity.KEY_IMG;

import static com.example.media1.thesistest2.ArrivalActivity.KEY_PID4;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_NAME4;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_DESC4;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_TYPE4;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_COO4;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_IMG4;

import static com.example.media1.thesistest2.NearbyActivity.mPlacesMapListS2;
import static com.example.media1.thesistest2.NearbyActivity.KEY_PID9;


public class PlaceDetails extends AppCompatActivity {

    //String passedVar = null;
    //private TextView passedView = null;
    String passedVarPid = null;
    String passedVarN = null;
    private TextView passedViewN = null;
    String passedVarD = null;
    private TextView passedViewD = null;
    String passedVarI = null;
    private TextView passedViewI = null;
    String passedVarC = null;
    //private TextView passedViewC = null;
    String passedVarT = null;
    //private TextView passedViewC = null;

    public static final String KEY_PID2 = "place id";
    public static final String KEY_NAME2 = "name";
    public static final String KEY_COO2 = "coordinates";
    public static final String KEY_TYPE2 = "place type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Get our passed variable from our intent's EXTRAS
        //passedVar = getIntent().getStringExtra(KEY_PID);
        //find out text view
        //passedView = (TextView)findViewById(R.id.passed);
        //display our passed variable
        //Log.d("msg7","HERE");
        //passedView.setText("YOU CLICKED ITEM ID="+passedVar);

        View GOButton = findViewById(R.id.nvgtbtn);


        if (getIntent().getStringExtra(KEY_PID4) != null) {
            passedVarPid = getIntent().getStringExtra(KEY_PID4);
            //passedVarN = getIntent().getStringExtra(KEY_NAME4); //arrival
            //passedVarD = getIntent().getStringExtra(KEY_DESC4);
            //passedVarI = getIntent().getStringExtra(KEY_IMG4);
            //passedVarC = getIntent().getStringExtra(KEY_COO4);
            //passedVarT = getIntent().getStringExtra(KEY_TYPE4);
            GOButton.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra(KEY_PID8) != null) {
            passedVarPid = getIntent().getStringExtra(KEY_PID8);  //tourdetails
            GOButton.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra(KEY_PID9) != null) {
            passedVarPid = getIntent().getStringExtra(KEY_PID9);  //nearbyactivity
            GOButton.setVisibility(View.VISIBLE);
        } else {
            passedVarPid = getIntent().getStringExtra(KEY_PID);
            //passedVarN = getIntent().getStringExtra(KEY_NAME); //placesactivity
            //passedVarD = getIntent().getStringExtra(KEY_DESC);
            //passedVarI = getIntent().getStringExtra(KEY_IMG);
            //passedVarC = getIntent().getStringExtra(KEY_COO);
            //passedVarT = getIntent().getStringExtra(KEY_TYPE);
            GOButton.setVisibility(View.VISIBLE);

        }

        if (mPlacesMapListS.size() != 0) {
            for (int i = 0; i < mPlacesMapListS.size(); i++) {

                if (Integer.parseInt(mPlacesMapListS.get(i).get(KEY_PID)) == Integer.parseInt(passedVarPid)) {

                    //type = mTPlacesMapList.get(n).get(KEY_TYPE);
                    //coordinates = mTPlacesMapList.get(n).get(KEY_COO);
                    passedVarN = mPlacesMapListS.get(i).get(KEY_NAME); //placesactivity
                    passedVarD = mPlacesMapListS.get(i).get(KEY_DESC);
                    passedVarI = mPlacesMapListS.get(i).get(KEY_IMG);
                    passedVarC = mPlacesMapListS.get(i).get(KEY_COO);
                    passedVarT = mPlacesMapListS.get(i).get(KEY_TYPE);

                }

            }
        }else if(mPlacesMapListS2.size() != 0){
            for (int i = 0; i < mPlacesMapListS2.size(); i++) {

                if (Integer.parseInt(mPlacesMapListS2.get(i).get(KEY_PID)) == Integer.parseInt(passedVarPid)) {

                    //type = mTPlacesMapList.get(n).get(KEY_TYPE);
                    //coordinates = mTPlacesMapList.get(n).get(KEY_COO);
                    passedVarN = mPlacesMapListS2.get(i).get(KEY_NAME); //placesactivity
                    passedVarD = mPlacesMapListS2.get(i).get(KEY_DESC);
                    passedVarI = mPlacesMapListS2.get(i).get(KEY_IMG);
                    passedVarC = mPlacesMapListS2.get(i).get(KEY_COO);
                    passedVarT = mPlacesMapListS2.get(i).get(KEY_TYPE);

                }

            }
        }else{
            for (int i = 0; i < mTPlacesMapListS.size(); i++) {

                if (Integer.parseInt(mTPlacesMapListS.get(i).get(KEY_PID)) == Integer.parseInt(passedVarPid)) {

                    //type = mTPlacesMapList.get(n).get(KEY_TYPE);
                    //coordinates = mTPlacesMapList.get(n).get(KEY_COO);
                    passedVarN = mTPlacesMapListS.get(i).get(KEY_NAME); //placesactivity
                    passedVarD = mTPlacesMapListS.get(i).get(KEY_DESC);
                    passedVarI = mTPlacesMapListS.get(i).get(KEY_IMG);
                    passedVarC = mTPlacesMapListS.get(i).get(KEY_COO);
                    passedVarT = mTPlacesMapListS.get(i).get(KEY_TYPE);

                }

            }
        }

        //passedVarN = getIntent().getStringExtra(KEY_NAME);
        passedViewN = (TextView)findViewById(R.id.name);
        passedViewN.setText(passedVarN);

        //passedVarD = getIntent().getStringExtra(KEY_DESC);
        passedViewD = (TextView)findViewById(R.id.description);
        passedViewD.setText(passedVarD);


        View imageplace = findViewById(R.id.imgid);
        imageplace.setVisibility(View.VISIBLE);
        if (passedVarI == null){
            imageplace.setVisibility(View.GONE);
        }


        //passedVarI = getIntent().getStringExtra(KEY_IMG);
        //passedViewI = (TextView)findViewById(R.id.pimg);
        //passedViewI.setText(passedVarI);

        //passedVarM = getIntent().getStringExtra(KEY_MAP);

        //passedVarC = getIntent().getStringExtra(KEY_COO);
        //passedVarT = getIntent().getStringExtra(KEY_TYPE);

        new SendHttpRequestTask().execute();
        new SendHttpRequestTask2().execute();



        Button clickGOButton = (Button) findViewById(R.id.nvgtbtn);
        clickGOButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent4 = new Intent(PlaceDetails.this, NavigationActivity.class);
                myIntent4.putExtra(KEY_PID2, passedVarPid);
                myIntent4.putExtra(KEY_NAME2, passedVarN);
                myIntent4.putExtra(KEY_COO2, passedVarC); //Optional parameters
                myIntent4.putExtra(KEY_TYPE2, passedVarT);
                PlaceDetails.this.startActivity(myIntent4);

            }
        });

    }



    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                //URL url = new URL("http://www.catster.com/wp-content/uploads/2017/08/A-fluffy-cat-looking-funny-surprised-or-concerned.jpg");
                //URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/cat.jpg");
                URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/"+passedVarI);

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

    private class SendHttpRequestTask2 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                //URL url = new URL("http://www.catster.com/wp-content/uploads/2017/08/A-fluffy-cat-looking-funny-surprised-or-concerned.jpg");
                //URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/cat.jpg");
                //URL url = new URL("https://zafora.icte.uowm.gr/~ictest00344/placeimgs/"+passedVarI);
                //URL urlm = new URL("http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=15&size=200x200&sensor=false");

                String latc;
                String lngc;
                //Log.d("msg7","3=" +passedVarT );
                //Log.d("msg7","3");
                int pVTi = Integer.parseInt(passedVarT);
                String[] coordst = passedVarC.split(";");
                String patharea="&path=color:0xff0000ff|weight:3";

                String coordpStr = "";


                if ( pVTi == 3 ){ //circle
                    Log.d("msg7", "IN 3");

                    String radius = coordst[1]; //radius
                    latc = coordst[2]; //cenlat

                    lngc = coordst[3]; //cenlng

                    double ER = 6371; //earth radius
                    double latc2,lngc2,d;

                    latc2 = ( Double.parseDouble(latc) * Math.PI ) / 180;
                    lngc2 = ( Double.parseDouble(lngc) * Math.PI ) / 180;
                    double kmradius = Double.parseDouble(radius)/1000;
                    d = kmradius / ER;

                    int i = 0;
                    int j = 0;
                    Log.d("msg7", "coordpStr: " +coordpStr );

                    //generating lat,lng points that belong to the circle
                    for ( i=0; i<=360; i+=8 ) {
                        double brng = i * Math.PI / 180;

                        double plat = Math.asin(Math.sin(latc2) * Math.cos(d) + Math.cos(latc2) * Math.sin(d) * Math.cos(brng));
                        double plng = ((lngc2 + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(latc2), Math.cos(d) - Math.sin(latc2) * Math.sin(plat))) * 180) / Math.PI;
                        plat = (plat * 180) / Math.PI;

                        String splat = Double.toString(plat);
                        String splng = Double.toString(plng);

                        coordpStr = coordpStr + "|" + splat + "," + splng ;

                    }

                    Log.d("msg7", "tcoordpStr: " +coordpStr );

                } else if( pVTi == 2 ){ //rectangle
                    Log.d("msg7", "IN 2");

                    //String cenCoo = new FindRecCenter(passedVarC);

                    String latne = coordst[1];
                    String lngne = coordst[2];
                    String latsw = coordst[3];
                    String lngsw = coordst[4];
                    double llatne = Double.parseDouble(latne);
                    double llngne = Double.parseDouble(lngne);
                    double llatsw = Double.parseDouble(latsw);
                    double llngsw = Double.parseDouble(lngsw);

                    double llatc,llngc;

                    llngc = llngsw + ((llngne -llngsw ) / 2);
                    llatc = llatsw + ((llatne - llatsw) / 2);

                    latc = String.valueOf(llatc);
                    lngc = String.valueOf(llngc);

                    coordpStr = "|" + latne + "," + lngsw + "|" + latne + "," + lngne + "|" + latsw + "," + lngne + "|" + latsw + "," + lngsw + "|" + latne + "," + lngsw ;

                    //URL url = new URL("http://maps.google.com/maps/api/staticmap?center=" + latc + "," + lngc + "&path=color:0xff0000ff|weight:3|" + latne + "," + lngsw + "|" + latne + "," + lngne + "|" + latsw + "," + lngne + "|" + latsw + "," + lngsw + "|" + latne + "," + lngsw + "&size=200x200&sensor=false");

                } else {
                    latc = "0";
                    lngc = "0";
                }

                String patht = patharea + coordpStr ;

                //&zoom=12
                URL url = new URL("http://maps.google.com/maps/api/staticmap?center=" + latc + "," + lngc + patht + "&size=200x200&sensor=false");

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
            ImageView imageView = (ImageView) findViewById(R.id.mapimg);
            imageView.setImageBitmap(result);
        }


    }

}
