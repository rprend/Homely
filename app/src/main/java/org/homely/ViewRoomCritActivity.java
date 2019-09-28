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
        crits.add(new Critiques("Plugged In Appliances", "Unplug your appliances when not in use. Even when appliances are turned off they will draw out electricity",-30, 70)); //-20, -25
        crits.add(new Critiques("Windows", "I suggest you put thermal backed curtains over your windows. This will block out sunlight, keep air from entering and escaping your home, and it will lower your energy bills. Curtains like these can be bought for less than $10.", -20, 190));
        crits.add(new Critiques("Recycle Bin", "Add a recycle bin. You can recycle most types of plastic, cardboard, paper, metal, and glass. You can even get paid for recycling aluminum cans and glass bottles. Generally these items are worth $0.05 or $0.10 each. Ink cartridges, scrap metal, and other items can also me redeemed for money at certain facilities.", -20, -25));

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