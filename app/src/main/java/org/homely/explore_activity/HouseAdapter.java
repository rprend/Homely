package org.homely.explore_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.homely.House;
import org.homely.R;
import org.homely.Room;

import java.util.ArrayList;
import java.util.List;

public class HouseAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<House> houses = new ArrayList<>();

    public HouseAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));

        houses.add(new House("Dmitry", R.drawable.armory101));
        houses.get(0).addRoom(new Room("Dining room", "table_360.jpg"));
        houses.get(0).addRoom(new Room("Bedroom", "ryanroom.jpg"));

        houses.add(new House("Teddy", R.drawable.siebel));
        //houses.add(new House("Sarah", R.drawable.allen));
    }

    @Override
    public int getCount() {
        return houses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.activity_listview, null);
        TextView houseLabel = (TextView)           view.findViewById(R.id.houseLabel);
        ImageView houseImage = (ImageView) view.findViewById(R.id.houseImage);
        House house = houses.get(position);
        houseLabel.setText(house.getTitle());
        houseImage.setBackgroundResource(house.getImage());
        return view;
    }

    public House getHouse(int i) {
        return houses.get(i);
    }
}
