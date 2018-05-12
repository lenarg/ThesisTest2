package com.example.media1.thesistest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.example.media1.thesistest2.model.TourStoring;


public class ToursActivity extends AppCompatActivity implements LoadJSONTask2.Listener, AdapterView.OnItemClickListener {

    private ListView mListView2;

    public static final String URL = "https://zafora.icte.uowm.gr/~ictest00344/get_tjson.php";

    private List<HashMap<String, String>> mToursMapList = new ArrayList<>();

    public static final String KEY_TID = "tour_id";
    public static final String KEY_UID = "user_id";
    public static final String KEY_TNAME = "tour_name";
    public static final String KEY_TDESC = "tour_desc";
    public static final String KEY_TPL = "tour_places";
    //private static final String KEY_COO = "coordinates";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        mListView2 = (ListView) findViewById(R.id.list_view2);
        mListView2.setOnItemClickListener(this);
        new LoadJSONTask2(this).execute(URL);
    }

    @Override
    public void onLoaded(List<TourStoring> toursList) {

        for (TourStoring tours : toursList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_TID, tours.getTour_id());
            map.put(KEY_UID, tours.getUser_id());
            map.put(KEY_TNAME, tours.getTour_name());
            map.put(KEY_TDESC, tours.getTour_desc());
            map.put(KEY_TPL, tours.getTour_places());

            //map.put(KEY_TYPE, allplaces.getType());
            //map.put(KEY_COO, allplaces.getCoordinates());

            mToursMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Toast.makeText(this, mToursMapList.get(i).get(KEY_TNAME),Toast.LENGTH_LONG).show();
        Intent myIntent3 = new Intent(ToursActivity.this, TourDetails.class);
        //myIntent2.putExtra("Position", i);
        myIntent3.putExtra( KEY_TID, mToursMapList.get(i).get(KEY_TID)); //String.valueOf(l));//ID_EXTRA, id
        myIntent3.putExtra( KEY_TNAME, mToursMapList.get(i).get(KEY_TNAME));
        myIntent3.putExtra( KEY_TDESC, mToursMapList.get(i).get(KEY_TDESC));
        myIntent3.putExtra( KEY_TPL, mToursMapList.get(i).get(KEY_TPL));


        //myIntent2.putExtra("key", "value"); //Optional parameters
        //CurrentActivity.this.startActivity(myIntent);
        startActivity(myIntent3);

    }

    private void loadListView() {
/*
        ListAdapter adapter = new SimpleAdapter(ToursActivity.this, mToursMapList, R.layout.list_item2,
                new String[] { KEY_TID, KEY_UID, KEY_TNAME, KEY_TDESC, KEY_TPL },
                new int[] { R.id.tour_id, R.id.user_id, R.id.tour_name, R.id.tour_desc, R.id.tsa_order });
*/

        ListAdapter adapter = new SimpleAdapter(ToursActivity.this, mToursMapList, R.layout.list_item2,
                new String[] { KEY_TNAME },
                new int[] { R.id.tour_name });


        mListView2.setAdapter(adapter);

    }
}