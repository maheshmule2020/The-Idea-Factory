package com.krishna.iparker.User;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.krishna.iparker.Admin.LoginActivity;
import com.krishna.iparker.Admin.SendLocActivity;
import com.krishna.iparker.Admin.SharedPrefManagerAdmin;
import com.krishna.iparker.Admin.UserRequestActivity;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    // Latitude & Longitude
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    ArrayList<HashMap<String, String>> location ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(this, UserLoginActivity.class));
            return;
        }
        if (android.os.Build.VERSION.SDK_INT > 16) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        location=new ArrayList<>();

        //String url = "http://192.168.0.105/pathhole_db/get_loc.php";
        try {

            JSONArray data = new JSONArray(getHttpGet(IPConfig.GET_LOC));

            location = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("loc_id", c.getString("loc_id"));
                map.put("lat", c.getString("lat"));
                map.put("lon", c.getString("lon"));
                map.put("pname", c.getString("pname"));
                map.put("avail_slots", c.getString("avail_slots"));
                map.put("rate", c.getString("rate"));
                map.put("ptype", c.getString("ptype"));
                location.add(map);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // *** Display Google Map
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(MapsActivity.this);

        // *** Focus & Zoom


    }
    public static String getHttpGet(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            org.apache.http.HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Latitude = Double.parseDouble(location.get(0).get("lat").toString());
        Longitude = Double.parseDouble(location.get(0).get("lon").toString());
        LatLng coordinate = new LatLng(Latitude, Longitude);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

        // *** Marker (Loop)
        for (int i = 0; i < location.size(); i++) {
            Latitude = Double.parseDouble(location.get(i).get("lat").toString());
            Longitude = Double.parseDouble(location.get(i).get("lon").toString());
            String pname = location.get(i).get("pname").toString();
            String avail_slots = location.get(i).get("avail_slots").toString();
            String rate = location.get(i).get("rate").toString();
            String ptype = location.get(i).get("ptype").toString();

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title("Parking");
            //googleMap.addMarker(markerOptions);

            final ModelClass info = new ModelClass();
            info.setPname(pname);
            info.setAvailslots(avail_slots);
            info.setRate(rate);
            info.setPtype(ptype);

            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
            googleMap.setInfoWindowAdapter(customInfoWindow);
            Marker m = googleMap.addMarker(markerOptions);
            m.setTag(info);
            m.showInfoWindow();
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getBaseContext(), SendRequestActivity.class);
                    intent.putExtra("pname", info.getPname());
                    intent.putExtra("avail_slots", info.getAvailslots());
                    intent.putExtra("rate", info.getRate());
                    intent.putExtra("ptype", info.getPtype());

                    startActivity(intent);
                }
            });
        }
    }

    public void onClick(View view) {

        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(MapsActivity.this, UserLoginActivity.class));

    }
}
