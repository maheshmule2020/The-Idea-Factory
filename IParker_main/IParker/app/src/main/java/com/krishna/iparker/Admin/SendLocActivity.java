package com.krishna.iparker.Admin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;

import java.util.HashMap;
import java.util.Map;

public class SendLocActivity extends AppCompatActivity {

    String lat = "", lon = "";
    private EditText mEdtPName,mEdtAvailSlots,mEdtRate,mEdtPType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_loc);
        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        mEdtPName=findViewById(R.id.edtPName);
        mEdtAvailSlots=findViewById(R.id.edtAvailSlots);
        mEdtRate=findViewById(R.id.edtRate);
        mEdtPType=findViewById(R.id.editText4);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) SendLocActivity.this.getSystemService(Context.LOCATION_SERVICE);
                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                        TextView tv = (TextView) findViewById(R.id.txtLoc);
                        tv.setText("Your Location is: " + lat + "," + lon);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                // Register the listener with the Location Manager to receive location updates
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postData(lat, lon);
            }
        });


    }

    public void postData(final String la,final String lo) {
        final String pname=mEdtPName.getText().toString();
        final String avail_slots=mEdtAvailSlots.getText().toString();
        final String rate=mEdtRate.getText().toString();
        final  String ptype=mEdtPType.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(SendLocActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, IPConfig.SEND_LOC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.contains("Success")){

                    Toast.makeText(SendLocActivity.this,"Location Added Successfully",Toast.LENGTH_SHORT).show();
                }
                //  Toast.makeText(getContext(), "msg"+response, Toast.LENGTH_SHORT).show();
                Log.i("My success",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SendLocActivity.this, "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("lat",la);
                map.put("lon",lo);
                map.put("pname",pname);
                map.put("avail_slots",avail_slots);
                map.put("rate",rate);
                map.put("ptype",ptype);


                return map;
            }
        };
        queue.add(request);
    }

}
