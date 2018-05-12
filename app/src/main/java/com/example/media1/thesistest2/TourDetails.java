package com.example.media1.thesistest2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

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

    private List<HashMap<String, String>> mTPlacesMapList = new ArrayList<>();

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
                }
            }
        }

        loadListView();

    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

        Intent myIntent4 = new Intent(TourDetails.this, PlaceDetails.class);
        myIntent4.putExtra( KEY_PID, mTPlacesMapList.get(i).get(KEY_PID)); //String.valueOf(l));//ID_EXTRA, id
        myIntent4.putExtra( KEY_NAME, mTPlacesMapList.get(i).get(KEY_NAME));
        myIntent4.putExtra( KEY_DESC, mTPlacesMapList.get(i).get(KEY_DESC));
        myIntent4.putExtra( KEY_TYPE, mTPlacesMapList.get(i).get(KEY_TYPE));
        myIntent4.putExtra( KEY_COO, mTPlacesMapList.get(i).get(KEY_COO));
        myIntent4.putExtra( KEY_IMG, mTPlacesMapList.get(i).get(KEY_IMG));

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
