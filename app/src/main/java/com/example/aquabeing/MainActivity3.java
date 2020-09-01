package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity3 extends AppCompatActivity {
    Toolbar toolbar;
    /*NavigationView navigationView;
    private DrawerLayout drawerLayout;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle( MainActivity3.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
*/
       /*navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity3.this);
        toolbar.getMenu().clear();*/


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFrag()).commit();
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.home_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFrag = new HomeFrag();
                            break;
                        case R.id.nav_profile:
                            selectedFrag = new ProfileFrag();
                            break;
                        case R.id.nav_help:
                            selectedFrag = new HelpFrag();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrag).commit();
                    return true;
                }
            };

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
            //return true;
        return false;
    }*/
   /* @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }*/


}