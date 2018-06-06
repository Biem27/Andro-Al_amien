package com.example.biem.alamien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.model.baseUrlApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private EditText email,telp,username,password,repass;
    private RadioButton tk,sd,smp;
    private Snackbar snackbar;
    private ProgressDialog pd;
    com.example.biem.alamien.model.baseUrlApi baseUrlApi = new baseUrlApi();
    private String URL = baseUrlApi.getBaseUrl();
    private String Urlregister = "api/user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //find id
        pd = new ProgressDialog(register.this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        telp = findViewById(R.id.telp);
        email = findViewById(R.id.email);
        tk = findViewById(R.id.tk);
        sd = findViewById(R.id.sd);
        smp = findViewById(R.id.smp);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int t = item.getItemId();
        if (t==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();

    }

    public void daftar(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        View focusView = null;
        username.setError(null);
        password.setError(null);
        boolean cancel = false;

        if (!tk.isChecked() && !sd.isChecked() && !smp.isChecked()) {
            showSnackbar("Pilih jenjang anda");
        } else if (TextUtils.isEmpty(user)) {
            showSnackbar("Isi Username");
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            username.requestFocus();
            cancel = true;
        } else if (TextUtils.isEmpty(pass)) {
            showSnackbar("Isi password");
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            password.requestFocus();
            cancel = true;
        } else if (TextUtils.isEmpty(repass.getText())) {
            showSnackbar("not correct");
            repass.setError(getString(R.string.error_field_required));
            focusView = repass;
            repass.requestFocus();
            cancel = true;
//        }else if (!repass.getText().toString().equals(password.getText().toString())){
//            showSnackbar("not correct");
//            repass.setError(getString(R.string.error_field_required));
//            focusView = repass;
//            repass.requestFocus();
//            cancel = true;
//        }
        }else{
            signupRequest();
        }

    }
    private void signupRequest(){
        pd.setMessage("Signing Up . . .");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(register.this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL+Urlregister,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String succes = jsonObject.getString("succes");
                            if (succes.equals("1")) {
//                                startActivity(new Intent(getApplicationContext(),bukti_bayar.class));
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                                showSnackbar("Data berhasil di input");
                            }else {
                                showSnackbar("Data gagal di input \n Username sudah ada");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", finalResponse);


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                params.put("email", email.getText().toString());
                params.put("no_telp", email.getText().toString());
                if (tk.isChecked()) {
                    params.put("jenjang","46127");
                }else if (sd.isChecked()){
                    params.put("jenjang","46128");
                }else if (smp.isChecked()){
                    params.put("jenjang","46128");
                }
                params.put("api", "register");
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }
}

