package com.krishna.iparker.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

public class RefillWalletActivity extends AppCompatActivity {

    private EditText mEdtAccNo,mEdtCVV,mEdtAmt;
    private Button mBtnRefillWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refill_wallet);
        mEdtAccNo=findViewById(R.id.edtAccNo);
        mEdtCVV=findViewById(R.id.edtCVV);
        mEdtAmt=findViewById(R.id.edtAmount);
        mBtnRefillWallet=findViewById(R.id.btnRefillWallet);

        mBtnRefillWallet.setOnClickListener(new MyBtnClickListener());
    }
    class MyBtnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
              refillWallet();
        }
    }
    public void refillWallet(){
        final String cardno=mEdtAccNo.getText().toString().trim();
        final String cvv=mEdtCVV.getText().toString().trim();

        final String amt=mEdtAmt.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.REFILL_WALLET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RefillWalletActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cardno",cardno);
                params.put("cvv",cvv);
                params.put("amount", amt);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(RefillWalletActivity.this);
        requestQueue.add(stringRequest);


    }

}
