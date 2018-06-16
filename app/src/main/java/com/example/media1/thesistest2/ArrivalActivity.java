package com.example.media1.thesistest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import static com.example.media1.thesistest2.NavigationActivity.KEY_NAME7;
import static com.example.media1.thesistest2.NavigationActivity.KEY_PID7;
import static com.example.media1.thesistest2.NavigationActivity.KEY_PLACELIST7;

import static com.example.media1.thesistest2.PlacesActivity.KEY_NAME;
import static com.example.media1.thesistest2.PlacesActivity.KEY_COO;
import static com.example.media1.thesistest2.PlacesActivity.KEY_PID;
import static com.example.media1.thesistest2.PlacesActivity.KEY_TYPE;
import static com.example.media1.thesistest2.PlacesActivity.mPlacesMapListS;

import static com.example.media1.thesistest2.TourDetails.mTPlacesMapListS;


public class ArrivalActivity extends AppCompatActivity {

    public static final String KEY_PID3 = "next tour place id";
    public static final String KEY_NAME3 = "next tour place name";
    public static final String KEY_COO3 = "next tour place coordinates";
    public static final String KEY_TYPE3 = "next tour place type";
    public static final String KEY_PLACELIST3 = "remained place tour list";

    public static final String KEY_PID4 = "this place id";
    public static final String KEY_NAME4 = "this place name";
    public static final String KEY_DESC4 = "this place desc";
    public static final String KEY_TYPE4 = "this place type";
    public static final String KEY_COO4 = "this place coordinates";
    public static final String KEY_IMG4 = "this place img";

    String nextpid = null;
    String nplist = null;

    String passedVarN7 = null;
    private TextView passedViewN7 = null;
    String passedVarPid7 = null;
    String passedVarPL7 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        passedVarN7 = getIntent().getStringExtra(KEY_NAME7);

        passedViewN7 = (TextView)findViewById(R.id.arplname);
        passedViewN7.setText(passedVarN7);

        passedVarPid7 = getIntent().getStringExtra(KEY_PID7);
        passedVarPL7 = getIntent().getStringExtra(KEY_PLACELIST7);
        View NextPlaceButton = findViewById(R.id.nexttplace);
        NextPlaceButton.setVisibility(View.GONE);

        View MainMenuButton = findViewById(R.id.mainmenu);
        MainMenuButton.setVisibility(View.VISIBLE);



        if ( passedVarPL7 != null ){
            NextPlaceButton.setVisibility(View.VISIBLE);

            String[] rtplaces = passedVarPL7.split(";");
            nextpid = rtplaces[1];

            for (int j=2; j< rtplaces.length ; j++){
                nplist = nplist + ";" + rtplaces[j];
            }
        }

        Button clickGetDetailsButton = (Button) findViewById(R.id.getdetails);
        clickGetDetailsButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent5 = new Intent(ArrivalActivity.this, PlaceDetails.class);
                myIntent5.putExtra(KEY_PID4, getIntent().getStringExtra(KEY_PID7));

                //myIntent5.putExtra(KEY_NAME4, "");
                //myIntent5.putExtra(KEY_DESC4, "");
                //myIntent5.putExtra(KEY_TYPE4, "");
                //myIntent5.putExtra(KEY_COO4, "");
                //myIntent5.putExtra(KEY_IMG4, "");
                ArrivalActivity.this.startActivity(myIntent5);
            }
        });

        Button clickNextPlaceButton = (Button) findViewById(R.id.nexttplace);
        clickNextPlaceButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent6 = new Intent(ArrivalActivity.this, NavigationActivity.class);

                String npname = null;
                String npcoo = null;
                String nptype = null;
                if ( mPlacesMapListS.size() != 0 ) {
                    for (int i = 0; i < mPlacesMapListS.size(); i++) {
                        if (Integer.parseInt(mPlacesMapListS.get(i).get(KEY_PID)) == Integer.parseInt(nextpid)) {
                            npname = mPlacesMapListS.get(i).get(KEY_NAME);
                            npcoo = mPlacesMapListS.get(i).get(KEY_COO);
                            nptype = mPlacesMapListS.get(i).get(KEY_TYPE);
                        }
                    }
                }else{
                    for (int i = 0; i < mTPlacesMapListS.size(); i++) {
                        if (Integer.parseInt(mTPlacesMapListS.get(i).get(KEY_PID)) == Integer.parseInt(nextpid)) {
                            npname = mTPlacesMapListS.get(i).get(KEY_NAME);
                            npcoo = mTPlacesMapListS.get(i).get(KEY_COO);
                            nptype = mTPlacesMapListS.get(i).get(KEY_TYPE);
                        }
                    }
                }

                myIntent6.putExtra(KEY_PID3,  nextpid);
                myIntent6.putExtra(KEY_NAME3,  npname);
                myIntent6.putExtra(KEY_COO3, npcoo);
                myIntent6.putExtra(KEY_TYPE3, nptype);
                myIntent6.putExtra(KEY_PLACELIST3, nplist);
                ArrivalActivity.this.startActivity(myIntent6);
            }
        });

        Button clickMainMenuButton = (Button) findViewById(R.id.mainmenu);

        //if ( mPlacesMapListS.size() != 0 ) {
            //MainMenuButton.setVisibility(View.GONE);
        //    clickEndTourButton.setText("Main Menu");
        //}

        clickMainMenuButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent7 = new Intent(ArrivalActivity.this, MainMenu.class);
                ArrivalActivity.this.startActivity(myIntent7);
            }
        });
    }

}
