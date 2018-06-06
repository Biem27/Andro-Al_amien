package com.example.biem.alamien;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.activity.bukti_bayar;
import com.example.biem.alamien.model.baseUrlApi;
import com.example.biem.alamien.serivices.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    baseUrlApi baseUrlApi = new baseUrlApi();
    private String URL = baseUrlApi.getBaseUrl();
    private String UrlLogin = "api/user";
    private ProgressDialog pd;
    SharedPreferences.Editor editor;
    Snackbar snackbar;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    SharedPreferences sharedpref;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        pd = new ProgressDialog(LoginActivity.this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggin()){
            startActivity(new Intent(getApplicationContext(),dashboardd.class));
            finish();
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void register(View view) {
        Intent masuk = new Intent(getApplicationContext(),register.class);
        startActivity(masuk);
    }

    //logika login

    private void loginRequest(){
        sharedpref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
        pd.setMessage("Signing In . . .");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String response = null;
        Log.d("cek", "Berhasil Login");
//        final String finalResponse = response;
//        editor = sharedpref.edit();
//        editor.putString("username",mEmailView.getText().toString());
//        editor.commit();

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL+UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String succes = jsonObject.getString("succes");
                            if (succes.equals("1")) {
                                JSONObject data_user=jsonObject.getJSONObject("data_user");
                                String id_user = data_user.getString("ID_USER");
                                String nama_user = data_user.getString("NAMA_USER");
                                String username = data_user.getString("EMAIL_USER");
                                String jenjang = data_user.getString("jenjang");
                                sessionManager.createSession(id_user,nama_user,username,jenjang);

//                                startActivity(new Intent(getApplicationContext(),bukti_bayar.class));
                                startActivity(new Intent(getApplicationContext(),dashboardd.class));
                                finish();
                                showSnackbar("Login Berhasil");
                            }else {
                                    showSnackbar("Login Gagal \nperiksa Username & password anda");
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
                        pd.hide();
                        showSnackbar("Error Gagal");
                        error.printStackTrace();
//                        Log.d("ErrorResponse", finalResponse);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", mEmailView.getText().toString());
                params.put("password", mPasswordView.getText().toString());
                params.put("api","login");
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();

    }

    public void masuk(View view) {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        View focusView = null;
        mEmailView.setError(null);
        mPasswordView.setError(null);
        boolean cancel = false;

        if (TextUtils.isEmpty(email)) {
           showSnackbar("Harap isi username anda");
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            mEmailView.requestFocus();
            cancel = true;
        }if (TextUtils.isEmpty(password)) {
            showSnackbar("Harap isi password anda");
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            mPasswordView.requestFocus();
            cancel = true;
        }else if (!isPasswordValid(password)){
            showSnackbar("Ketentuan Password salah");
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            mPasswordView.requestFocus();
            cancel = true;
        }
        else {
            loginRequest();
        }
    }
}

