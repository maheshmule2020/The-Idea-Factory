package com.krishna.iparker.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.iparker.Admin.LoginActivity;
import com.krishna.iparker.Admin.SharedPrefManagerAdmin;
import com.krishna.iparker.Admin.UserRequestActivity;
import com.krishna.iparker.IPConfig;
import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendRequestActivity extends AppCompatActivity {

    private TextView mTxtPname,mTxtAvailSlots,mTxtRate,mTxtPtype,mTxtDate,mTxtTime,mTxtTimeTo;
    private EditText mEdtVehicleNo;
    RequestQueue requestQueue;
    String rate="",id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        mTxtPname=findViewById(R.id.txtSendPname);
        mTxtAvailSlots=findViewById(R.id.txtSendAvailSlots);
        mTxtRate=findViewById(R.id.txtSendRate);
        mTxtPtype=findViewById(R.id.txtSendPtype);
        mTxtDate=findViewById(R.id.txtDate);
        mTxtTime=findViewById(R.id.txtTime);
        mTxtTimeTo=findViewById(R.id.txtTimeTo);
        mEdtVehicleNo=findViewById(R.id.edtVehicleNo);

        final String pname= getIntent().getStringExtra("pname");
        Toast.makeText(SendRequestActivity.this, pname, Toast.LENGTH_SHORT).show();

        String avail_slots= getIntent().getStringExtra("avail_slots");
         rate= getIntent().getStringExtra("rate");
        String ptype= getIntent().getStringExtra("ptype");

        mTxtPname.setText("Place Name: "+pname);
        mTxtAvailSlots.setText("Availble slots: "+avail_slots);
        mTxtRate.setText("Rate: "+rate);
        mTxtPtype.setText("Type: "+ptype);

        mTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
              int  year = c.get(Calendar.YEAR);
              int  month = c.get(Calendar.MONTH);
              int  day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(SendRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                        mTxtDate.setText(year + "-" + monthofyear + "-" +dayofmonth );
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        mTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SendRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTxtTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time From");
                mTimePicker.show();

            }
        });

        mTxtTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SendRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTxtTimeTo.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time To");
                mTimePicker.show();

            }
        });

        Button mBtnSendRequset=findViewById(R.id.btnSendRequest);
        mBtnSendRequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEdtVehicleNo.getText().toString().equals("")){
                    postData();
                }else {
                    Toast.makeText(SendRequestActivity.this, "field can't be empty", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final String email= SharedPrefManager.getInstance(SendRequestActivity.this).getUserEmail();
        requestQueue = Volley.newRequestQueue(SendRequestActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                IPConfig.GET_NOTIFY+email, null, new SuccessListener(),
                new FailureListener());

        requestQueue.add(jsonObjectRequest);
        getPenalty(rate);
    }

    public void postData() {
        RequestQueue queue = Volley.newRequestQueue(SendRequestActivity.this);
        final String uname= SharedPrefManager.getInstance(SendRequestActivity.this).getUserName();
        final String email= SharedPrefManager.getInstance(SendRequestActivity.this).getUserEmail();
        final String vno=mEdtVehicleNo.getText().toString();
        final String pname=mTxtPname.getText().toString();
        final String date=mTxtDate.getText().toString();
        final String time=mTxtTime.getText().toString();
        final String timeTO=mTxtTimeTo.getText().toString();


        StringRequest request = new StringRequest(Request.Method.POST, IPConfig.SEND_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.contains("Success")){

                    Toast.makeText(SendRequestActivity.this,"Request Sent",Toast.LENGTH_SHORT).show();
                    mTxtPname.setText("");
                    mTxtAvailSlots.setText("");
                    mTxtRate.setText("");
                    mTxtDate.setText("");
                    mTxtTime.setText("");
                    mTxtTimeTo.setText("");
                    Intent intent = new Intent(SendRequestActivity.this,QRActivity.class);
                    startActivity(intent);
                }
                //  Toast.makeText(getContext(), "msg"+response, Toast.LENGTH_SHORT).show();
                Log.i("My success",""+response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SendRequestActivity.this, "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String,String> map = new HashMap<>();
                map.put("username",uname);
                map.put("vehicle_no",vno);
                map.put("email",email);
                map.put("parkingname",pname);
                map.put("date",date);
                map.put("timeFrom",time);
                map.put("timeTo",timeTO);


                return map;
            }
        };
        queue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_show_balance) {


            startActivity(new Intent(SendRequestActivity.this, ShowWalletActivity.class));

            return true;
        }
        if (id == R.id.menu_user_logout) {

            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(SendRequestActivity.this, UserLoginActivity.class));

            return true;
        }else if (id == R.id.menu_user_notif) {


            startActivity(new Intent(SendRequestActivity.this, RequestAcceptActivity.class));

            return true;
        }else if (id == R.id.menu_my_wallet) {


            startActivity(new Intent(SendRequestActivity.this, RefillWalletActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SuccessListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            try {

                JSONArray jsonOrders = (JSONArray) response.get("result");
                for (int i = 0; i < jsonOrders.length(); i++)

                {

                    JSONObject jsonObject1 = jsonOrders.getJSONObject(i);
                    id=jsonObject1.getString("id");
                    String time=jsonObject1.getString("flag");
                    if (time.equals("1")){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SendRequestActivity.this);
                        alertDialogBuilder.setMessage("Are you sure, Do you want to extend time?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Calendar mcurrentTime = Calendar.getInstance();
                                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                                TimePickerDialog mTimePicker;
                                                mTimePicker = new TimePickerDialog(SendRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                      String timeTo=(selectedHour + ":" + selectedMinute);
                                                      sendExtend(timeTo);
                                                      deductAmount(rate);
                                                    }
                                                }, hour, minute, true);//Yes 24 hour time
                                                mTimePicker.setTitle("Select Time");
                                                mTimePicker.show();

                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(SendRequestActivity.this,"Thanks...!!!",Toast.LENGTH_LONG).show();
                                        deleteRequest();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    class FailureListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("error",error.toString());
        }
    }

    public void sendExtend(final String timeTo){
        final String email= SharedPrefManager.getInstance(SendRequestActivity.this).getUserEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.GET_EXTEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SendRequestActivity.this, response, Toast.LENGTH_LONG).show();

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
                params.put("timeTo",timeTo);
                params.put("email",email);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(SendRequestActivity.this);
        requestQueue.add(stringRequest);


    }

    public void deductAmount(final String rate) {
        final String email= SharedPrefManager.getInstance(SendRequestActivity.this).getUserEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.GET_DEDUCT_AMT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SendRequestActivity.this, response, Toast.LENGTH_LONG).show();
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
                params.put("email",email);
                params.put("amount", rate);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SendRequestActivity.this);
        requestQueue.add(stringRequest);

    }

    public void deleteRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.DELETE_USER_URL+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(SendRequestActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SendRequestActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                return new HashMap<>();
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(SendRequestActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getPenalty(final String rate) {
        final String email= SharedPrefManager.getInstance(SendRequestActivity.this).getUserEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                IPConfig.GET_PENAULTY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SendRequestActivity.this, response, Toast.LENGTH_LONG).show();
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
                params.put("email",email);
                params.put("amount",rate);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SendRequestActivity.this);
        requestQueue.add(stringRequest);

    }

}
