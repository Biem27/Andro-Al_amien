package com.example.biem.alamien.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.biem.alamien.R;


public class pengumuman_fragment extends Fragment {
Button button;
Fragment fragment=null;
private TextView id, nama, tgl, asal, jml;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate(R.layout.fragment_pengumuman_fragment, container, false);
        id = root.findViewById(R.id.id_daftar);
        nama = root.findViewById(R.id.nama_siswa);
        tgl = root.findViewById(R.id.tanggal_lahir);
        asal = root.findViewById(R.id.asal_sekolah);
        jml = root.findViewById(R.id.nilai);
//        button = root.findViewById(R.id.nilai);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new nilai_fragment();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.screen_area,fragment);
//                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
//                ft.commit();
//            }
//        });
        return root;
    }

}
