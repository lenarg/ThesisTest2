package com.example.media1.thesistest2;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

//import com.google.android.gms.location.places.Place;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

//ayto htan gia to xml arxeio
/**public class PlacesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        listView = (ListView) findViewById(R.id.list);

        List<Placep> placesp = null;

        try{
            XMLPullParserHandler parser = new XMLPullParserHandler();
            placesp = parser.parse(getAssets().open("placesp.xml"));
            ArrayAdapter<Placep> adapter =
                    new ArrayAdapter<Placep>(this, R.layout.list_item, placesp); //simple_list_item_1

            listView.setAdapter(adapter);

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

}**/

//edw dokimazoume me json

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//package com.example.media1.thesistest2;
import com.example.media1.thesistest2.JSONParser;

public class PlacesActivity extends Activity {
    TextView uid;
    TextView name1;
    TextView email1;
    Button Btngetdata;

    //URL to get JSON Array
    private static String url = "http://zafora.icte.uowm.gr/~ictest00344/testjson.php";//"http://10.0.2.2/JSON/";

    //JSON Node Names
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";

    JSONArray user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();

            }
        });

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uid = (TextView)findViewById(R.id.uid);
            name1 = (TextView)findViewById(R.id.name);
            email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(PlacesActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);//rl
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                user = json.getJSONArray(TAG_USER);
                JSONObject c = user.getJSONObject(0);

                // Storing  JSON item in a Variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String email = c.getString(TAG_EMAIL);

                //Set JSON Data in TextView
                uid.setText(id);
                name1.setText(name);
                email1.setText(email);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}