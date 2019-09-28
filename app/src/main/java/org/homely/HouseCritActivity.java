package org.homely;

import android.animation.Animator;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class HouseCritActivity extends Activity {
    private static final String TAG = HouseCritActivity.class.getSimpleName();

    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    /** Tracks the file to be loaded across the lifetime of this app. **/
    /** Configuration information for the panorama. **/
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private ImageLoaderTask backgroundImageLoaderTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_crit);


        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);
        panoWidgetView.setEventListener(new ActivityEventListener());
        //panoWidgetView.setDisplayMode(1);
        panoWidgetView.setStereoModeButtonEnabled(false);
        panoWidgetView.setFullscreenButtonEnabled(false);
        panoWidgetView.setInfoButtonEnabled(false);
        String image = getIntent().getStringExtra("image");
        showPano(image);
    }

    private void showPano(String image) {

        // Load the bitmap in a background thread to avoid blocking the UI thread. This operation can
        // take 100s of milliseconds.
        if (backgroundImageLoaderTask != null) {
            // Cancel any task from a previous intent sent to this activity.
            backgroundImageLoaderTask.cancel(true);
        }
        backgroundImageLoaderTask = new ImageLoaderTask();
        backgroundImageLoaderTask.execute(new Pair<String, VrPanoramaView.Options>(image, panoOptions));
    }


    public void capture_crit(View view) {
        view.setVisibility(View.GONE);
        final View edit_card = findViewById(R.id.edit_card);
//        edit_card.setVisibility(View.VISIBLE);
     //   edit_card.setAlpha(1.0f);

        edit_card.animate().translationY(edit_card.getHeight())
                .setDuration(0)
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                edit_card.setVisibility(View.VISIBLE);
                edit_card.animate().translationY(0)
                        .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                        .setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });




        panoWidgetView.setPureTouchTracking(true);

    }

    public void send_crit(View view) {
        final View edit_card = findViewById(R.id.edit_card);
        edit_card.animate().translationY(edit_card.getHeight())
                .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        edit_card.setVisibility(View.INVISIBLE);
                        edit_card.animate().translationY(-edit_card.getHeight())
                                .setDuration(0)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

        Toast toast = Toast.makeText(this, "Critique Sumbitted", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,20);
        toast.show();
        panoWidgetView.setPureTouchTracking(false);
        findViewById(R.id.fab).setVisibility(View.VISIBLE);

    }
    /**
     * Helper class to manage threading.
     */
    class ImageLoaderTask extends AsyncTask<Pair<String, VrPanoramaView.Options>, Void, Boolean> {

        /**
         * Reads the bitmap from disk in the background and waits until it's loaded by pano widget.
         */
        @Override
        protected Boolean doInBackground(Pair<String, VrPanoramaView.Options>... fileInformation) {
            VrPanoramaView.Options panoOptions = null;  // It's safe to use null VrPanoramaView.Options.
            InputStream istr = null;

            AssetManager assetManager = getAssets();
            try {
                istr = assetManager.open(fileInformation[0].first);
                panoOptions = new VrPanoramaView.Options();
                panoOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
            } catch (IOException e) {
                Log.e(TAG, "Could not decode default bitmap: " + e);
                return false;
            }


            //panoWidgetView.setPureTouchTracking(true);
            panoWidgetView.setFlingingEnabled(true);
            panoWidgetView.loadImageFromBitmap(BitmapFactory.decodeStream(istr), panoOptions);
            try {
                istr.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close input stream: " + e);
            }

            return true;
        }
    }

    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            loadImageSuccessful = false;
            Toast.makeText(
                    HouseCritActivity.this, "Error loading pano: " + errorMessage, Toast.LENGTH_SHORT)
                    .show();
            Log.e(TAG, "Error loading pano: " + errorMessage);
        }
    }
}
