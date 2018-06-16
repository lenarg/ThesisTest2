package com.example.media1.thesistest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.example.media1.thesistest2.PlaceDetails.KEY_PID2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_NAME2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_COO2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_TYPE2;

import static com.example.media1.thesistest2.ArrivalActivity.KEY_PID3;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_NAME3;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_COO3;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_TYPE3;
import static com.example.media1.thesistest2.ArrivalActivity.KEY_PLACELIST3;

import static com.example.media1.thesistest2.TourDetails.KEY_NAME5;
import static com.example.media1.thesistest2.TourDetails.KEY_COO5;
import static com.example.media1.thesistest2.TourDetails.KEY_PID5;
import static com.example.media1.thesistest2.TourDetails.KEY_TYPE5;
import static com.example.media1.thesistest2.TourDetails.KEY_PLACELIST5;

//import static com.example.media1.thesistest2.MyLocService.mLastLocation;
//import static com.example.media1.thesistest2.MyLocService.MY_ACTION;

//import static com.example.media1.thesistest2.MyLocService.KEY_DATAPASSED;

public class NavigationActivity extends AppCompatActivity implements SensorEventListener{

    String passedVarPid = null;
    String passedVarNn = null;
    private TextView passedViewNn = null;
    String passedVarCn = null;
    private TextView passedViewCn = null;
    String passedVarTn = null;
    String passedVarPL = null;
    private TextView passedViewTn = null;
    private TextView fieldBearing = null;
    private ImageView arrow = null;

    Location fakeloc = new Location("");
    Location locCn = new Location("");

    MyReceiver myReceiver;

    public static final String KEY_PID7 = "place id";
    public static final String KEY_NAME7 = "place name";
    public static final String KEY_PLACELIST7 = "placelist";

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];

    private String nltp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        startService(new Intent(this, MyLocService.class));

        if ( getIntent().getStringExtra(KEY_NAME3) != null){
            passedVarPid = getIntent().getStringExtra(KEY_PID3);
            passedVarNn = getIntent().getStringExtra(KEY_NAME3); //arrivalactivity
            passedVarCn = getIntent().getStringExtra(KEY_COO3);
            passedVarTn = getIntent().getStringExtra(KEY_TYPE3);
            passedVarPL = getIntent().getStringExtra(KEY_PLACELIST3);
        }else if( getIntent().getStringExtra(KEY_NAME5) != null ){
            passedVarPid = getIntent().getStringExtra(KEY_PID5);
            passedVarNn = getIntent().getStringExtra(KEY_NAME5); //tourdetails
            passedVarCn = getIntent().getStringExtra(KEY_COO5);
            passedVarTn = getIntent().getStringExtra(KEY_TYPE5);
            passedVarPL = getIntent().getStringExtra(KEY_PLACELIST5);
        }else{
            passedVarPid = getIntent().getStringExtra(KEY_PID2);
            passedVarNn = getIntent().getStringExtra(KEY_NAME2); //placedetails
            passedVarCn = getIntent().getStringExtra(KEY_COO2);
            passedVarTn = getIntent().getStringExtra(KEY_TYPE2);
        }
        passedViewNn = (TextView)findViewById(R.id.nvgtName);
        passedViewNn.setText(passedVarNn);

        /*if (passedVarPL != null){
            String[] ltp = passedVarPL.split(";");

            if (ltp.length > 1) {

                String nppid = ltp[1]; //next tour place pid
                //String nltp = "";
                if (ltp.length>2) {

                     nppid = ltp[1]; //next tour place pid
                     for (int i = 2; i < ltp.length; i++) {
                         //nltp[i-1] = ltp[i];
                         nltp = ";" + ltp[i]; //ayto tha perastei sto epomeno navigation ws ta merh pou exoun apomeinei sto tour
                     }
                }else{
                    nltp = null;
                }

            }else{
                nltp = null;
            }

        }*/

        //passedVarCn = getIntent().getStringExtra(KEY_COO2);
        passedViewCn = (TextView)findViewById(R.id.nvgtDist);

        //passedVarTn = getIntent().getStringExtra(KEY_TYPE2);

        arrow = (ImageView) findViewById(R.id.directarrow);
        fieldBearing = (TextView)findViewById(R.id.dirtext);

        //Location fakeloc = new Location("");
        fakeloc.setLatitude(38.0413); //eleusis
        fakeloc.setLongitude(23.5418);

        //fakeloc.setLatitude(40.5182); //kastoria
        //fakeloc.setLongitude(21.2681);
        fakeloc.setAltitude(0);
        //if(mLastLocation != null) {


        String[] coords = passedVarCn.split(";");
        String latc = "0";
        String lngc = "0";
        int nt = Integer.parseInt(passedVarTn);


        if (nt == 3) { //circle
            latc = coords[2]; //center
            lngc = coords[3];

        } else if (nt == 2) { //rec

            double latne = Double.parseDouble(coords[1]);
            double lngne = Double.parseDouble(coords[2]);
            double latsw = Double.parseDouble(coords[3]);
            double lngsw = Double.parseDouble(coords[4]);

            double llatc, llngc;

            llngc = lngsw + ((lngne - lngsw) / 2);
            llatc = latsw + ((latne - latsw) / 2);

            latc = String.valueOf(llatc);
            lngc = String.valueOf(llngc);
        }

        double locCnlat = Double.parseDouble(latc);
        double locCnlng = Double.parseDouble(lngc);

        //Location locCn = new Location("");
        locCn.setLatitude(locCnlat);
        locCn.setLongitude(locCnlng);
        locCn.setAltitude(0);
