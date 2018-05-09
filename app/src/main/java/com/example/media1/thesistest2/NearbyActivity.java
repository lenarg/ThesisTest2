package com.example.media1.thesistest2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.media1.thesistest2.model.PlaceStoring;


//public class NearbyActivity extends AppCompatActivity {
public class NearbyActivity extends AppCompatActivity implements LocationListener, LoadJSONTask.Listener, AdapterView.OnItemClickListener {
    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;

    private ListView mListView;

    public static final String URL = "https://zafora.icte.uowm.gr/~ictest00344/get_json.php";

    private List<HashMap<String, String>> mPlacesMapList = new ArrayList<>();

    public static final String KEY_PID = "place_id";
    public static final String KEY_UID = "user_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_TYPE = "type";
    public static final String KEY_COO = "coordinates";
    public static final String KEY_IMG = "pfimage";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);


        //latitudeField.setText(String.valueOf(lat));
        //longitudeField.setText(String.valueOf(lng));


        // Initialize the location fields
        if (location != null) {
            double lat5 = (location.getLatitude()); //ayta einai  h topothesia mou
            double lng5 = (location.getLongitude());
            Log.d("msg7", "HERE lat: " + lat5 + " lng: " + lng5);

            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.d("msg7", "Location not available");
        }

        mListView = (ListView) findViewById(R.id.list_view4);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (location.getLatitude()); //ayta einai  h topothesia mou
        double lng = (location.getLongitude());
        //latitudeField.setText(String.valueOf(lat));
        //longitudeField.setText(String.valueOf(lng));
        Log.d("msg7", "now HERE lat: " + lat + " lng: " + lng);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaded(List<PlaceStoring> allplacesList) {

        Collections.sort(allplacesList, new SortbyDist());

        for (PlaceStoring allplaces : allplacesList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_PID, allplaces.getPlace_id());
            map.put(KEY_UID, allplaces.getUser_id());
            map.put(KEY_NAME, allplaces.getName());
            map.put(KEY_DESC, allplaces.getDescription());
            map.put(KEY_TYPE, allplaces.getType());
            map.put(KEY_COO, allplaces.getCoordinates());
            map.put(KEY_IMG, allplaces.getImage());

            mPlacesMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Toast.makeText(this, mPlacesMapList.get(i).get(KEY_NAME),Toast.LENGTH_LONG).show();
        Intent myIntent2 = new Intent(NearbyActivity.this, PlaceDetails.class);
        //myIntent2.putExtra("Position", i);
        myIntent2.putExtra(KEY_PID, mPlacesMapList.get(i).get(KEY_PID)); //String.valueOf(l));//ID_EXTRA, id
        myIntent2.putExtra(KEY_NAME, mPlacesMapList.get(i).get(KEY_NAME));
        myIntent2.putExtra(KEY_DESC, mPlacesMapList.get(i).get(KEY_DESC));
        myIntent2.putExtra(KEY_TYPE, mPlacesMapList.get(i).get(KEY_TYPE));
        myIntent2.putExtra(KEY_COO, mPlacesMapList.get(i).get(KEY_COO));
        myIntent2.putExtra(KEY_IMG, mPlacesMapList.get(i).get(KEY_IMG));

        //myIntent2.putExtra("key", "value"); //Optional parameters
        //CurrentActivity.this.startActivity(myIntent);
        startActivity(myIntent2);
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(NearbyActivity.this, mPlacesMapList, R.layout.list_item,
                new String[]{KEY_NAME},
                new int[]{R.id.name});

        mListView.setAdapter(adapter);

    }


    class SortbyDist extends Context implements Comparator<PlaceStoring> {
        // Used for sorting in ascending order of
        // distance

        public int compare(PlaceStoring a, PlaceStoring b) {

            String acoo = a.getCoordinates();
            String bcoo = b.getCoordinates();
            String[] coordsa = acoo.split(";");
            String[] coordsb = bcoo.split(";");
            String latca = "0";
            String lngca = "0";
            String latcb = "0";
            String lngcb = "0";

            int at = Integer.parseInt(a.getType());
            int bt = Integer.parseInt(b.getType());

            if (at == 3) { //circle
                latca = coordsa[2]; //center
                lngca = coordsa[3];

            } else if (at == 2) { //rec
                String latnea = coordsa[1];
                String lngnea = coordsa[2];
                String latswa = coordsa[3];
                String lngswa = coordsa[4];
                double llatnea = Double.parseDouble(latnea);
                double llngnea = Double.parseDouble(lngnea);
                double llatswa = Double.parseDouble(latswa);
                double llngswa = Double.parseDouble(lngswa);

                double llatca, llngca;

                llngca = llngswa + ((llngnea - llngswa) / 2);
                llatca = llatswa + ((llatnea - llatswa) / 2);

                latca = String.valueOf(llatca);
                lngca = String.valueOf(llngca);
            }
            if (bt == 3) { //circle
                latcb = coordsb[2]; //center
                lngcb = coordsb[3];

            } else if (bt == 2) { //rec
                String latneb = coordsb[1];
                String lngneb = coordsb[2];
                String latswb = coordsb[3];
                String lngswb = coordsb[4];
                double llatneb = Double.parseDouble(latneb);
                double llngneb = Double.parseDouble(lngneb);
                double llatswb = Double.parseDouble(latswb);
                double llngswb = Double.parseDouble(lngswb);

                double llatcb, llngcb;

                llngcb = llngswb + ((llngneb - llngswb) / 2);
                llatcb = llatswb + ((llatneb - llatswb) / 2);

                latcb = String.valueOf(llatcb);
                lngcb = String.valueOf(llngcb);

            }
            Location loca = new Location(LocationManager.GPS_PROVIDER);
            Location locb = new Location(LocationManager.GPS_PROVIDER);

            double latcad = Double.parseDouble(latca);
            double lngcad = Double.parseDouble(lngca);
            double latcbd = Double.parseDouble(latcb);
            double lngcbd = Double.parseDouble(lngcb);
            loca.setLatitude(latcad);
            loca.setLongitude(lngcad);
            locb.setLatitude(latcbd);
            locb.setLongitude(lngcbd);

            //Location location2 = new Location("");
            //location2.setLatitude(38.0546);
            //location2.setLongitude(23.5318);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(NearbyActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return 0;
            }else {
                Location location2 = locationManager.getLastKnownLocation(provider);
                double dista = loca.distanceTo(location2);
                double distb = locb.distanceTo(location2);
                return Double.compare(dista, distb);
                //return dista - distb;

                //latca lngca latcb lngcb

                //return a.rollno - b.rollno;
            }
        }


        @Override
        public AssetManager getAssets() {
            return null;
        }

        @Override
        public Resources getResources() {
            return null;
        }

        @Override
        public PackageManager getPackageManager() {
            return null;
        }

        @Override
        public ContentResolver getContentResolver() {
            return null;
        }

        @Override
        public Looper getMainLooper() {
            return null;
        }

        @Override
        public Context getApplicationContext() {
            return null;
        }

        @Override
        public void setTheme(int resid) {

        }

        @Override
        public Resources.Theme getTheme() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }

        @Override
        public String getPackageName() {
            return null;
        }

        @Override
        public ApplicationInfo getApplicationInfo() {
            return null;
        }

        @Override
        public String getPackageResourcePath() {
            return null;
        }

        @Override
        public String getPackageCodePath() {
            return null;
        }

        @Override
        public SharedPreferences getSharedPreferences(String name, int mode) {
            return null;
        }

        @Override
        public FileInputStream openFileInput(String name) throws FileNotFoundException {
            return null;
        }

        @Override
        public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
            return null;
        }

        @Override
        public boolean deleteFile(String name) {
            return false;
        }

        @Override
        public File getFileStreamPath(String name) {
            return null;
        }

        @Override
        public File getFilesDir() {
            return null;
        }

        @Override
        public File getNoBackupFilesDir() {
            return null;
        }

        @Nullable
        @Override
        public File getExternalFilesDir(@Nullable String type) {
            return null;
        }

        @Override
        public File[] getExternalFilesDirs(String type) {
            return new File[0];
        }

        @Override
        public File getObbDir() {
            return null;
        }

        @Override
        public File[] getObbDirs() {
            return new File[0];
        }

        @Override
        public File getCacheDir() {
            return null;
        }

        @Override
        public File getCodeCacheDir() {
            return null;
        }

        @Nullable
        @Override
        public File getExternalCacheDir() {
            return null;
        }

        @Override
        public File[] getExternalCacheDirs() {
            return new File[0];
        }

        @Override
        public File[] getExternalMediaDirs() {
            return new File[0];
        }

        @Override
        public String[] fileList() {
            return new String[0];
        }

        @Override
        public File getDir(String name, int mode) {
            return null;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return null;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
            return null;
        }

        @Override
        public boolean deleteDatabase(String name) {
            return false;
        }

        @Override
        public File getDatabasePath(String name) {
            return null;
        }

        @Override
        public String[] databaseList() {
            return new String[0];
        }

        @Override
        public Drawable getWallpaper() {
            return null;
        }

        @Override
        public Drawable peekWallpaper() {
            return null;
        }

        @Override
        public int getWallpaperDesiredMinimumWidth() {
            return 0;
        }

        @Override
        public int getWallpaperDesiredMinimumHeight() {
            return 0;
        }

        @Override
        public void setWallpaper(Bitmap bitmap) throws IOException {

        }

        @Override
        public void setWallpaper(InputStream data) throws IOException {

        }

        @Override
        public void clearWallpaper() throws IOException {

        }

        @Override
        public void startActivity(Intent intent) {

        }

        @Override
        public void startActivity(Intent intent, @Nullable Bundle options) {

        }

        @Override
        public void startActivities(Intent[] intents) {

        }

        @Override
        public void startActivities(Intent[] intents, Bundle options) {

        }

        @Override
        public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {

        }

        @Override
        public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {

        }

        @Override
        public void sendBroadcast(Intent intent) {

        }

        @Override
        public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {

        }

        @Override
        public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {

        }

        @Override
        public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

        }

        @Override
        public void sendBroadcastAsUser(Intent intent, UserHandle user) {

        }

        @Override
        public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {

        }

        @Override
        public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

        }

        @Override
        public void sendStickyBroadcast(Intent intent) {

        }

        @Override
        public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

        }

        @Override
        public void removeStickyBroadcast(Intent intent) {

        }

        @Override
        public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {

        }

        @Override
        public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

        }

        @Override
        public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {

        }

        @Nullable
        @Override
        public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
            return null;
        }

        @Nullable
        @Override
        public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
            return null;
        }

        @Override
        public void unregisterReceiver(BroadcastReceiver receiver) {

        }

        @Nullable
        @Override
        public ComponentName startService(Intent service) {
            return null;
        }

        @Override
        public boolean stopService(Intent service) {
            return false;
        }

        @Override
        public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
            return false;
        }

        @Override
        public void unbindService(@NonNull ServiceConnection conn) {

        }

        @Override
        public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
            return false;
        }

        @Nullable
        @Override
        public Object getSystemService(@NonNull String name) {
            return null;
        }

        @Nullable
        @Override
        public String getSystemServiceName(@NonNull Class<?> serviceClass) {
            return null;
        }

        @Override
        public int checkPermission(@NonNull String permission, int pid, int uid) {
            return 0;
        }

        @Override
        public int checkCallingPermission(@NonNull String permission) {
            return 0;
        }

        @Override
        public int checkCallingOrSelfPermission(@NonNull String permission) {
            return 0;
        }

        @Override
        public int checkSelfPermission(@NonNull String permission) {
            return 0;
        }

        @Override
        public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {

        }

        @Override
        public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {

        }

        @Override
        public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {

        }

        @Override
        public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {

        }

        @Override
        public void revokeUriPermission(Uri uri, int modeFlags) {

        }

        @Override
        public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
            return 0;
        }

        @Override
        public int checkCallingUriPermission(Uri uri, int modeFlags) {
            return 0;
        }

        @Override
        public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
            return 0;
        }

        @Override
        public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
            return 0;
        }

        @Override
        public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {

        }

        @Override
        public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {

        }

        @Override
        public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {

        }

        @Override
        public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {

        }

        @Override
        public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
            return null;
        }

        @Override
        public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
            return null;
        }

        @Override
        public Context createDisplayContext(@NonNull Display display) {
            return null;
        }
    }
}


