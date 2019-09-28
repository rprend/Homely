package org.homely;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.homely.explore_activity.ExploreFragment;
import org.homely.explore_activity.HouseAdapter;
import org.homely.house_activity.HouseActivity;

public class MainActivity extends AppCompatActivity implements ExploreFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView menuView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return true;
            }
        });



        Toolbar myToolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
