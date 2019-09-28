package org.homely;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.homely.explore_activity.HouseAdapter;
import org.homely.house_activity.HouseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.explore_listview);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.exploreToolbar);
        setSupportActionBar(myToolbar);

        final HouseAdapter adapter = new HouseAdapter(this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                System.out.println("Clicked " + position);
                House clickedHouse = adapter.getHouse(position);

                Intent intent = new Intent(view.getContext(), HouseActivity.class);
                intent.putExtra("House", clickedHouse);
                startActivity(intent);
            }
        });

    }

}
