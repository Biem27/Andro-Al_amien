package com.example.biem.alamien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.biem.alamien.R;
import com.example.biem.alamien._sliders.FragmentSlider;
import com.example.biem.alamien._sliders.SliderIndicator;
import com.example.biem.alamien._sliders.SliderPagerAdapter;
import com.example.biem.alamien._sliders.SliderView;
import com.example.biem.alamien.isiData;


public class home_fragment extends android.support.v4.app.Fragment {
    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private ImageButton home,profile,jadwal,nilai,pengumuman,daftarulang,data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_home_fragment, container, false);
        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);
        home = rootView.findViewById(R.id.btn_home);
        profile = rootView.findViewById(R.id.btn_profile);
        jadwal = rootView.findViewById(R.id.btn_jadwal);
        nilai = rootView.findViewById(R.id.btn_nilai);
        pengumuman = rootView.findViewById(R.id.btn_pengumuman);
        daftarulang = rootView.findViewById(R.id.btn_daftarulang);
        data = rootView.findViewById(R.id.data);
        setupSlider();

        //button click
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),isiData.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               android.support.v4.app.Fragment fragment = new home_fragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment = new profile_fragmen();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();

            }
        });

        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment = new jadwal_fragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();

            }
        });
        nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment = new nilai_fragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();
            }
        });
        pengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment = new pengumuman_fragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();
            }
        });
        daftarulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment = new daftarulang_fragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("detail");//action untuk bisa back ke fragment sebelumnya
                ft.commit();
            }
        });

        return rootView;


//        find id button

    }
    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("https://www.jawapos.com/radar/uploads/radarjember/news/2017/12/12/al-baitul-amien-mulai-terima-murid-baru_m_33101.jpeg"));
        fragments.add(FragmentSlider.newInstance("https://i.ytimg.com/vi/P6EcqJ0Fm04/maxresdefault.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-3.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }
}
