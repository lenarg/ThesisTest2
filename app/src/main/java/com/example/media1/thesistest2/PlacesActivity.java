package com.example.media1.thesistest2;

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

    private List<HashMap<String, String>> mPlacesMapList = new ArrayList<>();

    private static final String KEY_PID = "place_id";
    private static final String KEY_UID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_TYPE = "type";
    private static final String KEY_COO = "coordinates";


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

            mPlacesMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, mPlacesMapList.get(i).get(KEY_NAME),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(PlacesActivity.this, mPlacesMapList, R.layout.list_item,
                new String[] { KEY_PID, KEY_UID, KEY_NAME, KEY_DESC, KEY_TYPE, KEY_COO },
                new int[] { R.id.place_id,R.id.user_id, R.id.name, R.id.description, R.id.type, R.id.coordinates });

        mListView.setAdapter(adapter);

    }
}