package com.krishna.iparker.User;

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
import com.krishna.iparker.Admin.LoginActivity;
import com.krishna.iparker.Admin.RegisterActivity;
import com.krishna.iparker.Admin.UserRequestActivity;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText mEdtName,mEdtEmail,mEdtMobile,mEdtAddess,mEdtPassword,mEdtCardno,mEdtCvv,mEdtAmount;
    private Button mBtnSignUp;
    ProgressDialog progressDialog;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        init();
    }
    private void init() {
        mEdtName=findViewById(R.id.etFullName);
        mEdtEmail=findViewById(R.id.etEmail);
        mEdtMobile=findViewById(R.id.etMobile);
        mEdtAddess=findViewById(R.id.etArea);
        mEdtPassword=findViewById(R.id.etPassword);
        mBtnSignUp=findViewById(R.id.btnRegister);

        mEdtCardno=findViewById(R.id.etCardno);
        mEdtCvv=findViewById(R.id.etcvv);
        mEdtAmount=findViewById(R.id.etAmount);

        progressDialog=new ProgressDialog(this);


        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.etFullName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.etMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.etArea, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.addresserror);
        String regexPassword = ".{6,}";
        awesomeValidation.addValidation(this, R.id.etPassword, regexPassword, R.string.invalid_password);

       // awesomeValidation.addValidation(this, R.id.etCardno, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.invalid_cardno);
        String regexCVV = ".{3,}";
     //   awesomeValidation.addValidation(this, R.id.etcvv, regexPassword, R.string.invalid_cvv);
        String regexAmount = ".{1,}";
      //  awesomeValidation.addValidation(this, R.id.etAmount, regexPassword, R.string.invalid_amount);







        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    registerUser();
                    //process the data further
                }

            }
        });


    }
    public void registerUser(){
        final String email=mEdtEmail.getText().toString().trim();
        final String name=mEdtName.getText().toString().trim();
        final String mobile=mEdtMobile.getText().toString().trim();
        final String password=mEdtPassword.getText().toString().trim();
        final String area=mEdtAddess.getText().toString().trim();
        final String cardno=mEdtCardno.getText().toString().trim();
        final String cvv=mEdtCvv.getText().toString().trim();
        final String amount=mEdtAmount.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (response.contains("success")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UserRegisterActivity.this,UserLoginActivity.class));
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);
                params.put("area", area);
                params.put("cardno", cardno);
                params.put("cvv", cvv);
                params.put("amount", amount);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(UserRegisterActivity.this);
        requestQueue.add(stringRequest);


    }
}
