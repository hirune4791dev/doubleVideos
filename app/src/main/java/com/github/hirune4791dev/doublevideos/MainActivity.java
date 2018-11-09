package com.github.hirune4791dev.doublevideos;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

/*
 * Reference URL list:
 *  http://pentan.info/android/app/sample/mediaplayer.html
 *  http://blog.tappli.com/article/44620525.html
 */

public class MainActivity extends Activity implements SurfaceHolder.Callback{
    private static final String TAG = "doubleVideos";
    private static final String RIGHT_MP4_FILE  = "video/right.mp4";
    private static final String LEFT_MP4_FILE   = "video/left.mp4";

    private SurfaceHolder   right_holder,left_holder2;
    //private PowerManager.WakeLock lock;
    private int prev_status = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView mPreview;
        /* Set Full screen flag */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* Support Transparent color */
        //getWindow().setFormat(PixelFormat.TRANSPARENT);
        getWindow().setFormat(PixelFormat.RGBX_8888);
        /* No window title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //layout_height
        mPreview = (SurfaceView) findViewById(R.id.surfaceView1);
        //mPreview.setSecure(true);
        right_holder = mPreview.getHolder();
        //holder1.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        right_holder.addCallback(this);

        mPreview = (SurfaceView) findViewById(R.id.surfaceView2);
        //mPreview.setSecure(true);
        left_holder2 = mPreview.getHolder();
        //holder2.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        left_holder2.addCallback(this);

        //PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        //lock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "doubleVideos");

        Log.i(TAG, "Create");
    }

    public void mediaPlay(MediaPlayer mp, SurfaceHolder paramSurfaceHolder, String mediaPath) {
        try {
            mp.setLooping(true);
            mp.setDataSource(mediaPath);
            //mp.setDisplay(paramSurfaceHolder);
            mp.prepare();
            mp.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        Log.i(TAG, "Enter");
        MediaPlayer mp = new MediaPlayer();
        String mediaPath;

        if(paramSurfaceHolder==right_holder){
            Log.i(TAG, "right_holder");
            mediaPath = System.getenv("EXTERNAL_STORAGE") + "/" + RIGHT_MP4_FILE;
            mp.setDisplay(paramSurfaceHolder);
            mp.setVolume((float)1,(float)0);
        } else {
            Log.i(TAG, "left_holder");
            mediaPath = System.getenv("EXTERNAL_STORAGE") + "/" + LEFT_MP4_FILE;
            mp.setDisplay(paramSurfaceHolder);
            mp.setVolume((float)0,(float)1);
        }
        //lock.acquire();
        mediaPlay(mp,paramSurfaceHolder,mediaPath);
    }

    @Override
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
                               int paramInt2, int paramInt3) {
        Log.i(TAG, "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        Log.i(TAG, "surfaceDestroyed()");
        //if(lock.isHeld()) lock.release();
    }
}
