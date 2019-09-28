package org.homely.house_activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.homely.House;
import org.homely.R;
import org.homely.Room;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    House house;

    public RoomAdapter(Context applicationContext, House house) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
        this.house = house;
    }
    @Override
    public int getCount() {
        return house.getRooms().size();
    }

    @Override
    public Object getItem(int position) {
        return house.getRooms().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO: change null to parent?
        view = inflater.inflate(R.layout.activity_house_listview, null);
        TextView houseLabel = (TextView)           view.findViewById(R.id.roomLabel);
        ImageView houseImage = (ImageView) view.findViewById(R.id.roomImage);
        Room room = house.getRooms().get(position);
        houseLabel.setText(room.getName());

        // Crops a photo
        Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(), room.getImage_id());
//        Bitmap mBitmap  = Bitmap.createBitmap(bitmap, 0, 0, 1080, 1080);
        houseImage.setImageBitmap(bitmap);
        return view;
    }
}
