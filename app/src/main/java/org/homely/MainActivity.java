package org.homely;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.homely.explore_activity.ExploreFragment;
import org.homely.house_activity.HouseFragment;

public class MainActivity extends AppCompatActivity implements ExploreFragment.OnFragmentInteractionListener , HouseFragment.OnFragmentInteractionListener {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final House myHouse = new House("User", R.drawable.armory101);
        myHouse.addRoom(new Room("Kitchen", "kitchen_360.jpg"));

        FrameLayout frame = findViewById(R.id.fragmentPlaceholder);
        if (savedInstanceState == null) {
            Fragment newFragment = new ExploreFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragmentPlaceholder, newFragment).commit();
        }

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView menuView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getTitle().toString()) {
                    case "Explore":
                        currentFragment = new ExploreFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentPlaceholder, currentFragment).commit();
                        myToolbar.setTitle("Explore");
                        break;
                    case "My home":
                        currentFragment = new HouseFragment(myHouse);
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.fragmentPlaceholder, currentFragment).commit();
                        myToolbar.setTitle("My home");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
