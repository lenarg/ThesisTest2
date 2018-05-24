package com.example.media1.thesistest2;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.media1.thesistest2.PlaceDetails.KEY_NAME2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_COO2;

public class NavigationActivity extends AppCompatActivity {

    String passedVarNn = null;
    private TextView passedViewNn = null;
    String passedVarCn = null;
    private TextView passedViewCn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //Intent intent = getIntent();
        //String value = intent.getStringExtra("key");
        //String value = intent.getStringExtra("key");

        passedVarNn = getIntent().getStringExtra(KEY_NAME2);
        passedViewNn = (TextView)findViewById(R.id.nvgtName);
        passedViewNn.setText(passedVarNn);

        passedVarCn = getIntent().getStringExtra(KEY_COO2);
        passedViewCn = (TextView)findViewById(R.id.nvgtDist);

        Location fakeloc = new Location("");
        fakeloc.setLatitude(38.0413); //eleusis
        fakeloc.setLongitude(23.5418);

        //navrw to kentra tou
        //na kanw distanceTo()



        //passedViewN.setText(passedVarN);
    }
}