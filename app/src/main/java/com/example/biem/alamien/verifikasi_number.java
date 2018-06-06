package com.example.biem.alamien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class verifikasi_number extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifikasi_number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon
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

    public void verifikasi(View view) {
        startActivity(new Intent(getApplicationContext(),dashboardd.class));
    }
}
