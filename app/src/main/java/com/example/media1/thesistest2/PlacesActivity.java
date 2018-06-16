package com.example.media1.thesistest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

//import com.google.android.gms.location.places.Place;

import java.util.List;
import android.view.View;

//edw dokimazoume populate listview me json

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.media1.thesistest2.model.PlaceStoring;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacesActivity extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    private ListView mListView;

    public static final String URL = "https://zafora.icte.uowm.gr/~ictest00344/get_json.php";

    public List<HashMap<String, String>> mPlacesMapList = new ArrayList<>();
    public static List<HashMap<String, String>> mPlacesMapListS = new ArrayList<>();

    //private static final String KEY_PID = "place_id";
    public static final String KEY_PID = "place_id";
    public static final String KEY_UID = "user_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_TYPE = "type";
    public static final String KEY_COO = "coordinates";
    public static final String KEY_IMG = "pfimage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);
    }

    @Override
    public void onLoaded(List<PlaceStoring> allplacesList) {

        for (PlaceStoring allplaces : allplacesList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_PID, allplaces.getPlace_id());
            map.put(KEY_UID, allplaces.getUser_id());
            map.put(KEY_NAME, allplaces.getName());
            map.put(KEY_DESC, allplaces.getDescription());
            map.put(KEY_TYPE, allplaces.getType());
            map.put(KEY_COO, allplaces.getCoordinates());
            map.put(KEY_IMG, allplaces.getImage());

            mPlacesMapList.add(map);
            mPlacesMapListS.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Toast.makeText(this, mPlacesMapList.get(i).get(KEY_NAME),Toast.LENGTH_LONG).show();
        Intent myIntent2 = new Intent(PlacesActivity.this, PlaceDetails.class);
        //myIntent2.putExtra("Position", i);
        myIntent2.putExtra( KEY_PID, mPlacesMapList.get(i).get(KEY_PID)); //String.valueOf(l));//ID_EXTRA, id
        myIntent2.putExtra( KEY_NAME, mPlacesMapList.get(i).get(KEY_NAME));
        myIntent2.putExtra( KEY_DESC, mPlacesMapList.get(i).get(KEY_DESC));
        myIntent2.putExtra( KEY_TYPE, mPlacesMapList.get(i).get(KEY_TYPE));
        myIntent2.putExtra( KEY_COO, mPlacesMapList.get(i).get(KEY_COO));
        myIntent2.putExtra( KEY_IMG, mPlacesMapList.get(i).get(KEY_IMG));

        //myIntent2.putExtra("key", "value"); //Optional parameters
        //CurrentActivity.this.startActivity(myIntent);
        startActivity(myIntent2);
    }

    private void loadListView() {

        /*ListAdapter adapter = new SimpleAdapter(PlacesActivity.this, mPlacesMapList, R.layout.list_item,
                new String[] { KEY_PID, KEY_UID, KEY_NAME, KEY_DESC, KEY_TYPE, KEY_COO, KEY_IMG },
                new int[] { R.id.place_id,R.id.user_id, R.id.name, R.id.description, R.id.type, R.id.coordinates, R.id.pfimage });*/

        ListAdapter adapter = new SimpleAdapter(PlacesActivity.this, mPlacesMapList, R.layout.list_item,
                new String[] { KEY_NAME },
                new int[] { R.id.name });

        mListView.setAdapter(adapter);

    }
}