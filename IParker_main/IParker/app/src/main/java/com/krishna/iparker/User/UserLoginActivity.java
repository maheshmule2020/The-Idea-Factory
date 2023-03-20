package com.krishna.iparker.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.krishna.iparker.Admin.LoginActivity;
import com.krishna.iparker.Admin.RegisterActivity;
import com.krishna.iparker.Admin.SharedPrefManagerAdmin;
import com.krishna.iparker.Admin.UserRequestActivity;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEdtUsername,mEdtPassword;
    private Button mBtnSignUp;
    private TextView admin,mTxtAdmin;
    ProgressDialog progressDialog;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        init();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(this, MapsActivity.class));
            return;
        }
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }
    private void init() {
        mEdtUsername=findViewById(R.id.edtUsername);
        mEdtPassword=findViewById(R.id.edtPassword);
        Button btnLogIn = findViewById(R.id.btnLogIn);
        mTxtAdmin=findViewById(R.id.txtAdmin);
        admin=findViewById(R.id.Admin);
        mTxtAdmin.setVisibility(View.VISIBLE);
        mBtnSignUp=findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtUsername.toString().equals("") ||
                        mEdtPassword.toString().equals("")) {
                    Toast.makeText(UserLoginActivity.this, "Please enter full details", Toast.LENGTH_SHORT).show();
                    return;
                }

                userLogin();
            }
        });
        mBtnSignUp.setOnClickListener(this);
        mTxtAdmin.setOnClickListener(this);
        admin.setOnClickListener(this);



    }

    private void userLogin() {

        final String username = mEdtUsername.getText().toString().trim();
        final String password = mEdtPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, IPConfig.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).
                                        userLogin(
                                                obj.getInt("id"),
                                                obj.getString("name"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(UserLoginActivity.this, MapsActivity.class));
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
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(UserLoginActivity.this);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:

                startActivity(new Intent(UserLoginActivity.this,UserRegisterActivity.class));
                break;
            case R.id.txtAdmin:
                startActivity(new Intent(UserLoginActivity.this,LoginActivity.class));
                break;
            case R.id.Admin:
                startActivity(new Intent(UserLoginActivity.this,OwnerLoginActivity.class));
                break;

            default:

        }
    }

}
