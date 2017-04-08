package com.example.media1.thesistest2;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

//import com.google.android.gms.location.places.Place;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        listView = (ListView) findViewById(R.id.list);

        List<Placep> placesp = null;

        try{
            XMLPullParserHandler parser = new XMLPullParserHandler();
            placesp = parser.parse(getAssets().open("placesp.xml"));
            ArrayAdapter<Placep> adapter =
                    new ArrayAdapter<Placep>(this, R.layout.list_item, placesp); //simple_list_item_1

            listView.setAdapter(adapter);

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

}