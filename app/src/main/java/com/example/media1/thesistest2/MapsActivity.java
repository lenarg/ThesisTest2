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

import static com.example.media1.thesistest2.R.id.map;

/* //trying http://stackoverflow.com/questions/29724192/using-json-for-android-maps-api-markers-not-showing-up
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //private static final String LOG_TAG = "ExampleApp";

    //private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";

    protected GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //setUpMapIfNeeded();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            map = mapFragment.getMap(); //mapFragment.getMap();
            if (map != null) {
                //setUpMap();
                new MarkerTask().execute();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        new MarkerTask().execute();

    }

    private class MarkerTask extends AsyncTask<Void, Void, String> { //added by so

        private static final String LOG_TAG = "ExampleApp";

        private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";

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
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    LatLng latLng = new LatLng(jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1));

                    //move CameraPosition on first result
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng).zoom(13).build();

                        map.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                    // Create a marker for each city in the JSON data.
                    map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(jsonObj.getString("name"))
                            .snippet(Integer.toString(jsonObj.getInt("population")))
                            .position(latLng));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    } //till here
}
*/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback { //now

    //private static final String LOG_TAG = "ExampleApp";

    //private static final String SERVICE_URL = "http://zafora.icte.uowm.gr/~ictest00344/testjson.php";//"https://api.myjson.com/bins/4jb09";

    private GoogleMap mMap;
    //private final static String URL = "xyzz"; // trying this http://stackoverflow.com/questions/22650959/how-to-add-and-display-multiple-markers-that-are-parsed-from-a-json-object?rq=1
    //public static final String TAG = "MapsEarthquakeMapActivity";  //

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

        private static final String SERVICE_URL = "http://zafora.icte.uowm.gr/~ictest00344/testjson.php"; //"https://api.myjson.com/bins/4jb09";

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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    LatLng latLng = new LatLng(jsonObj.getJSONArray("coordinates").getDouble(0),
                            jsonObj.getJSONArray("coordinates").getDouble(1));

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
                            .title(jsonObj.getString("name"))//.title(jsonObj.getString("name"))
                            .snippet(jsonObj.getString("description"))//.snippet(Integer.toString(jsonObj.getInt("population")))
                            .position(latLng));


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

        }
    }



    //When the Search button is pressed
    public void onSearch(View view){
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
        // addressTextView.setText("addr");

        Geocoder geocoder2 = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder2.getFromLocation(address.getLatitude(), address.getLongitude(), 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
                }
                addressTextView.setText(strReturnedAddress.toString());
            }
            else {
                addressTextView.setText("No Address returned!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            addressTextView.setText("Cannot get Address!");
        }


    }


    //@Override
    //public void onMapLoaded() {
    //   mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));
    // }


   // public void

    //den xrisimopoieitai mallon pia(?)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Kozani and move the camera and zoom there
        LatLng kozani = new LatLng(40.300882, 21.788082);
        mMap.addMarker(new MarkerOptions().position(kozani).title("Marker in Kozani"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(kozani));
        mMap.setMyLocationEnabled(true);
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

        //Restriction of panning out of the wanted area (Dyt Mak)

        // bounds of the desired area
        /*var allowedBounds = new google.maps.LatLngBounds(
                new google.maps.LatLng(70.33956792419954, 178.01171875),
                new google.maps.LatLng(83.86483689701898, -88.033203125)
        ); */

        //var lastValidCenter = map.getCenter();
        //private LatLng lastValidCenter = mMap.getCameraPosition().target;

       // mMap.setOnCameraChangeListener();
      //  mMap.onC



      /*  google.maps.event.addListener(map, 'center_changed', function() {
            if (allowedBounds.contains(mMap.getCameraPosition().target)) {
                // still within valid bounds, so save the last valid position
                lastValidCenter = mMap.getCameraPosition().target;
                return;
            }

            // not valid anymore => return to last valid position
            mMap.panTo(lastValidCenter);
        }); /*

        //1st way to zoom the camera into my boundaries
        // Set the camera to the greatest possible zoom level that includes the bounds
        // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));

        //LatLngBounds bounds = new LatLngBounds.Builder()
        //      .include(new LatLng(39.821813, 20.784578))
        //    .include(new LatLng(40.944606, 22.181563))
        //  .build();

        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        //2nd way
        // Create a LatLngBounds that includes Australia.
        //private LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(39.821813, 20.784578), new LatLng(40.944606, 22.181563)); //dyt makedonias
        // Set the camera to the greatest possible zoom level that includes the
        // bounds
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));

        /* 3rd way
        try {
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));
        } catch (IllegalStateException e) {
            // layout not yet initialized
            final View mapView = getFragmentManager()
                    .findFragmentById(R.id.map).getView();
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @SuppressWarnings("deprecation")
                            @SuppressLint("NewApi")
                            // We check which build version we are using.
                            @Override
                            public void onGlobalLayout() {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    mapView.getViewTreeObserver()
                                            .removeGlobalOnLayoutListener(this);
                                } else {
                                    mapView.getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));
                            }
                        });
            }
        }  */


    } //now

    //otan dokimaza me to xml
    /** public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
        private GoogleMap mMap;
        private List<PlaceOnMap> placeList;

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            this.placeList = getPlaceList();
        }

        @Override
        public void onMapReady(final GoogleMap googleMap) {
            this.mMap = googleMap;
            addPlaceListMarkersToGoogleMap();
        }

        private void addPlaceListMarkersToGoogleMap() {
            for (final PlaceOnMap place : this.placeList) {
                final LatLong latLong = new LatLong(place.getPlaceLatitude(), place.getPlaceLongitude());
                this.mMap.addMarker(new MarkerOptions().position(latLong).title(place.getPlaceName()));
            }
        }

        private List<PlaceOnMap> getPlaceList() {
            final String xmlString = "<placesp>" +
                    "<placep>" +
                    "  <place_id>1</place_id>" +
                    "  <name>Place1</name>" +
                    "  <description>Place description 1</description>" +
                    "  <coordinates>;40.430224;21.559570</coordinates>" +
                    "</placep>" +
                    "<placep>" +
                    "  <place_id>2</place_id>" +
                    "  <name>Place2</name>" +
                    "  <description>Place description 2</description>" +
                    "  <coordinates>;40.423324;21.062439</coordinates>" +
                    "</placep>" +
                    "<placep>" +
                    "  <place_id>3</place_id>" +
                    "  <name>Place3</name>" +
                    "  <description>Place description 3</description>" +
                    "  <coordinates>;40.266952;21.238220</coordinates>" +
                    "</placep>" +
                    "</placesp>";
            final InputStream xmlStream = getXmlStream(xmlString);
            final PlaceXmlParser parser = new PlaceXmlParser();
            return parser.parsePlacesXml(xmlStream);
        }

        private InputStream getXmlStream(final String xmlString) {
            InputStream xmlStream = null;
            try {
                xmlStream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            } catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return xmlStream;
        }
    } **/

    //dokimazontas me to json
    //1st try
    /**
   @Override
    protected void onPostExecute(String result) {

        // Had to create this hard coded JSON result string to have something to test with.
        result = "[ { " + "\"point\":\"40.259357,21.549875\"," +  "\"name5\":\"Siatista\"}]";

        try {
            JSONArray json = new JSONArray(result);

            for (int i = 0; i < json.length(); i++) {
                JSONObject e = json.getJSONObject(i);
                String point = e.getString("point");

                String[] point2 = point.split(",");
                double lat1 = Double.parseDouble(point2[0]);
                double lng1 = Double.parseDouble(point2[1]);

                mMap.addMarker(new MarkerOptions().title(e.getString("name5")).position(new LatLng(lat1, lng1)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }**/

}/**/ //now
