package com.example.biem.alamien;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.biem.alamien.fragment.About_fragment;
import com.example.biem.alamien.fragment.daftarulang_fragment;
import com.example.biem.alamien.fragment.home_fragment;
import com.example.biem.alamien.fragment.jadwal_fragment;
import com.example.biem.alamien.fragment.pengumuman_fragment;
import com.example.biem.alamien.fragment.profile_fragmen;
import com.example.biem.alamien.serivices.SessionManager;

public class dashboardd extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewFlipper view;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("AL BAITUL AMIEN");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_home);

        sessionManager=new SessionManager(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboardd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id==R.id.keluar){
            // Showing Echo Response Message Coming From Server.
            Toast.makeText(dashboardd.this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
            // Closing the current activity.
            finish();
            // Redirect to Main Login activity after log out.
            Intent intent = new Intent(dashboardd.this, LoginActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new home_fragment();
                break;
            case R.id.nav_profile:
                fragment = new profile_fragmen();
                break;
            case R.id.nav_isidata:
                startActivity(new Intent(getApplicationContext(),isiData.class));
                break;
            case R.id.nav_jadwal:
                fragment = new jadwal_fragment();
                break;
            case R.id.nav_nilai:
                fragment = new jadwal_fragment();
                break;
            case R.id.nav_pengumuman:
                fragment = new pengumuman_fragment();
                break;
            case R.id.nav_daftarulang:
                fragment = new daftarulang_fragment();
                break;
            case R.id.nav_About:
                fragment = new About_fragment();
                break;
            case R.id.nav_logout:
                sessionManager.logout();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.addToBackStack("detail");
            ft.commit();
        }
        //membuat action back berulang hingga activity terahir
        Fragment switc = null;
        if (switc!= null){
            FragmentTransaction fk = getSupportFragmentManager().beginTransaction();
            fk.replace(R.id.screen_area, fragment);
            for (int i=0;i< getSupportFragmentManager().getBackStackEntryCount();i++){
                getSupportFragmentManager().popBackStackImmediate();
            }
            fk.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
