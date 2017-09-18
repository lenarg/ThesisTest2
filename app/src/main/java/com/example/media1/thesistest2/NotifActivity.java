package com.example.media1.thesistest2;


import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import com.example.media1.thesistest2.model.PlaceStoring;
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

public class NotifActivity  {

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

        private static final String SERVICE_URL = "http://zafora.icte.uowm.gr/~ictest00344/get_json.php";//"http://zafora.icte.uowm.gr/~ictest00344/testjson.php"; //"https://api.myjson.com/bins/4jb09";

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(Void... args) {

            HttpURLConnection conn = null;
            final StringBuilder json = new StringBuilder();
            try {
                // Connect to the web service
                URL url = new URL(SERVICE_URL);
                conn = (HttpURLConnection) url.openConnection();
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

                //latlng tap = ( 40.416425, 21.521270); //point inside namata region (rectangle area)

                for ( int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String type = jsonObj.getString("type");
                    if ( type == "1" || type == "2" ){ // Polygon or Rectangle

                        String coords = jsonObj.getString("coordinates");
                        String[] coordstable = coords.split(";"); //ΕΔΩ ΕΜΕΙΝΑ ΜΠΟΡΕΙ ΝΑ ΧΩ ΚΑΝΕΙ ΚΑΙ ΧΑΖΟΜΑΡΑ, ΝΑ ΤΟ ΞΑΝΑΔΩ
                        String coords1 = coordstable[1];
                        Double lat = Double.parseDouble(coords1);
                        String coords2 = coordstable[2];
                        Double lng = Double.parseDouble(coords2);
                        // put coordinates in vertices array as latlng(double lat, double lng) format
                        // do Ray-Casting
                        //if R-C result is odd then we're in the area, show notification!

                    }else if ( type == "3" ){ //Circle

                        // do Geofencing

                    }

                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    String coords = jsonObj.getString("coordinates");
                    //Log.d("coords", coords);
                    String[] coordstable = coords.split(";");
                    String coords1 = coordstable[1];
                    Log.d("coords", coords1);
                    String coords2 = coordstable[2];
                    Log.d("coords2", coords2);

                    int place_id = jsonObj.getInt("place_id");

                    String type = jsonObj.getString("type");

                    int itype = Integer.parseInt(type);
                    if ( itype == 1 ){
                        //Log.d("msg1", "Polygon!"); //polygon ;lat1,lng1,lat2,lng2..,latn,lngn
                        PolygonOptions polygonOptions = new PolygonOptions();
                        polygonOptions.strokeColor(Color.RED);
                        for (int j = 1; j<coordstable.length; j=j+2){
                            Double plat = Double.parseDouble(coordstable[j]);
                            Double plng = Double.parseDouble(coordstable[j+1]);

                            polygonOptions.add( new LatLng( plat, plng ));
                        }
                        Polygon polygon = mMap.addPolygon(polygonOptions);

                        polygon.setClickable(true);

                    }else if ( itype == 2 ){
                        //Log.d("msg2", "rectangle");//rectangle ;latne;lngne;latsw;lngsw
                        Double latne = Double.parseDouble(coordstable[1]);
                        Double lngne = Double.parseDouble(coordstable[2]);
                        Double latsw = Double.parseDouble(coordstable[3]);
                        Double lngsw = Double.parseDouble(coordstable[4]);

                        Polygon rectangle = mMap.addPolygon(new PolygonOptions()
                                .add( new LatLng(latne, lngsw), new LatLng(latsw, lngsw), new LatLng(latsw, lngne), new LatLng(latne, lngne), new LatLng(latne, lngsw))
                                .strokeColor(Color.RED));

                        rectangle.setClickable(true);
                    }else if ( itype == 3 ){
                        //Log.d("msg3", "circle"); //circle  ;radius;latcen;lngcen
                        Double radius = Double.parseDouble(coordstable[1]);
                        //Log.d("msgr", String.valueOf(radius));
                        Double latcen = Double.parseDouble(coordstable[2]);
                        Double lngcen = Double.parseDouble(coordstable[3]);
                        Circle circle = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(latcen, lngcen))//.center(new LatLng(40.300882, 21.788082)) //coordstable [2],coordstable [3]
                                .radius(radius)//.radius(10000) //coordstable [1]
                                .strokeColor(Color.RED));

                    }else {
                        Log.d("msg4", "Undentified type");
                    }


                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }
        }
    }
}





/**
    latlng tap = ( 40.416425, 21.521270); //point inside namata region (rectangle area)

    for (int i = 0; i < allplaces.length; i++) {
        if ( type(i) == "1" || type(i) == "2" ){ // Polygon or Rectangle

            // put coordinates in vertices array as latlng(double lat, double lng) format
            // do Ray-Casting
            //if R-C result is odd then we're in the area, show notification!

        }else if ( type(i) == "3" ){ //Circle

            // do Geofencing

        }

    }
**/

}
