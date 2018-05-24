package com.example.media1.thesistest2;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.media1.thesistest2.model.PlaceStoring;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**public class NotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
    }

}**/



public class NotifActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        //setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new MarkerTask().execute();
        //setUpMapIfNeeded();
    }


    //for marker showing up
    private class MarkerTask extends AsyncTask<Void, Void, String> {

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

            //Log.d("msg7","Its here 1!");

            try {
                //Log.d("msg7","Its here 2!");

                //Log.d("msg7","Its here 4!");

                JSONObject baseJsonResponse = new JSONObject(json); //Convert json String into a JSONObject
                // De-serialize the JSON string into an array of city objects
                //Log.d("msg7","Its here 4!");
                JSONArray jsonArray = baseJsonResponse.getJSONArray("allplaces");//new JSONArray(json);//Extract “allplaces” JSONArray
                //Log.d("msg7","Its here 5!");
                //LatLng tap = new LatLng(40.416425,21.521270); //point inside namata region (rectangle area)
                //LatLng tap = new LatLng(40.449148, 21.517980); //point inside vlasti (rec)
                //LatLng tap = new LatLng(40.328595, 20.996130); //circle kotyli
                LatLng tap = new LatLng(40.387060, 21.323610); //pol simantro
                //LatLng tap = new LatLng(40.393161, 20.835766);
                //LatLng tap = new LatLng(40.417223, 21.057134); //circle
                //int intersectCount = 0;
                //LatLng tap = new LatLng( 40.203385, 21.444823 ); //kivotos
                //int m = jsonArray.length();
                //Log.d("msg7","lengtharray: "+m);
                for ( int i = 0; i < jsonArray.length(); i++) {
                    //Log.d("msg7","Its here :" + i );
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String type = jsonObj.getString("type");
                    int itype = Integer.parseInt(type);
                    int place_id = jsonObj.getInt("place_id");
                    String coords = jsonObj.getString("coordinates");
                    String[] coordstable = coords.split(";");
                    //List<LatLng> vertices = new ArrayList<LatLng>();

                    if ( itype == 1 ||  itype == 2 ){ // Polygon or Rectangle

                        //Log.d("msg7","Rec and Pols");

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

                        /*
                        int intersectCount = 0;
                        for (int j = 0; j < vertices.size() - 1; j++) {
                            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                                    intersectCount++;
                            }
                        }

                        if ( (intersectCount % 2) == 1 ) { //odd = inside


                            //Show notification
                            Log.d("msg7","Its IN place with ID: " + place_id);

                        }else{ //even = outside

                            //do nothing
                            //Log.d("msg7","its OUT");

                        }*/
                        //PolyUtil.containsLocation(userLocation, polyPointsList, false);
                        boolean isitin = PolyUtil.containsLocation( tap, vertices, false);

                        if (isitin == true){
                            Log.d("msg7","its in rec place with ID: " + place_id);

                        }

                    }else  if ( itype == 3 ){ //Circle

                        //Log.d("msg7","Circle");

                        // do Geofencing
                        //function pointInCircle(point, radius, center)
                        //{
                        //    return (google.maps.geometry.spherical.computeDistanceBetween(point, center) <= radius)
                        //}
                        //;radius;latcen,lngcen

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
                        //Log.d("msg7","center" + pcenter);
                        //Log.d("msg7","radius" + pradius);
                        //Log.d("msg7","distance" + distance[0]);

                        if( distance[0] < pradius  ){ //itsIN
                            Log.d("msg7","Its IN circle place" + place_id); //with ID: " + place_id);
                            //Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
                            sendNotification();


                        } else { //its OUT
                            //Toast.makeText(getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
                            //Log.d("msg7","Its OUT");
                        }

                    }

                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }
        }
    }

    public void sendNotification() {

        Log.d("msg7","here1");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }


/*
    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }*/
}