/*
        Context context = getApplicationContext();
        CharSequence text = "loc " + mLastLocation;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/

        double remdist = locCn.distanceTo(fakeloc);
        DecimalFormat df = new DecimalFormat("#.##");
        String sremdist;
        if (remdist<1000) {
            sremdist = df.format(remdist);
            sremdist =  sremdist + " m";
        }else{
            //remdist = remdist/1000;
            sremdist = df.format(remdist/1000);
            sremdist =  sremdist + " km";
        }

        //sremdist = df.format(remdist/1000);
       // String sremdist = Double.toString(remdist);
        passedViewCn.setText(sremdist);
        //}else{
        //    passedViewCn.setText("Wait for location...");
        //}
        //passedViewCn.setText("fakeloC: "+fakeloc);

        /*gia thn puksida*/
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //ORIENTATION);//
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(NavigationActivity.this,
                MyLocService.class);
        startService(intent);

        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            //String datapassed = arg1.getStringExtra("DATAPASSED");
            //int datapassed = arg1.getIntExtra("DATAPASSED", 0);
            int lat1 = arg1.getIntExtra("lat1", 0);
            int lat2 = arg1.getIntExtra("lat2", 0);
            int lng1 = arg1.getIntExtra("lng1", 0);
            int lng2 = arg1.getIntExtra("lng2", 0);


            //fakeloc.setLatitude(Double.parseDouble(lat1 + "." + lat2));
            //fakeloc.setLongitude(Double.parseDouble(lng1 + "." + lng2));
            //fakeloc.setAltitude(0);

/*
            Toast.makeText(NavigationActivity.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + String.valueOf(datapassed),
                    Toast.LENGTH_LONG).show();*/
            Toast.makeText(NavigationActivity.this,
                    "Triggered by Service!\n"
                            + "lat: " + String.valueOf(lat1)+ "." + String.valueOf(lat2) + " lng: " + String.valueOf(lng1) + "." + String.valueOf(lng2),
                    Toast.LENGTH_LONG).show();
