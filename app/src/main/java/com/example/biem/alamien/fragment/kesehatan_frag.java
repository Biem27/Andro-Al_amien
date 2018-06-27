package com.example.biem.alamien.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.R;
import com.example.biem.alamien.model.baseUrlApi;
import com.example.biem.alamien.serivices.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class kesehatan_frag extends Fragment {
    private Snackbar snackbar;
    private Button gofrag3;
    private ProgressBar pd;
    TextView nm_sekolah,thn_masuk,thn_tamat,almt_sekolah,ksltan,aktifitas;
    private String Urlinsert = "api/isidata";
    com.example.biem.alamien.model.baseUrlApi baseUrlApi = new baseUrlApi();
    private String URL = baseUrlApi.getBaseUrl();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nv = inflater.inflate(R.layout.fragment_kesehatan, container, false);
        pd = new ProgressBar(getContext());
        nm_sekolah = nv.findViewById(R.id.nama_sekolah);
        thn_masuk = nv.findViewById(R.id.tahun_masuk);
        thn_tamat = nv.findViewById(R.id.tahun_tamat);
        almt_sekolah = nv.findViewById(R.id.alamat_sekolah);
        ksltan = nv.findViewById(R.id.kesulitan);
        aktifitas = nv.findViewById(R.id.aktifitas);
        gofrag3 = nv.findViewById(R.id.kefrag3);

        gofrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clik();
            }
        });
        return nv;
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(getActivity().findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();

    }
    public void clik() {
        String nama_s = nm_sekolah.getText().toString();
        String tahun_m = thn_masuk.getText().toString();
        String tahun_t = thn_tamat.getText().toString();
        String alamat_s = almt_sekolah.getText().toString();
        String sulit = ksltan.getText().toString();
        String tifitas = aktifitas.getText().toString();

        View focusView = null;
        boolean cancel = false;
        if (TextUtils.isEmpty(nama_s)) {
            showSnackbar("Isi Username");
            nm_sekolah.setError(getString(R.string.error_field_required));
            focusView = nm_sekolah;
            nm_sekolah.requestFocus();
            cancel = true;
        } else if (TextUtils.isEmpty(tahun_m)) {
            showSnackbar("Isi password");
            thn_masuk.setError(getString(R.string.error_field_required));
            focusView = thn_masuk;
            thn_masuk.requestFocus();
            cancel = true;
        }else if (TextUtils.isEmpty(tahun_t)) {
            showSnackbar("Isi password");
            thn_tamat.setError(getString(R.string.error_field_required));
            focusView = thn_tamat;
            thn_tamat.requestFocus();
            cancel = true;
        }else if (TextUtils.isEmpty(alamat_s)) {
            showSnackbar("Isi password");
            almt_sekolah.setError(getString(R.string.error_field_required));
            focusView = almt_sekolah;
            almt_sekolah.requestFocus();
            cancel = true;
        }else if (TextUtils.isEmpty(sulit)) {
            showSnackbar("Isi password");
            ksltan.setError(getString(R.string.error_field_required));
            focusView = ksltan;
            ksltan.requestFocus();
            cancel = true;
        }else if (TextUtils.isEmpty(tifitas)) {
            showSnackbar("Isi password");
            aktifitas.setError(getString(R.string.error_field_required));
            focusView = aktifitas;
            aktifitas.requestFocus();
            cancel = true;
        }else{
            signupRequest();
        }

    }
    private void signupRequest() {
        pd.setVisibility(View.VISIBLE);
        gofrag3.setVisibility(View.GONE);

        final String nama_s = nm_sekolah.getText().toString().trim();
        final String tahun_m = thn_masuk.getText().toString().trim();
        final String tahun_t = thn_tamat.getText().toString().trim();
        final String alamat_s = almt_sekolah.getText().toString().trim();
        final String sulit = ksltan.getText().toString().trim();
        final String tifitas = aktifitas.getText().toString().trim();


        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String mIduser = String.valueOf(user.get(sessionManager.ID_USER));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + Urlinsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("succes");
                            if (succes.equals("1")) {
//                                startActivity(new Intent(getApplicationContext(),bukti_bayar.class));
                                showSnackbar("Data berhasil di input");
                                android.support.v4.app.Fragment fragment = new riwayatsekolah_frag();
                                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.screen_area,fragment);
                                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                                ft.commit();
                            }else {
                                showSnackbar("Data Gagal input");
                                pd.setVisibility(View.GONE);
                                gofrag3.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showSnackbar("Data gagal di input");
                            pd.setVisibility(View.GONE);
                            gofrag3.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showSnackbar("Data gagal di input, cek koneksimu" + error.toString());
                        pd.setVisibility(View.GONE);
                        gofrag3.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nm_sekolah", nama_s);
                params.put("thn_masuk", tahun_m);
                params.put("thn_tsmst", tahun_t);
                params.put("almt_sekolah", alamat_s);
                params.put("ksulitan", sulit);
                params.put("aktifitas", tifitas);
                params.put("api", "riwayat_sklh");
                params.put("id_user", mIduser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}