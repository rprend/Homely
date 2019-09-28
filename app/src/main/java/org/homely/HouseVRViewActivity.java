package org.homely;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class HouseVRViewActivity extends Activity {
    private static final String TAG = HouseVRViewActivity.class.getSimpleName();

    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    /** Tracks the file to be loaded across the lifetime of this app. **/
    /** Configuration information for the panorama. **/
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private ImageLoaderTask backgroundImageLoaderTask;

    ViewPager viewPager;
    CardAdaptor adaptor;
    List<Critiques> crits;


    /**
     * Called when the app is launched via the app icon or an intent using the adb command above. This
     * initializes the app and loads the image to render.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_vrview);
        //360 view
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);
        panoWidgetView.setEventListener(new ActivityEventListener());
        //panoWidgetView.setDisplayMode(1);
        panoWidgetView.setStereoModeButtonEnabled(false);
        panoWidgetView.setFullscreenButtonEnabled(false);
        panoWidgetView.setInfoButtonEnabled(false);
        //bottom cards
        crits = new ArrayList<>();
        crits.add(new Critiques("Leaky Fridge", "the fridge leaks water"));
        crits.add(new Critiques("Trashcan Size", "the trashcan is way too big"));
        crits.add(new Critiques("Bad Window", "the window should be better insulated"));

        adaptor = new CardAdaptor(crits, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adaptor);

        // Initial launch of the app or an Activity recreation due to rotation.
        showPano("table_360.jpg");
    }

    /**
     * Load custom images based on the Intent or load the default image. See the Javadoc for this
     * class for information on generating a custom intent via adb.
     */
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

    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoWidgetView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();

        // The background task has a 5 second timeout so it can potentially stay alive for 5 seconds
        // after the activity is destroyed unless it is explicitly cancelled.
        if (backgroundImageLoaderTask != null) {
            backgroundImageLoaderTask.cancel(true);
        }
        super.onDestroy();
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


            panoWidgetView.setPureTouchTracking(true);
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
                    HouseVRViewActivity.this, "Error loading pano: " + errorMessage, Toast.LENGTH_LONG)
                    .show();
            Log.e(TAG, "Error loading pano: " + errorMessage);
        }
    }
}