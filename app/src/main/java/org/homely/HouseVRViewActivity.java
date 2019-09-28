package org.homely;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class HouseVRViewActivity extends Activity {

    private MonoscopicView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_vrview);

        videoView = (MonoscopicView) findViewById(R.id.video_view);
        VideoUiView videoUi= (VideoUiView) findViewById(R.id.video_ui_view);

        videoView.initialize(videoUi);


        ViewGroup root = (ViewGroup) findViewById(R.id.activity_root);
        for (int i = 0; i < root.getChildCount(); ++i) {
            root.getChildAt(i).setVisibility(View.VISIBLE);
        }
        videoView.loadMedia(getIntent());

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    protected void onPause() {
        videoView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.destroy();
        super.onDestroy();
    }
}