// + "." + String.valueOf(lat2) + " lng: " + String.valueOf(lng1) + "." + String.valueOf(lng2),
        }

    }

    protected void onResume() {
        super.onResume();
        mLastAccelerometerSet = false;
        mLastMagnetometerSet = false;
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        new IsItInTask().execute();

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener( this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    /* gia thn puxida */
    public void onSensorChanged( SensorEvent event ) {

        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            Log.d("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
                    mOrientation[0], mOrientation[1], mOrientation[2]));

            /*Context context = getApplicationContext();
            CharSequence text = "Orientation: " + mOrientation[0] + " " + mOrientation[1] + " " + mOrientation[2];
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/

            float azimuth = (float) Math.toDegrees(mOrientation[0]); // orientation
            azimuth = (azimuth + 360) % 360;


            // If we don't have a Location, we break out
            //if ( LocationObj == null ) return;
            if (fakeloc == null) return;

            //float azimuth = event.values[0]; // mOrientation[0]; //event.values[0];
            float baseAzimuth = azimuth;

            GeomagneticField geoField = new GeomagneticField(Double
                    .valueOf(fakeloc.getLatitude()).floatValue(), Double
                    .valueOf(fakeloc.getLongitude()).floatValue(),
                    Double.valueOf(fakeloc.getAltitude()).floatValue(),
                    System.currentTimeMillis());

            azimuth -= geoField.getDeclination(); // converts magnetic north into true north , azimuth = The angle that you've rotated your phone from true north.

            //Correct the azimuth
            //azimuth = azimuth % 360;

            //This is where we choose to point it
            //float direction = azimuth + LocationObj.bearingTo( destinationObj );


            // Store the bearingTo in the bearTo variable
            float bearTo = fakeloc.bearingTo(locCn); //bearTo = The angle from true north to the destination location from the point we're your currently standing.

            // If the bearTo is smaller than 0, add 360 to get the rotation clockwise.
            if (bearTo < 0) {
                bearTo = bearTo + 360;
            }
            float direction = bearTo - azimuth;
            // If the direction is smaller than 0, add 360 to get the rotation clockwise.
            if (direction < 0) {
                direction = direction + 360;
            }
            rotateImageView(arrow, R.drawable.locationarrowicon, direction); //arrow einai to imageview

/*
        Context context = getApplicationContext();
        CharSequence text = "direction: " + direction + "azimuth: " + azimuth + "baseazimuth: " +baseAzimuth;// + " " + mOrientation[1]  + " " +  mOrientation[2];
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/

            //Set the field
            String bearingText = "N";

            if ((360 >= baseAzimuth && baseAzimuth >= 337.5) || (0 <= baseAzimuth && baseAzimuth <= 22.5))
                bearingText = "N";
            else if (baseAzimuth > 22.5 && baseAzimuth < 67.5) bearingText = "NE";
            else if (baseAzimuth >= 67.5 && baseAzimuth <= 112.5) bearingText = "E";
            else if (baseAzimuth > 112.5 && baseAzimuth < 157.5) bearingText = "SE";
            else if (baseAzimuth >= 157.5 && baseAzimuth <= 202.5) bearingText = "S";
            else if (baseAzimuth > 202.5 && baseAzimuth < 247.5) bearingText = "SW";
            else if (baseAzimuth >= 247.5 && baseAzimuth <= 292.5) bearingText = "W";
            else if (baseAzimuth > 292.5 && baseAzimuth < 337.5) bearingText = "NW";
            else bearingText = "?";

            fieldBearing.setText(bearingText);
        }

    }

    private void rotateImageView(ImageView imageView, int drawable, float rotate ) {

        // Decode the drawable into a bitmap
        Bitmap bitmapOrg = BitmapFactory.decodeResource( getResources(),
                drawable );

        // Get the width/height of the drawable
        DisplayMetrics dm = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = bitmapOrg.getWidth(), height = bitmapOrg.getHeight();

        // Initialize a new Matrix
        Matrix matrix = new Matrix();

        // Decide on how much to rotate
        rotate = rotate % 360;

        // Actually rotate the image
        matrix.postRotate( rotate, width, height );

        // recreate the new Bitmap via a couple conditions
        Bitmap rotatedBitmap = Bitmap.createBitmap( bitmapOrg, 0, 0, width, height, matrix, true );
        //BitmapDrawable bmd = new BitmapDrawable( rotatedBitmap );

        //imageView.setImageBitmap( rotatedBitmap );
        imageView.setImageDrawable(new BitmapDrawable(getResources(), rotatedBitmap));
        imageView.setScaleType( ImageView.ScaleType.CENTER );
    }



    private class IsItInTask extends AsyncTask<Void, Void, String> {

        private static final String LOG_TAG = "ExampleApp";

        private static final String SERVICE_URL = "https://zafora.icte.uowm.gr/~ictest00344/get_json.php";//"http://zafora.icte.uowm.gr/~ictest00344/testjson.php"; //"https://api.myjson.com/bins/4jb09";

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(Void... args) {

            HttpsURLConnection conn = null;
            final StringBuilder json = new StringBuilder();
            try {
                // Connect to the web service
                URL url = new URL(SERVICE_URL);
                conn = (HttpsURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Read the JSON data into the StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    json.append(buff, 0, read);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error connecting to service", e);
                //throw new IOException("Error connecting to service", e); //uncaught
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return json.toString();
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String json) {

            try {

                JSONObject baseJsonResponse = new JSONObject(json); //Convert json String into a JSONObject
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray = baseJsonResponse.getJSONArray("allplaces");//new JSONArray(json);//Extract “allplaces” JSONArray
                //LatLng tap = new LatLng(40.416425,21.521270); //point inside namata region (rectangle area)
                //LatLng tap = new LatLng(40.449148, 21.517980); //point inside vlasti (rec)
                //LatLng tap = new LatLng(40.519648, 21.263543); //point inside rec kastoria 41
                LatLng tap = new LatLng(40.328595, 20.996130); //circle kotyli 28
                //LatLng tap = new LatLng(40.387060, 21.323610); //pol simantro
                //LatLng tap = new LatLng(40.393161, 20.835766);
                //LatLng tap = new LatLng(40.417223, 21.057134); //circle
                //int intersectCount = 0;
                //LatLng tap = new LatLng( 40.203385, 21.444823 ); //kivotos
                //int m = jsonArray.length();
                //Log.d("msg7","lengtharray: "+m);
                //for ( int i = 0; i < jsonArray.length(); i++) {
                    //Log.d("msg7","Its here :" + i );
                    //JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String type = passedVarTn;//jsonObj.getString("type");
                    int itype = Integer.parseInt(type);
                    int place_id = Integer.parseInt(passedVarPid);//jsonObj.getInt("place_id");
                    String coords = passedVarCn;//jsonObj.getString("coordinates");
                    String[] coordstable = coords.split(";");
                    //List<LatLng> vertices = new ArrayList<LatLng>();

                    if ( itype == 1 ||  itype == 2 ){ // Polygon or Rectangle
                        // put coordinates in vertices array as latlng(double lat, double lng) format
                        // do Ray-Casting
                        //if R-C result is odd then we're in the area, show notification!

                        List<LatLng> vertices = new ArrayList<LatLng>();

                        if (itype == 2) {

                            double platx1 = Double.parseDouble(coordstable[1]);
                            double plngy1 = Double.parseDouble(coordstable[2]);
                            double platx2 = Double.parseDouble(coordstable[3]);
                            double plngy2 = Double.parseDouble(coordstable[4]);

                            vertices.add(new LatLng(platx1, plngy1));
                            vertices.add(new LatLng(platx1, plngy2));
                            vertices.add(new LatLng(platx2, plngy2));
                            vertices.add(new LatLng(platx2, plngy1));
                        }else {
                            for (int j = 1; j < coordstable.length; j = j + 2) {

                                double platx = Double.parseDouble(coordstable[j]);
                                double plngy = Double.parseDouble(coordstable[j + 1]);

                                vertices.add(new LatLng(platx, plngy));
                            }
                        }

                        //PolyUtil.containsLocation(userLocation, polyPointsList, false);
                        boolean isitin = PolyUtil.containsLocation( tap, vertices, false);

                        if (isitin == true){
                            Log.d("msg7","its in rec place with ID: " + place_id);
                            Intent myIntent10 = new Intent(NavigationActivity.this, ArrivalActivity.class);
                            myIntent10.putExtra(KEY_PID7,  passedVarPid);
                            myIntent10.putExtra(KEY_NAME7,  passedVarNn);
                            myIntent10.putExtra(KEY_PLACELIST7, passedVarPL); //nltp
                            //myIntent10.putExtra("","");//na fainontai ta koumpia h oxi
                            NavigationActivity.this.startActivity(myIntent10);

                        }

                    }else  if ( itype == 3 ){ //Circle


                        //Double pradius = Double.parseDouble(coordstable[0]);
                        float pradius = Float.parseFloat(coordstable[1]);
                        //Log.d("msg7","radius" + coordstable[1] + pradius);

                        double platcen = Double.parseDouble(coordstable[2]);
                        double plngcen = Double.parseDouble(coordstable[3]);
                        //Log.d("msg7","radius" + coordstable[2] + platcen);
                        //Log.d("msg7","radius" + coordstable[3] +plngcen);

                        LatLng pcenter = new LatLng(platcen, plngcen);

                        float[] distance = new float[2];

                        Location.distanceBetween( tap.latitude, tap.longitude, platcen, plngcen, distance );

                        if( distance[0] < pradius  ){ //itsIN
                            Log.d("msg7","Its IN circle place" + place_id); //with ID: " + place_id);
                            //Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
                            //sendNotification();
                            //ArrivalActivity;
                            Intent myIntent9 = new Intent(NavigationActivity.this, ArrivalActivity.class);
                            myIntent9.putExtra(KEY_PID7,  passedVarPid);
                            myIntent9.putExtra(KEY_NAME7,  passedVarNn);
                            //myIntent9.putExtra(KEY_COO7, ""); //Optional parameters
                            //myIntent9.putExtra(KEY_TYPE7, "");
                            myIntent9.putExtra(KEY_PLACELIST7, passedVarPL);//nltp);
                            myIntent9.putExtra("","");//na fainontai ta koumpia h oxi
                            NavigationActivity.this.startActivity(myIntent9);


                        } else { //its OUT
                            //Toast.makeText(getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
                            //Log.d("msg7","Its OUT");
                        }

                    }

                //}

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }
        }
    }
}