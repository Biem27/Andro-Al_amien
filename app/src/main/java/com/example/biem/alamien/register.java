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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.activity.bukti_bayar;
import com.example.biem.alamien.model.baseUrlApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private EditText email,telp,username,password,repass;
    private RadioButton tk,sd,smp;
    private Snackbar snackbar;
    private Button btn_daftar;
    private ProgressBar loading;
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
        btn_daftar = findViewById(R.id.daftar);
        loading = findViewById(R.id.loading);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        telp = findViewById(R.id.telp);
        email = findViewById(R.id.email);
        tk = findViewById(R.id.tk);
        sd = findViewById(R.id.sd);
        smp = findViewById(R.id.smp);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clik();
            }
        });
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

    public void clik() {
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
        }else{
            signupRequest();
        }

    }
    private void signupRequest() {
    loading.setVisibility(View.VISIBLE);
    btn_daftar.setVisibility(View.GONE);

    final String email = this.email.getText().toString().trim();
    final String telp = this.telp.getText().toString().trim();
    final String username = this.username.getText().toString().trim();
    final String password = this.password.getText().toString().trim();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + Urlregister,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("succes");
                    if (succes.equals("1")) {
//                                startActivity(new Intent(getApplicationContext(),bukti_bayar.class));
                        showSnackbar("Data berhasil di input");
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                    }else {
                        showSnackbar("Username Telah ada");
                        loading.setVisibility(View.GONE);
                        btn_daftar.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showSnackbar("Data gagal di input");
                    loading.setVisibility(View.GONE);
                    btn_daftar.setVisibility(View.VISIBLE);
                }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showSnackbar("Data gagal di input" + error.toString());
                    loading.setVisibility(View.GONE);
                    btn_daftar.setVisibility(View.VISIBLE);
                }
            })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("email", email);
//            params.put("no_telp", telp);
            if (tk.isChecked()) {
                params.put("jenjang","1");
            }else if (sd.isChecked()){
                params.put("jenjang","2");
            }else if (smp.isChecked()){
                params.put("jenjang","3");
            }
            params.put("api", "register");
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
    }
}

