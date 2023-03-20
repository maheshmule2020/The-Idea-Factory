package com.krishna.iparker.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.krishna.iparker.IPConfig;
import com.krishna.iparker.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GetQRInfoActivity extends AppCompatActivity {


    String code;
    ProgressDialog pDialog;
    String checkURL = IPConfig.verifyQR_URL;
    TextView txqrdata, txtinfo;
    private EditText mEdtEnterAmt;
    Button scanqr, vinfo, mBtnSetCharges;
    ;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_qrinfo);
        vinfo = (Button) findViewById(R.id.btnvinfoTrafic);
        txqrdata = (TextView) findViewById(R.id.txtqrdataTrafic);
        txtinfo = (TextView) findViewById(R.id.txtuserdataTrafic);

        pDialog = new ProgressDialog(GetQRInfoActivity.this);
        pDialog.setMessage("Processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        vinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdetails();
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                code = intent.getStringExtra("SCAN_RESULT");
                details();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
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
            startActivity(new Intent(GetQRInfoActivity.this, LoginActivity.class));

            return true;
        }else if (id == R.id.menu_send_loc) {

            startActivity(new Intent(GetQRInfoActivity.this, SendLocActivity.class));

            return true;
        }else if (id == R.id.scan) {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
            startActivityForResult(intent, 0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void details() {

        RequestParams params = new RequestParams();
        params.put("code", code);

        pDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(checkURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String response = new String(responseBody);
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    String success = obj.getString("success");
                    if (success.equals("200")) {

                        IPConfig.username = obj.getString("username");
                        IPConfig.vehicle_no = obj.getString("vehicle_no");
                        IPConfig.email = obj.getString("email");
                        IPConfig.parkingname = obj.getString("date");
                        IPConfig.date = obj.getString("date");
                        IPConfig.timeFrom = obj.getString("timeFrom");
                        IPConfig.uid = obj.getString("id");
                        IPConfig.timeTo = obj.getString("timeTo");

                        txqrdata.setText(code);
                        Toast.makeText(GetQRInfoActivity.this, code, Toast.LENGTH_SHORT);

                        Toast.makeText(GetQRInfoActivity.this, "Secret Key send on registered email id", Toast.LENGTH_SHORT);
                        //
                        //sendEmail(IPaddress.skey, IPaddress.uemail);
                    } else {
                        txqrdata.setText("Invalid QRCode");
                        Toast.makeText(GetQRInfoActivity.this, "Invalid QRCode", Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    Toast.makeText(GetQRInfoActivity.this, "JSON Error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(GetQRInfoActivity.this, "Connectivity failed with server!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setdetails() {
        data = "User Id: " + IPConfig.uid + "\nUser Name: " + IPConfig.username
                + "\nvehicle_no: " + IPConfig.vehicle_no + "\nEmail: "
                + IPConfig.email + "\nParking Name: " + IPConfig.parkingname
                + "\nDate: " + IPConfig.date+ "\nTime From: " + IPConfig.timeFrom+"\nTime To: " + IPConfig.timeTo;

        txtinfo.setText(data);
    }

}
