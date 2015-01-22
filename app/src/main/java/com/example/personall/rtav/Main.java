package com.example.personall.rtav;

import android.app.Activity;
//*
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//*/
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;


public class Main extends Activity {

    //*
    private MediaCodec m_Codec;
    private MediaExtractor m_Extractor;
    private SurfaceView m_SurfaceView;
    private Surface m_Surface;
    private SurfaceHolder m_SurfaceHolder;

    private Camera cam;
    //*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*
        m_SurfaceView=(SurfaceView)this.findViewById(R.id.surfaceView);

        m_SurfaceHolder = m_SurfaceView.getHolder();
        m_SurfaceHolder.addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                try {
                    new Thread() {
                        public void run()
                        {
                            Log.e("Decodc", "Thread is running.2");
                            m_Surface = SurfaceHolder.getSurface();


                            //*
                            cam = Camera.open();
                            cam.setPreviewDisplay(SurfaceHolder);
                            Camera.Parameters parameters = cam.getParameters();
                            parameters.setFlashMode("off"); // 无闪光灯
                            parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                            parameters.setPreviewFormat(ImageFormat.YV12);
                            int camWidth=352;
                            int camHeight=177;
                            parameters.setPictureSize(camWidth, camHeight);
                            parameters.setPreviewSize(camWidth, camHeight);
                            //这两个属性 如果这两个属性设置的和真实手机的不一样时，就会报错
                            cam.setParameters(parameters);
                        }
                    }.start();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {

            }
        });
        m_Surface = m_SurfaceHolder.getSurface();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
