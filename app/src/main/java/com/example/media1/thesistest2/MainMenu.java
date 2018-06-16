package com.example.media1.thesistest2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

//import static com.example.media1.thesistest2.MyLocService.NEW_MESSAGE;

//import static com.example.media1.thesistest2.MyLocService.MY_ACTION;


public class MainMenu extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    TextView tv_latitude, tv_longitude, tv_address,tv_area,tv_locality;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;

    private int mID = 10002;

    //Intent locatorService = null;
    //AlertDialog alertDialog = null;

    //MyReceiver2 myReceiver2;

    //public static final String KEY_LOC = "location";
    //LocationManager myLocationManager;
    //Location myLocation;

    boolean isBound = false;

    //private MyLocService mBoundService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Log.d("msg6", "HERE1");
        //bindMyLocService();
Log.d("msg4", "HEREEE");
        //newMessage messageReceiver = new newMessage();
        //registerReceiver(messageReceiver, new IntentFilter(NEW_MESSAGE));

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





        /*tv_address = (TextView) findViewById(R.id.tv_address);
        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_longitude = (TextView) findViewById(R.id.tv_longitude);
        tv_area = (TextView)findViewById(R.id.tv_area);
        tv_locality = (TextView)findViewById(R.id.tv_locality);*/
        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();


        fn_permission();

        if (boolean_permission) {

            Log.d("msg4", "HEREEE2");
            if (mPref.getString("service", "").matches("")) {
                medit.putString("service", "service").commit();

                Intent intentloc = new Intent(getApplicationContext(), GoogleService.class);
                startService(intentloc);

            } else {
                Toast.makeText(getApplicationContext(), "Service is already running", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
        }

        //fn_permission();

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

    public void onNotifClick(View view) {
        Intent i = new Intent(this, NotifActivity.class);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Context context = getApplicationContext();
        NotificationManager mNotificationManager2 = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager2.cancel(mID);

        super.onDestroy();

        //Context context = getApplicationContext();
        //NotificationManager nManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        //nManager.cancelAll();


    }

    /*3rd try forloc*/

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainMenu.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {


            } else {
                ActivityCompat.requestPermissions(MainMenu.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intentloc) {

            latitude = Double.valueOf(intentloc.getStringExtra("latutide"));
            longitude = Double.valueOf(intentloc.getStringExtra("longitude"));
            Log.d("msg4","here32");
            Log.d("msg4","lat: "+latitude+" lng: "+longitude);
            /*List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);

                tv_area.setText(addresses.get(0).getAdminArea());
                tv_locality.setText(stateName);
                tv_address.setText(countryName);



            } catch (IOException e1) {
                e1.printStackTrace();
            }*/


            /*tv_latitude.setText(latitude+"");
            tv_longitude.setText(longitude+"");
            tv_address.getText();*/


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }



    /*try sevice 1*/
   /* @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver2 = new MainMenu.MyReceiver2();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY_ACTION);
        registerReceiver(myReceiver2, intentFilter);

        //Start our own service
        Intent intent = new Intent(MainMenu.this,
                MyLocService.class);
        startService(intent);

        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver2);
        super.onStop();
    }

    private class MyReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            //String datapassed = arg1.getStringExtra("DATAPASSED");
            //int datapassed = arg1.getIntExtra("DATAPASSED", 0);
            int lat1 = arg1.getIntExtra("lat1", 0);
            //int lat2 = arg1.getIntExtra("lat2", 0);
            //int lng1 = arg1.getIntExtra("lng1", 0);
            //int lng2 = arg1.getIntExtra("lng2", 0);


            //fakeloc.setLatitude(Double.parseDouble(lat1 + "." + lat2));
            //fakeloc.setLongitude(Double.parseDouble(lng1 + "." + lng2));
            //fakeloc.setAltitude(0);

            Log.d("msg6", "HERE3 lat:"+lat1);//+"lng1:"+lng1);*/
/*
            Toast.makeText(NavigationActivity.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + String.valueOf(datapassed),
                    Toast.LENGTH_LONG).show();*/
           /* Toast.makeText(NavigationActivity.this,
                    "Triggered by Service!\n"
                            + "lat: " + String.valueOf(lat1)+ "." + String.valueOf(lat2) + " lng: " + String.valueOf(lng1) + "." + String.valueOf(lng2),
                    Toast.LENGTH_LONG).show();*/
           // + "." + String.valueOf(lat2) + " lng: " + String.valueOf(lng1) + "." + String.valueOf(lng2),
    /*    }

    }

    protected void onResume() {
        super.onResume();


    }

    protected void onPause() {
        super.onPause();

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }*/

    /*tying with service 2*/
    /*private void bindMyLocService() {
        try {
            isBound = getApplicationContext().bindService( new Intent(getApplicationContext(), MyLocService.class), mConnection, BIND_AUTO_CREATE );
            bindService(new Intent(this, MyLocService.class), mConnection, BIND_AUTO_CREATE);
        } catch (SecurityException e) {
            // TODO: handle exception
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((MyLocService.MyBinder)service).getService();
            Log.d(MyLocService.TAG, "activity bound to service");

        }

        public void onServiceDisconnected(ComponentName className) {

            mBoundService = null;
            Log.d(MyLocService.TAG, "activity unbound to service");
        }
    };

    public class newMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(NEW_MESSAGE)) {
                Bundle extra = intent.getExtras();
                String username = extra.getString("yourvalue");

                Log.d("msg6", "username" +username);
            }
        }
    }*/

    /*
    public boolean stopService() {
        if (this.locatorService != null) {
            this.locatorService = null;
        }
        return true;
    }

    public boolean startService() {
        try {
            // this.locatorService= new
            // Intent(FastMainActivity.this,LocatorService.class);
            // startService(this.locatorService);

            FetchCordinates fetchCordinates = new FetchCordinates();
            fetchCordinates.execute();
            return true;
        } catch (Exception error) {
            return false;
        }

    }

    public AlertDialog CreateAlert(String title, String message) {
        AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle(title);

        alert.setMessage(message);

        return alert;

    }

    public class FetchCordinates extends AsyncTask<String, Integer, String> {
        ProgressDialog progDialog = null;

        public double lati = 0.0;
        public double longi = 0.0;

        public LocationManager mLocationManager;
        public VeggsterLocationListener mVeggsterLocationListener;

        @Override
        protected void onPreExecute() {
            mVeggsterLocationListener = new VeggsterLocationListener();
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Context context2 = getApplicationContext();

            if (ActivityCompat.checkSelfPermission(context2, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context2, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0,
                    mVeggsterLocationListener);

            progDialog = new ProgressDialog(MainMenu.this);
            progDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FetchCordinates.this.cancel(true);
                }
            });
            progDialog.setMessage("Loading... \n\nIf your GPS is OFF, please turn it ON");
            progDialog.setIndeterminate(true);
            progDialog.setCancelable(false);
            progDialog.show();

        }

        @Override
        protected void onCancelled(){
            System.out.println("Cancelled by user!");
            progDialog.dismiss();
            mLocationManager.removeUpdates(mVeggsterLocationListener);
        }

        @Override
        protected void onPostExecute(String result) {
            progDialog.dismiss();

            Toast.makeText(MainMenu.this,
                    "LATITUDE :" + lati + " LONGITUDE :" + longi,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            while (this.lati == 0.0) {

            }
            return null;
        }

        public class VeggsterLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {

                int lat = (int) location.getLatitude(); // * 1E6);
                int log = (int) location.getLongitude(); // * 1E6);
                int acc = (int) (location.getAccuracy());

                Log.d("msg6","lat "+ lat+" lng "+log+" acc "+acc);

                        String info = location.getProvider();
                try {

                    // LocatorService.myLatitude=location.getLatitude();

                    // LocatorService.myLongitude=location.getLongitude();

                    lati = location.getLatitude();
                    longi = location.getLongitude();

                    Toast.makeText(MainMenu.this,
                            "LATITUDE2 :" + lati + " LONGITUDE2 :" + longi,
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // progDailog.dismiss();
                    // Toast.makeText(getApplicationContext(),"Unable to get Location"
                    // , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("msg6", "OnProviderDisabled");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("msg6", "onProviderEnabled");
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.d("msg6", "onStatusChanged");

            }

        }

    }*/


}
