package com.krishna.iparker.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestAcceptActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    AcceptRequestAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> mListOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_accept);
        recyclerView = findViewById(R.id.recyclerConfirmed);
        mListOrders = new ArrayList<>();
        final String email= SharedPrefManager.getInstance(RequestAcceptActivity.this).getUserEmail();
        requestQueue = Volley.newRequestQueue(RequestAcceptActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                IPConfig.GET_ACCEPT+email, null, new SuccessListener(),
                new FailureListener());

        requestQueue.add(jsonObjectRequest);
        progressDialog =new ProgressDialog(RequestAcceptActivity.this);
    }
    class SuccessListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {

            Log.e("Response", "data:" + response.toString());
            Toast.makeText(RequestAcceptActivity.this,response.toString(), Toast.LENGTH_SHORT).show();


            try {

                JSONArray jsonOrders = (JSONArray) response.get("result");
                for (int i = 0; i < jsonOrders.length(); i++)

                {
                    ModelClass modelclass= new ModelClass();
                    JSONObject jsonObject1 = jsonOrders.getJSONObject(i);

                    String vehicle_no=jsonObject1.getString("vehicle_no");
                    String email=jsonObject1.getString("email");
                    String pname=jsonObject1.getString("pname");
                    String date=jsonObject1.getString("date");
                    String time=jsonObject1.getString("time");

                    String data = "vehicle no: " + vehicle_no + "\nemail: " + email
                            + "\n" + pname + "\ndate: " + date +
                            "\ntime: " + time +"\nYour Request Accepted ";
                    modelclass.setConfirmed(data);

                    mListOrders.add(modelclass);

                }

                adapter = new AcceptRequestAdapter(RequestAcceptActivity.this, mListOrders);

                recyclerView.setLayoutManager(new LinearLayoutManager(RequestAcceptActivity.this));

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
}
