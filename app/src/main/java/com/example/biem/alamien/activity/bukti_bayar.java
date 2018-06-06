package com.example.biem.alamien.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biem.alamien.R;
import com.example.biem.alamien.verifikasi_number;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class bukti_bayar extends AppCompatActivity {
    private ImageView setImage;
    private String url="http://192.168.1.12/alamin/WEB/alamin/android/uploadimg.php";
    private Bitmap bitmap;
    Snackbar snackbar;
    EditText ket;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bukti_bayar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon
        setImage = findViewById(R.id.img_set);
        ket = findViewById(R.id.brwse);
        pd = new ProgressDialog(bukti_bayar.this);
    }

    //back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int t = item.getItemId();
        if (t==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btn_browse(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"),999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode==RESULT_OK && data !=null){
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                setImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload(View view) {
        uploadimage();
    }
    public String getStringimage(Bitmap bitmap){
    ByteArrayOutputStream bm = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100,bm);
    byte[] imagebyte = bm.toByteArray();
    String encode = Base64.encodeToString(imagebyte,Base64.DEFAULT);
    return encode;
    }
    private void uploadimage() {
        pd.setMessage("Upload File . . .");
        pd.show();
        String response = null;
        final String finalResponse = response;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s = response.trim();
                if (s.equalsIgnoreCase("loi")){
                    pd.hide();
                    showSnackbar(response);
                    startActivity(new Intent(getApplicationContext(),verifikasi_number.class));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("ErrorResponse", finalResponse);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String keterangan = ket.getText().toString();
                String image = getStringimage(bitmap);

                Map<String,String> params = new HashMap<>();
                params.put("gambar",image);
                params.put("ket",keterangan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();

    }
}
