package com.example.media1.thesistest2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

public class MainMenu extends AppCompatActivity {

    private int mID = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Context context = getApplicationContext();
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("mARs is running")
                .setContentTitle("mARs");

        Intent intent = new Intent(context, MainMenu.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, mID, intent, 0);
        builder.setContentIntent(pIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notif = builder.build();
        mNotificationManager.notify(mID, notif);

    }

    /*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        super.onSaveInstanceState(savedInstanceState);//(outState, outPersistentState);
    }*/

    public void onMapsClick(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        //i.putExtra(KEY_LOC, "");
        startActivity(i);
    }

    public void onNearbyClick(View view) {
        Intent i = new Intent(this, NearbyActivity.class);
        startActivity(i);
    }

    public void onToursClick(View view) {
        Intent i = new Intent(this, ToursActivity.class);
        startActivity(i);
    }

    public void onPlacesClick(View view) {
        Intent i = new Intent(this, PlacesActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Context context = getApplicationContext();
        NotificationManager mNotificationManager2 = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager2.cancel(mID);

        Intent i = new Intent(this, GoogleService.class);
        stopService(i);

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
