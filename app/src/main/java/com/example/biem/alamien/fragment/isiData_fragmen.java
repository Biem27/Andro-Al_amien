package com.example.biem.alamien.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.R;

import java.util.HashMap;
import java.util.Map;


public class isiData_fragmen extends Fragment {
    private Snackbar snackbar;
    private Button gofrag2;
    private ProgressDialog pd;
    private static String S_URL ="http://192.168.43.207/alamin/WEB/alamin/android/register.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nv =  inflater.inflate(R.layout.fragment_isi_data_fragmen, container, false);
        pd = new ProgressDialog(getContext());
        gofrag2 = nv.findViewById(R.id.kefrag2);


        gofrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupRequest();
            }
        });

        return nv;
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(getActivity().findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();

    }
    private void signupRequest(){
        pd.setMessage("Wait Second . . .");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, S_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();
                        //Response
                        showSnackbar(response);

                        if(response.equals("Successfully Signed In")) {
                            Fragment fragment = new riwayatsekolah_frag();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.isidata,fragment);
                            ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                            ft.commit();
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

//                params.put("username", username.getText().toString());
//                params.put("password", password.getText().toString());
//                params.put("name", signUpName.getText().toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

}
