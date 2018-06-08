package com.example.media1.thesistest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;



public class ArrivalActivity extends AppCompatActivity {

    public static final String KEY_NAME3 = "next tour place name";
    public static final String KEY_COO3 = "next tour place coordinates";
    public static final String KEY_TYPE3 = "next tour place type";

    public static final String KEY_NAME4 = "this place name";
    public static final String KEY_DESC4 = "this place desc";
    public static final String KEY_TYPE4 = "this place type";
    public static final String KEY_COO4 = "this place coordinates";
    public static final String KEY_IMG4 = "this place img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        Button clickGetDetailsButton = (Button) findViewById(R.id.getdetails);
        clickGetDetailsButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent5 = new Intent(ArrivalActivity.this, PlaceDetails.class);
                myIntent5.putExtra(KEY_NAME4, "");
                myIntent5.putExtra(KEY_DESC4, "");
                myIntent5.putExtra(KEY_TYPE4, "");
                myIntent5.putExtra(KEY_COO4, "");
                myIntent5.putExtra(KEY_IMG4, "");
                ArrivalActivity.this.startActivity(myIntent5);
            }
        });

        Button clickNextPlaceButton = (Button) findViewById(R.id.nexttplace);
        clickNextPlaceButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent6 = new Intent(ArrivalActivity.this, NavigationActivity.class);
                myIntent6.putExtra(KEY_NAME3,  "");
                myIntent6.putExtra(KEY_COO3, ""); //Optional parameters
                myIntent6.putExtra(KEY_TYPE3, "");
                ArrivalActivity.this.startActivity(myIntent6);
            }
        });

        Button clickEndTourButton = (Button) findViewById(R.id.endtour);
        clickEndTourButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent7 = new Intent(ArrivalActivity.this, MainMenu.class);
                ArrivalActivity.this.startActivity(myIntent7);
            }
        });
    }

}
