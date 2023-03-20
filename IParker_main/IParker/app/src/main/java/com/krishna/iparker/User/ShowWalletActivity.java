package com.krishna.iparker.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class ShowWalletActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    ViewResultAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> mListCandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wallet);
        recyclerView = (RecyclerView) findViewById(R.id.rvResults);
        mListCandidate = new ArrayList<>();
        final String email= SharedPrefManager.getInstance(ShowWalletActivity.this).getUserEmail();
        requestQueue = Volley.newRequestQueue(ShowWalletActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                IPConfig.GET_ACC_BALANCE+email, null, new SuccessListener(),
                new FailureListener());

        requestQueue.add(jsonObjectRequest);
        progressDialog =new ProgressDialog(ShowWalletActivity.this);
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

                    modelClass.setAcc_balance(jsonObject1.getString("amount"));

                    mListCandidate.add(modelClass);

                }

                adapter = new ViewResultAdapter(ShowWalletActivity.this, mListCandidate);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(ShowWalletActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
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
