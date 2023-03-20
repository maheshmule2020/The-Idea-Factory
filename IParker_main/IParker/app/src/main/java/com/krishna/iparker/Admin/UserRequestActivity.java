package com.krishna.iparker.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRequestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    UserRequestAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> mListReq;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);

         /*if (!SharedPrefManagerAdmin.getInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }*/
        recyclerView = findViewById(R.id.recyclerRequest);
        mListReq = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(UserRequestActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                IPConfig.GET_USER_REQUEST, null, new SuccessListener(),
                new FailureListener());

        requestQueue.add(jsonObjectRequest);
        progressDialog =new ProgressDialog(UserRequestActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_logout) {

            SharedPrefManagerAdmin.getInstance(this).logout();
            finish();
            startActivity(new Intent(UserRequestActivity.this, LoginActivity.class));

            return true;
        }else if (id == R.id.menu_send_loc) {

            startActivity(new Intent(UserRequestActivity.this, SendLocActivity.class));

            return true;
        }else if (id == R.id.menu_scan_qr) {

            startActivity(new Intent(UserRequestActivity.this, GetQRInfoActivity.class));


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class SuccessListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {

            Log.e("Response", "data:" + response.toString());

            try {

                JSONArray jsonOrders = (JSONArray) response.get("result");
                for (int i = 0; i < jsonOrders.length(); i++)

                {
                    ModelClass modelClass = new ModelClass();
                    JSONObject jsonObject1 = jsonOrders.getJSONObject(i);

                    String username=(jsonObject1.getString("username"));
                    String vehicle_no=(jsonObject1.getString("vehicle_no"));
                    String email=(jsonObject1.getString("email"));
                    String pname=(jsonObject1.getString("parkingname"));
                    String date=(jsonObject1.getString("date"));
                    String time=(jsonObject1.getString("timeFrom"));
                    String timeTo=(jsonObject1.getString("timeTo"));

                    String data = "User Name: " + username+ "\nVehicle No.: " + vehicle_no
                            + "\nEmail: " + email + "\n"
                            + pname + "\nDate: " + date
                            + "\ntimeFrom: " + time
                            + "\ntimeTo: " + timeTo ;
                    modelClass.setId(jsonObject1.getString("id"));
                    modelClass.setDetails(data);
                    mListReq.add(modelClass);

                }

                adapter = new UserRequestAdapter(UserRequestActivity.this, mListReq);
                adapter.setAcceptClickListener(new MyAcceptBtnListener());

                recyclerView.setLayoutManager(new LinearLayoutManager(UserRequestActivity.this));

                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }

    }
    class FailureListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("error",error.toString());
        }
    }
    class MyAcceptBtnListener implements UserRequestAdapter.AcceptClickListener{

        @Override
        public void onAcceptClick(ModelClass modelClass) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    IPConfig.Accept_USER_REQUEST+modelClass.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(UserRequestActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserRequestActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("flag", "1");


                    return params;
                }
            };
            RequestQueue requestQueue=Volley.newRequestQueue(UserRequestActivity.this);
            requestQueue.add(stringRequest);
        }
    }
}
