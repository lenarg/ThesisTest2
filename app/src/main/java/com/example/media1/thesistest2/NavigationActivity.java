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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static com.example.media1.thesistest2.PlaceDetails.KEY_NAME2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_COO2;
import static com.example.media1.thesistest2.PlaceDetails.KEY_TYPE2;
import static com.example.media1.thesistest2.MyLocService.mLastLocation;
import static com.example.media1.thesistest2.MyLocService.MY_ACTION;

import static com.example.media1.thesistest2.MyLocService.KEY_DATAPASSED;

public class NavigationActivity extends AppCompatActivity implements SensorEventListener{

    String passedVarNn = null;
    private TextView passedViewNn = null;
    String passedVarCn = null;
    private TextView passedViewCn = null;
    String passedVarTn = null;
    private TextView passedViewTn = null;
    private TextView fieldBearing = null;
    private ImageView arrow = null;

    Location fakeloc = new Location("");
    Location locCn = new Location("");

    MyReceiver myReceiver;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //
        startService(new Intent(this, MyLocService.class));

        //Intent intent = getIntent();
        //String value = intent.getStringExtra("key");
        //String value = intent.getStringExtra("key");

        passedVarNn = getIntent().getStringExtra(KEY_NAME2);
        passedViewNn = (TextView)findViewById(R.id.nvgtName);
        passedViewNn.setText(passedVarNn);

        passedVarCn = getIntent().getStringExtra(KEY_COO2);
        passedViewCn = (TextView)findViewById(R.id.nvgtDist);

        passedVarTn = getIntent().getStringExtra(KEY_TYPE2);

        arrow = (ImageView) findViewById(R.id.directarrow);
        fieldBearing = (TextView)findViewById(R.id.dirtext);

        //Location fakeloc = new Location("");
        //fakeloc.setLatitude(38.0413); //eleusis
        //fakeloc.setLongitude(23.5418);

        fakeloc.setLatitude(40.5182); //kastoria
        fakeloc.setLongitude(21.2681);
        fakeloc.setAltitude(0);
        //if(mLastLocation != null) {

/**
        final Handler handler = new Handler();
        final long delay = 1 * 60 * 1000; //1min

        while(mLastLocation == null){
            passedViewCn.setText("Wait for location...");
            startService(new Intent(this, MyLocService.class));

                handler.postDelayed(new Runnable(){
                    public void run(){
                        // do your work

                        //if (continueToExecute){
                         //   executeSoemthing();
                        //}
                    }
                }, delay);

        }

        fakeloc = mLastLocation;***/





        //Context context = getApplicationContext();
        //CharSequence text = "fakeloc " + fakeloc;
       // int duration = Toast.LENGTH_LONG;

       // Toast toast = Toast.makeText(context, text, duration);
        //toast.show();


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
        intentFilter.addAction(MyLocService.MY_ACTION);
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
            int datapassed = arg1.getIntExtra("DATAPASSED", 0);

            /*Toast.makeText(NavigationActivity.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + String.valueOf(datapassed),
                    Toast.LENGTH_LONG).show();
//String.valueOf(datapassed),*/
        }

    }

    protected void onResume() {
        super.onResume();
        mLastAccelerometerSet = false;
        mLastMagnetometerSet = false;
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
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
}