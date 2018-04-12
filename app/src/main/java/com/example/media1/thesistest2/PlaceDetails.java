package com.example.media1.thesistest2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.example.media1.thesistest2.PlacesActivity.KEY_PID;
import static com.example.media1.thesistest2.PlacesActivity.KEY_NAME;
import static com.example.media1.thesistest2.PlacesActivity.KEY_DESC;
import static com.example.media1.thesistest2.PlacesActivity.KEY_TYPE;
import static com.example.media1.thesistest2.PlacesActivity.KEY_COO;

public class PlaceDetails extends AppCompatActivity {

    //String passedVar = null;
    //private TextView passedView = null;
    String passedVarN = null;
    private TextView passedViewN = null;
    String passedVarD = null;
    private TextView passedViewD = null;

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

        passedVarN = getIntent().getStringExtra(KEY_NAME);
        passedViewN = (TextView)findViewById(R.id.name);
        passedViewN.setText(passedVarN);

        passedVarD = getIntent().getStringExtra(KEY_DESC);
        passedViewD = (TextView)findViewById(R.id.description);
        passedViewD.setText(passedVarD);

    }

}
