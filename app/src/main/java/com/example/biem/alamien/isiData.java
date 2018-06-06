package com.example.biem.alamien;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.biem.alamien.fragment.isiData_fragmen;

public class isiData extends AppCompatActivity {
private EditText siswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Fragment fragment = new isiData_fragmen();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.isidata,fragment).commit();



    }

    public void kirim(View view) {

    }
}
