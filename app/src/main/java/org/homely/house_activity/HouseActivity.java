package org.homely.house_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.homely.House;
import org.homely.AddRoomCritActivity;
import org.homely.R;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView


                Intent intent = new Intent(view.getContext(), AddRoomCritActivity.class);
                intent.putExtra("image", house.getRooms().get(position).getImagePath());
                startActivity(intent);

            }
        });
    }
}
