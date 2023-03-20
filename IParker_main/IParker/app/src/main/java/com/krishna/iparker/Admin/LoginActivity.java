package com.krishna.iparker.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEdtUsername,mEdtPassword;
    private Button mBtnSignUp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        if (SharedPrefManagerAdmin.getInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(this, UserRequestActivity.class));
            return;
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }
    private void init() {
        mEdtUsername=findViewById(R.id.edtAdminUsername);
        mEdtPassword=findViewById(R.id.edtAdminPassword);
        Button btnLogIn = findViewById(R.id.btnAdminLogIn);
        mBtnSignUp=findViewById(R.id.btnAdminSignUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtUsername.toString().equals("") ||
                        mEdtPassword.toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter full details", Toast.LENGTH_SHORT).show();
                    return;
                }

                userLogin();
            }
        });
        mBtnSignUp.setOnClickListener(this);

    }

    private void userLogin() {

        final String username = mEdtUsername.getText().toString().trim();
        final String password = mEdtPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, IPConfig.ADMIN_LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManagerAdmin.getInstance(getApplicationContext()).
                                        userLogin(
                                                obj.getInt("id"),
                                                obj.getString("Name"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(LoginActivity.this, UserRequestActivity.class));
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag",error.toString());
                progressDialog.dismiss();
                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("Password", password);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdminSignUp:

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:

        }
    }
}
