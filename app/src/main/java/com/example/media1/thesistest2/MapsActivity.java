package com.example.media1.thesistest2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

//test from so
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.media1.thesistest2.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback { //now

    private GoogleMap mMap;

    // Create a LatLngBounds that includes West Macedonia Perfecture.
    //private LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(39.821813, 20.784578), new LatLng(40.944606, 22.181563)); //center (40.383210, 21.483071)

    private LatLngBounds allowedBounds = new LatLngBounds(
            new LatLng(70.33956792419954, 178.01171875),
            new LatLng(83.86483689701898, -88.033203125)
    );


    //var lastValidCenter = map.getCenter();
   // private LatLng lastValidCenter = mMap.getCameraPosition().target; //get the center coords



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mMap = mapFragment.getMap();//getMapAsync(this);//getMap();
            if (mMap != null) {
                //setUpMap();
                new MarkerTask().execute();
            }
        }
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

            try {

                JSONObject baseJsonResponse = new JSONObject(json); //Convert json String into a JSONObject
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray = baseJsonResponse.getJSONArray("allplaces");//new JSONArray(json);//Extract “allplaces” JSONArray
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    //LatLng latLng = new LatLng(jsonObj.getJSONArray("coordinates").getDouble(0),
                     //       jsonObj.getJSONArray("coordinates").getDouble(1));

                    String coords = jsonObj.getString("coordinates");
                    //Log.d("coords", coords);
                    String[] coordstable = coords.split(";");
                    String coords1 = coordstable[1];
                    Log.d("coords", coords1);
                    String coords2 = coordstable[2];
                    Log.d("coords2", coords2);

                    String type = jsonObj.getString("type");
                    //Log.d("msg1", "I am out!");
                    //Log.d("msgt", type);
                    int itype = Integer.parseInt(type);
                    /*if ( itype == 1 ){
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

                        /*polygon.infowindow = new google.maps.InfoWindow(
                                {
                                        content:"<b>" + c + "</b>" + "</br>",
                                });

                        getMap().setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                            public void onPolygonClick(Polygon polygon) {

                                google.maps.event.addListener(alertPolygon, 'click', function(ev) {
                                    //console.log(ev);
                                    //console.log(alertText);
                                    var infoWindow = new google.maps.InfoWindow({ content: alertText });
                                    infoWindow.setPosition(ev.latLnt);
                                    infoWindow.open(map);
                                });

                            }
                        });*//*

                    }else */if ( itype == 2 ){
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
                                //.clickable(true);

                        //circle.setClickable(true);

                    }else {
                        Log.d("msg4", "Undentified type");
                    }

                    //move CameraPosition on first result
                    if (i == 0) {
                        //LatLng kentro = new LatLng(40.300882, 21.788082); //kozani
                        LatLng kentro = new LatLng(40.368194, 21.385222); //melidoni
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(kentro).zoom(8).build();//.target(latLng).zoom(13).build();

                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }




                /*JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    LatLng latLng = new LatLng(jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1));

                    //move CameraPosition on first result
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng).zoom(13).build();

                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                    // Create a marker for each city in the JSON data.
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(jsonObj.getString("name"))
                            .snippet(Integer.toString(jsonObj.getInt("population")))
                            .position(latLng));*/
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

            /*mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener(){
                //public void onPolygonClick(Polygon polygon){
                 //   Log.d("msgpol", "A polygon was clicked!");
                //}
                public void onPolygonClick(Polygon polygon){
                      Log.d("msgpol", "A polygon or rectangle was clicked!");
                    }
            });*/

        }
    }


    //When the Search button is pressed
    public void onSearch(View view){
        Log.d("mgx","search");
        EditText locationTextField = (EditText)findViewById(R.id.searchTextField);
        String location = locationTextField.getText().toString();
        List<Address> addressList = null;

        //Get the marker on the address the user inputs
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


        //Display the coordinates
        TextView coordinatesTextView = (TextView)findViewById(R.id.coordinatesTextView);
        coordinatesTextView.setText("N " + address.getLatitude() + " E " + address.getLongitude() );

        //Here we get and display the address from the coordinates
        TextView addressTextView = (TextView)findViewById(R.id.addressTextView);
        //addressTextView.setText("addr");

        if (address != null) {
            Address returnedAddress = address;
            String addressName = addressList.get(0).getAddressLine(0);

            addressTextView.setText(addressName);
        }
        else {
            addressTextView.setText("No Address returned!");
        }


    }


    //den xrisimopoieitai mallon pia(?)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*// Add a marker in Kozani and move the camera and zoom there
        LatLng kozani = new LatLng(40.300882, 21.788082);
        mMap.addMarker(new MarkerOptions().position(kozani).title("Marker in Kozani"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(kozani));
        //mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kozani, 9));

        //add a circle in kozani
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(40.300882, 21.788082))
                .radius(10000)
                .strokeColor(Color.RED));
                //.fillColor(Color.BLUE));

        // Add a triangle in kozani
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(40.300882, 21.788082), new LatLng(40.300882, 22.788082), new LatLng(41.300882, 22.788082), new LatLng(40.300882, 21.788082))
                .strokeColor(Color.RED));
                //.fillColor(Color.BLUE));
*/
    }
}
