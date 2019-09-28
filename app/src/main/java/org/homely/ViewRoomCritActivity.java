package org.homely;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.homely.FancyVrView.*;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class ViewRoomCritActivity extends Activity {
    private static final String TAG = ViewRoomCritActivity.class.getSimpleName();

    private MonoscopicView videoView;


    ViewPager viewPager;
    CardAdaptor adaptor;
    List<Critiques> crits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_vrview);
        //360 view
        videoView = (MonoscopicView) findViewById(R.id.pano_view);
        final VideoUiView videoUi = (VideoUiView) findViewById(R.id.video_ui_view);

        videoView.initialize(videoUi);
        videoView.loadMedia(getIntent());

        //bottom cards
        crits = new ArrayList<>();
        crits.add(new Critiques("Leaky Fridge", "the fridge leaks water",100, 100));
        crits.add(new Critiques("Trashcan Size", "the trashcan is way too big", 10, 50));
        crits.add(new Critiques("Bad Window", "the window should be better insulated", -30, 70));

        adaptor = new CardAdaptor(crits, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adaptor);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int prev_pos = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset != 0) prev_pos = position;

                Critiques crit = crits.get(position);
               if (prev_pos < position) {
                   if (positionOffset == 0) {
                       videoView.renderer.setYawOffset(crit.getYawOffset());
                       videoView.renderer.setPitchOffset(crit.getPitchOffset());
                   } else {
                       videoView.renderer.setYawOffset(crit.getYawOffset() * (positionOffset));
                       videoView.renderer.setPitchOffset(crit.getPitchOffset() * (positionOffset));
                   }
               } else {
                   if (positionOffset == 0) {
                       videoView.renderer.setYawOffset(crit.getYawOffset() * (positionOffset));
                       videoView.renderer.setPitchOffset(crit.getPitchOffset() * (positionOffset));
                   } else {
                       videoView.renderer.setYawOffset(crits.get(position+1).getYawOffset() * (positionOffset));
                       videoView.renderer.setPitchOffset(crits.get(position+1).getPitchOffset() * (positionOffset));
                   }
               }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void pan(View view) {
        videoView.renderer.setYawOffset(40);
        videoView.renderer.setPitchOffset(49);
    }


    @Override
    protected void onPause() {
        videoView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    protected void onDestroy() {
        videoView.destroy();
        super.onDestroy();
    }



}