package com.example.media1.thesistest2;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Create a LatLngBounds that includes West Macedonia Perfecture.
    private LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(39.821813, 20.784578), new LatLng(40.944606, 22.181563)); //center (40.383210, 21.483071)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Kozani and move the camera and zoom there
        LatLng kozani = new LatLng(40.300882, 21.788082);
        mMap.addMarker(new MarkerOptions().position(kozani).title("Marker in Kozani"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(kozani));
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kozani, 9));


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


    }



}
