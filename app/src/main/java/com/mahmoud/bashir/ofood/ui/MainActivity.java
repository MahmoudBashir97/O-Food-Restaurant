package com.mahmoud.bashir.ofood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mahmoud.bashir.ofood.Fragments.HomeFragment;
import com.mahmoud.bashir.ofood.Fragments.LikeFragment;
import com.mahmoud.bashir.ofood.Fragments.ProfileFragment;
import com.mahmoud.bashir.ofood.Fragments.SearchFragment;
import com.mahmoud.bashir.ofood.R;

public class MainActivity extends FragmentActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private Fragment frag;
    private BottomNavigationView mainnav;
    FrameLayout mainframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainframe=(FrameLayout) findViewById(R.id.main_frame);
        mainnav=(BottomNavigationView) findViewById(R.id.main_nav);

        mainnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home_menu:
                        frag=new HomeFragment();
                        setFragment(frag);
                        return true;
                    case R.id.search_menu:

                        frag=new SearchFragment();
                        setFragment(frag);
                        return true;
                    case R.id.fav_menu:

                        frag=new LikeFragment();
                        setFragment(frag);
                        return true;
                    case R.id.profile_menu:

                        frag=new ProfileFragment();
                        setFragment(frag);
                        return true;

                }
                return false;
            }
        });

        mainnav.setSelectedItemId(R.id.home_menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home_menu:

                frag=new HomeFragment();
                setFragment(frag);
                return true;
            case R.id.search_menu:

                frag=new SearchFragment();
                setFragment(frag);
                return true;
            case R.id.fav_menu:

                frag=new LikeFragment();
                setFragment(frag);
                return true;
            case R.id.profile_menu:

                frag=new ProfileFragment();
                setFragment(frag);
                return true;

        }
        return false;
    }

    private void setFragment(Fragment frag) {
        FragmentManager Fragmang=getSupportFragmentManager();
        FragmentTransaction Fragtr=Fragmang.beginTransaction();
        Fragtr.replace(R.id.main_frame,frag);
        Fragtr.commit();


    }
}
