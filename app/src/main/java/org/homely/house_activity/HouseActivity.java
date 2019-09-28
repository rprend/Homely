package org.homely.house_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;

import org.homely.House;
import org.homely.R;
import org.homely.explore_activity.HouseAdapter;

public class HouseActivity extends AppCompatActivity {

    House house;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        if (savedInstanceState == null ) {
            this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_right);
        }
//         else {
//            onStartCount = 2;
//        }

        house = (House) getIntent().getSerializableExtra("House");

        final ListView listView = (ListView) findViewById(R.id.houseListview);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.houseToolbar);
//        setSupportActionBar(myToolbar);

        myToolbar.setTitle(house.getTitle());

        final RoomAdapter adapter = new RoomAdapter(this, house);
        listView.setAdapter(adapter);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Get the selected item text from ListView
//                System.out.println("Clicked " + position);
//                House clickedHouse = adapter.getHouse(position);
//
//                Intent intent = new Intent(view.getContext(), HouseActivity.class);
//                intent.putExtra("House", clickedHouse);
//                startActivity(intent);
//
//            }
//        });
    }
}
