package com.example.media1.thesistest2;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.media1.thesistest2.PlacesActivity.KEY_COO;
import static com.example.media1.thesistest2.PlacesActivity.KEY_DESC;
import static com.example.media1.thesistest2.PlacesActivity.KEY_IMG;
import static com.example.media1.thesistest2.PlacesActivity.KEY_NAME;
import static com.example.media1.thesistest2.PlacesActivity.KEY_PID;
import static com.example.media1.thesistest2.PlacesActivity.KEY_TYPE;
import static com.example.media1.thesistest2.PlacesActivity.KEY_UID;
import static com.example.media1.thesistest2.ToursActivity.KEY_TNAME;
import static com.example.media1.thesistest2.ToursActivity.KEY_TDESC;
import static com.example.media1.thesistest2.ToursActivity.KEY_TPL;

import com.example.media1.thesistest2.model.PlaceStoring;


public class TourDetails extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    private ListView mListView5;

    public static final String URL = "https://zafora.icte.uowm.gr/~ictest00344/get_json.php";


    String passedVarTN = null;
    private TextView passedViewTN = null;
    String passedVarTD = null;
    private TextView passedViewTD = null;
    String passedVarTPL = null;
    private TextView passedViewTPL = null;
    String eptshow = "";


    Location myloc = new Location("");
    Location locCn = new Location("");

    public List<HashMap<String, String>> mTPlacesMapList = new ArrayList<>();
    public static List<HashMap<String, String>> mTPlacesMapListS = new ArrayList<>();

    public static final String KEY_PID8 = "place id";

    public static final String KEY_PID5 = "place id";
    public static final String KEY_NAME5 = "name";
    public static final String KEY_COO5 = "coordinates";
    public static final String KEY_TYPE5 = "place type";
    public static final String KEY_PLACELIST5 = "sorted list of places";
    String[] sortedPlaceTable;

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

        passedVarTPL = getIntent().getStringExtra(KEY_TPL);
        Log.d("msg7","tour places: " + passedVarTPL);


        //new TourDetails.SendHttpRequestTask3().execute();
        //new TourDetails.SendHttpRequestTask4().execute();

        mListView5 = (ListView) findViewById(R.id.list_view5);
        mListView5.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);


        Button clickGOButton = (Button) findViewById(R.id.tnvgt);
        clickGOButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent8 = new Intent(TourDetails.this, NavigationActivity.class);

                String name5 ="";
                String type5 ="";
                String coordinates5="";

                for (int i =0; i<mTPlacesMapList.size(); i++){
                    //Log.d("msg8", "n " +n);
                    if( Integer.parseInt(mTPlacesMapList.get(i).get(KEY_PID)) ==  Integer.parseInt(sortedPlaceTable[0]) ){

                        name5 = mTPlacesMapList.get(i).get(KEY_NAME);
                        type5 = mTPlacesMapList.get(i).get(KEY_TYPE);
                        coordinates5 = mTPlacesMapList.get(i).get(KEY_COO);

                    }
                }
                myIntent8.putExtra(KEY_PID5, sortedPlaceTable[0]);
                myIntent8.putExtra(KEY_NAME5, name5);
                myIntent8.putExtra(KEY_COO5, coordinates5);
                myIntent8.putExtra(KEY_TYPE5, type5);
                myIntent8.putExtra(KEY_PLACELIST5, eptshow );
                //myIntent8.putExtra(KEY_TPL2, passedVarTPL);
                TourDetails.this.startActivity(myIntent8);

            }
        });
    }

    private String[] sortTourPlaces(int[] spt) {


        int k=0;
        //myloc=lat,lng; //mylocation x=myloc
        myloc.setLatitude(38.0413); //eleusis
        myloc.setLongitude(23.5418);
        int ls = spt.length;
        String[] ept = new String[ls];
        String[][] sv = new String[1][2];
        int j = ls;
        //int le = 0;


        while ( k<ls ){ // not for

            String[][] distable = new String[j][3]; //emptying array
            j--;
            int m =0;

            for(int i=0; i<spt.length; i++){
                if ( spt[i] != 0 ) { //to null egine 0
                    distable[m][0] = Integer.toString(spt[i]);
                    //Log.d("msg8", "distable["+m+"][0]: "+spt[i]);

                    String type ="0";
                    String coordinates ="0";

                    //Log.d("msg8", "mTPlacesMapList.size()"+mTPlacesMapList.size());


                    //Log.d("msg8", "HERE1");
                   // Log.d("msg8", "HERE1"+mTPlacesMapList);

                    //Log.d("msg8", "size " +mTPlacesMapList.size());
                    //for (Map.Entry<String,String> entry : mTPlacesMapList.entrySet()){}
                    for (int n =0; n<mTPlacesMapList.size()  ;n++){
                        //Log.d("msg8", "n " +n);
                        if( Integer.parseInt(mTPlacesMapList.get(n).get(KEY_PID)) == spt[i] ){

                            type = mTPlacesMapList.get(n).get(KEY_TYPE);
                            coordinates = mTPlacesMapList.get(n).get(KEY_COO);

                        }
                    }

                    //finding the center
                    String[] coords = coordinates.split(";");
                    String latc = "0";
                    String lngc = "0";
                    int nt = Integer.parseInt(type);


                    if (nt == 3) { //circle
                        latc = coords[2]; //center
                        lngc = coords[3];

                    } else if (nt == 2) { //rec

                        double latne = Double.parseDouble(coords[1]);
                        double lngne = Double.parseDouble(coords[2]);
                        double latsw = Double.parseDouble(coords[3]);
                        double lngsw = Double.parseDouble(coords[4]);

                        double llatc, llngc;

                        llngc = lngsw + ((lngne - lngsw) / 2);
                        llatc = latsw + ((latne - latsw) / 2);

                        latc = String.valueOf(llatc);
                        lngc = String.valueOf(llngc);
                    }

                    double locCnlat = Double.parseDouble(latc);
                    double locCnlng = Double.parseDouble(lngc);

                    //Location locCn = new Location("");
                    locCn.setLatitude(locCnlat);
                    locCn.setLongitude(locCnlng);

                    distable[m][1]= Double.toString( locCn.distanceTo(myloc) );//distance(spt[i] to x);
                    distable[m][2]= locCn.getLatitude() +";"+locCn.getLongitude();

                    //Log.d("msg8", "distable["+m+"][0]: "+distable[m][0]);
                    //Log.d("msg8", "distable["+m+"][2]: "+distable[m][2]);

                    m++;
                }
            }

            //sv = [ distable[0,0] , distable[0,1] ];
            sv[0][0]= distable[0][0];
            sv[0][1]= distable[0][1];

            for (int i=1;i<distable.length; i++){
                if( Double.parseDouble(distable[i][1]) < Double.parseDouble(sv[0][1]) ){
                    sv[0][0]=distable[i][0];
                    sv[0][1]=distable[i][1];
                }
            }
            ept[k]=sv[0][0];
            //Log.d("msg8", "ept["+k+"]: "+ept[k]);
            k++;
            for(int i=0;i<spt.length;i++){
                if ( spt[i] == Integer.parseInt( sv[0][0] ) ){
                    spt[i]=0;
                }
            }

            //myloc = sv[0,0];
            for (int i=0; i<distable.length; i++) {
                if (distable[i][0] == sv[0][0] ) {
                    String[] coo2 = distable[i][2].split(";");


                    myloc.setLatitude( Double.parseDouble(coo2[0]) );//to kentro aytou me th mikroterh apostash einai to neo x/shmeio ekkinhshs
                    myloc.setLongitude( Double.parseDouble(coo2[1]) );
                }
            }
        }


        return ept;

    }




    @Override
    public void onLoaded(List<PlaceStoring> allplacesList) {

        String[] tplaces = passedVarTPL.split(";");
        for (PlaceStoring allplaces : allplacesList) {

            HashMap<String, String> map = new HashMap<>();

            for ( int i=1; i<tplaces.length; i++ ) {
                //Log.d("msg7","pids " + allplaces.getPlace_id());
                //Log.d("msg7","pidt " + tplaces[i]);
                if ( Integer.parseInt(allplaces.getPlace_id()) == Integer.parseInt(tplaces[i]) ) {
                    Log.d("msg7","IN " );
                    map.put(KEY_PID, allplaces.getPlace_id());
                    map.put(KEY_UID, allplaces.getUser_id());
                    map.put(KEY_NAME, allplaces.getName());
                    map.put(KEY_DESC, allplaces.getDescription());
                    map.put(KEY_TYPE, allplaces.getType());
                    map.put(KEY_COO, allplaces.getCoordinates());
                    map.put(KEY_IMG, allplaces.getImage());

                    mTPlacesMapList.add(map);
                    mTPlacesMapListS.add(map);
                }
            }
        }


        //Log.d("msg8", "size loaded" +mTPlacesMapList.size());
        loadListView();

        String[] tourpltable = passedVarTPL.split(";");
        int[] itourpltable = new int[tourpltable.length-1];//= {1,2};
        String sptshow= "places: ";
        //String eptshow= "";

        for ( int i = 1; i<tourpltable.length ;i++ ){
            itourpltable[i-1] = Integer.parseInt( tourpltable[i] );
            sptshow = sptshow + itourpltable[i-1] + " " ;
        }

        Log.d("msg8", "sptshow" +sptshow);
        sortedPlaceTable = sortTourPlaces(itourpltable);

        for (int i = 1; i< sortedPlaceTable.length; i++ ){
            eptshow = eptshow + ";" + sortedPlaceTable[i];

        }
        Log.d("msg8", "eptshow" +eptshow);



    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

        Intent myIntent4 = new Intent(TourDetails.this, PlaceDetails.class);
        myIntent4.putExtra( KEY_PID8, mTPlacesMapList.get(i).get(KEY_PID)); //String.valueOf(l));//ID_EXTRA, id
        //myIntent4.putExtra( KEY_NAME, mTPlacesMapList.get(i).get(KEY_NAME));
        //myIntent4.putExtra( KEY_DESC, mTPlacesMapList.get(i).get(KEY_DESC));
        //myIntent4.putExtra( KEY_TYPE, mTPlacesMapList.get(i).get(KEY_TYPE));
        //myIntent4.putExtra( KEY_COO, mTPlacesMapList.get(i).get(KEY_COO));
        //myIntent4.putExtra( KEY_IMG, mTPlacesMapList.get(i).get(KEY_IMG));

        //myIntent2.putExtra("key", "value"); //Optional parameters
        //CurrentActivity.this.startActivity(myIntent);
        startActivity(myIntent4);

    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(TourDetails.this, mTPlacesMapList, R.layout.list_item,
                new String[] { KEY_NAME },
                new int[] { R.id.name });


        mListView5.setAdapter(adapter);

    }
}
