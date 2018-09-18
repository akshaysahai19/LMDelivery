package aksahysahai.lmdelivery;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<DeliveryGetSet> deliveryGetSets;
    SharedPreferences sharedPreferences;
    TextView no_data_tv;


    //api url
    String url = "https://mock-api-mobile.dev.lalamove.com/deliveries?offset=0&limit=20";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        no_data_tv = findViewById(R.id.no_data_tv);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        deliveryGetSets = new ArrayList<>();

        //this if-else loop checks, if the device has an active internet connection or not
        //if it does, then http request will be used otherwise it will check if it is present in cached storage
        if (isConnectedToInternet()) {
            new HttpGetRequest().execute();
        } else {
            if (!sharedPreferences.getString("delivery_data", "").equals("")) {
                updateDataArray(sharedPreferences.getString("delivery_data", ""));
            }
        }

    }


    private class HttpGetRequest extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceGetCall(url);

            Log.e("TAG", "Response from get_url: " + jsonStr);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateDataArray(result);
        }

    }


    //this function converts the json response to JSON array and then as JsonObject
    public void updateDataArray(String jsonResponse) {
        //it caches the reponse, so that it can be used when the device is offline
        sharedPreferences.edit().putString("delivery_data", jsonResponse).apply();
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getJSONObject(i).getString("id");
                    String description = jsonArray.getJSONObject(i).getString("description");
                    String imageUrl = jsonArray.getJSONObject(i).getString("imageUrl");
                    JSONObject location = new JSONObject(jsonArray.getJSONObject(i).getString("location"));
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    String address = location.getString("address");

                    LocationGetSet locationGetSet = new LocationGetSet(address, lat, lng);
                    DeliveryGetSet deliveryGetSet = new DeliveryGetSet(description, imageUrl, locationGetSet);
                    deliveryGetSets.add(deliveryGetSet);
                }
                updateRecyclerview();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //recyclerview is updated with retreived data
    public void updateRecyclerview() {
        no_data_tv.setVisibility(View.GONE);
        recyclerViewAdapter = new RecyclerViewAdapter(this, deliveryGetSets);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    //function to check if the device is online or not?
    public boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


}
