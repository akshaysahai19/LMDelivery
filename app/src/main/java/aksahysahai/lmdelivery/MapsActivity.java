package aksahysahai.lmdelivery;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AppCompatImageView info_card_iv;
    TextView info_card_tv;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //This adds a back button to the activity android
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        info_card_iv = findViewById(R.id.info_card_iv);
        info_card_tv = findViewById(R.id.info_card_tv);

        Glide.with(this)
                .load(getIntent().getStringExtra("imageUrl"))
                .into(info_card_iv);

        info_card_tv.setText(getIntent().getStringExtra("description"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in given lat_lng and move the camera
        LatLng delivery_lat_lng = new LatLng(getIntent().getDoubleExtra("lat",0), getIntent().getDoubleExtra("lng",0));
        mMap.addMarker(new MarkerOptions().position(delivery_lat_lng).title(getIntent().getStringExtra("address")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delivery_lat_lng));
        mMap.setMinZoomPreference(14);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
