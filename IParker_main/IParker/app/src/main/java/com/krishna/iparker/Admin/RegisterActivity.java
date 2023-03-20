package com.krishna.iparker.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEdtName,mEdtMobile,mEditEmail,mEdtPassword,
            mEdtArea,mEdtSlots,mEdtChages,lat,lon,pname;
    private Button mBtnRegister;
    ProgressDialog progressDialog;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        init();

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                   registerUser();
                }
            }
        });

        progressDialog=new ProgressDialog(this);

    }
    public void registerUser(){
        final String name=mEdtName.getText().toString().trim();
        final String mobile=mEdtMobile.getText().toString().trim();
        final String email=mEditEmail.getText().toString().trim();
        final String password=mEdtPassword.getText().toString().trim();
        final String area=mEdtArea.getText().toString().trim();
        final String slots=mEdtSlots.getText().toString().trim();
        final String charges=mEdtChages.getText().toString().trim();
        final String latt=lat.getText().toString().trim();
        final String lonn=lon.getText().toString().trim();
        final String ppname=pname.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.ADMIN_REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (response.contains("success")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", name);
                params.put("mobile",mobile);
                params.put("email", email);
                params.put("Password",password);
                params.put("area", area);
                params.put("slots",slots);
                params.put("charges",charges);
                params.put("pname", ppname);
                params.put("lon",lonn);
                params.put("lat",latt);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);


    }
    private void init() {
        mEdtName=findViewById(R.id.etAdminFullName);
        mEdtMobile=findViewById(R.id.etAdminMobile);
        mEditEmail=findViewById(R.id.etAdminEmail);
        mEdtPassword=findViewById(R.id.etAdminPassword);
        lat=findViewById(R.id.etLatArea);
        lon=findViewById(R.id.etLonSlots);
        pname=findViewById(R.id.etPname);
        mEdtSlots=findViewById(R.id.etAdminSlots);
        mEdtChages=findViewById(R.id.etAdminCharge);
        mEdtArea=findViewById(R.id.etAdminArea);
        mBtnRegister=findViewById(R.id.btnAdminRegister);
        addValidationToViews();
    }
    private void addValidationToViews() {

        awesomeValidation.addValidation(this, R.id.etAdminFullName, RegexTemplate.NOT_EMPTY, R.string.nameerror);

        awesomeValidation.addValidation(this, R.id.etAdminEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        String regexPassword = ".{6,}";
        awesomeValidation.addValidation(this, R.id.etAdminPassword, regexPassword, R.string.invalid_password);

        awesomeValidation.addValidation(this, R.id.etAdminMobile, "^[+]?[0-9]{10,13}$", R.string.mobileerror);


    }

}
