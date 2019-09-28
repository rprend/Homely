package org.homely.FancyVrView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.homely.MonoscopicView;
import org.homely.R;

public class FancyViewRoomCritActivity extends Activity {
    private MonoscopicView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_view_room_crit);

        videoView = (MonoscopicView) findViewById(R.id.video_view);
        VideoUiView videoUi = (VideoUiView) findViewById(R.id.video_ui_view);

        videoView.initialize(videoUi);
        videoView.loadMedia(getIntent());
    }


    public void pan_left(View view) {
        //videoView.TouchTracker.accumulatedTouchOffsetDegrees
        //videoView.renderer.setYawOffset(-40);
        //float prev_pOff = videoView.renderer.getPitchOffset();
        //videoView.renderer.setPitchOffset(40);
        //float[] prev_yOff = videoView.renderer.getYawOffset();
        //Matrix.rotateM(prev_yOff, 0, 40, 0, 1, 0);
    }

    /**
     * Normal apps don't need this. However, since we use adb to interact with this sample, we
     * want any new adb Intents to be routed to the existing Activity rather than launching a new
     * Activity.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // Save the new Intent which may contain a new Uri. Then tear down & recreate this Activity to
        // load that Uri.
        setIntent(intent);
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    protected void onPause() {
        // MonoscopicView is a GLSurfaceView so it needs to pause & resume rendering. It's also
        // important to pause MonoscopicView's sensors & the video player.
        videoView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.destroy();
        super.onDestroy();
    }
}
