package com.example.personall.rtav;

import android.app.Activity;
//*
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

                            /*
                            MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc",352, 288);
                            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
                            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 24);
                            mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
                            //mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
                            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
                            //mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar);
                            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
                            */
                            //*
                            Log.e("decoder","decode wile begin_3");
                        try {
                            m_Extractor  = new MediaExtractor();
                            String fileString="/storage/sdcard/1.264";
                            m_Extractor.setDataSource(fileString);

                            Log.e("CODEC","count:"+m_Extractor.getTrackCount());

                            return;

/*
                            for (int i = 0; i < m_Extractor.getTrackCount(); i++) {

                                MediaFormat format = m_Extractor.getTrackFormat(i);
                                String mime = format.getString(MediaFormat.KEY_MIME);
                                if (mime.startsWith("video/")) {
                                    m_Extractor.selectTrack(i);
                                    m_Codec = MediaCodec.createDecoderByType(mime);
                                    m_Codec.configure(format, m_Surface, null, 0);
                                    break;
                                }

                            }
*/

                            /*
                            m_Codec = MediaCodec.createDecoderByType("video/avc");
                            Log.e("decoder","decode wile begin_4");
                            m_Codec.configure(mediaFormat, m_Surface, null, 0);
                            */
                            /*
                            if(m_Codec == null)
                            {
                                Log.e("Decodc","can't find video info");
                                return;
                            }

                            Log.e("decoder","decode wile begin_1");
                            m_Codec.start();
                            //*/

                            /*
                            Socket sc;
                            DataInputStream netInputStream;
                            DataOutputStream netOutputStream;

                            //String ip = "192.168.1.236";
                            String ip = "192.168.0.61";
                            int port = 3000;

                            Log.e("decoder","decode wile begin_2");






                                sc = new Socket(ip, port);

                                netInputStream = new DataInputStream(sc.getInputStream());
                                netOutputStream = new DataOutputStream(sc.getOutputStream());
*/
                                /*
                                ByteBuffer[] inputBuffers = m_Codec.getInputBuffers();
                                ByteBuffer[] outputBuffers = m_Codec.getOutputBuffers();
                                //*/
/*
                                byte[] buffcontent = new byte[100*1204];

                                int mCount=0;
                                int length;

                                while(true)
                                {
                                    //int ct = netInputStream.read(buffcontent);

                                    System.out.println("__read "+1+" bytes."+buffcontent[0]+":"+buffcontent[1]);

                                    //length = ct;

                                    int inputBufferIndex = m_Codec.dequeueInputBuffer(-1);
                                    if (inputBufferIndex>=0){
                                        ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];

                                        int sampleSize = m_Extractor.readSampleData(inputBuffer, 0);

                                        m_Codec.queueInputBuffer(inputBufferIndex, 0, sampleSize,
                                                m_Extractor.getSampleTime(), 0);
                                        m_Extractor.advance();


                                        System.out.println("__read: "+sampleSize+" bytes."+buffcontent[0]+":"+buffcontent[1]);

                                        inputBuffer.clear();
                                        inputBuffer.put(buffcontent,0,length);
                                        m_Codec.queueInputBuffer(inputBufferIndex, 0, length, mCount * 1000000 / 25, 0);
                                        mCount++;

                                    }

                                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                                    int outputBufferIndex = m_Codec.dequeueOutputBuffer(bufferInfo,0);
                                    if (outputBufferIndex >= 0) {
                                        m_Codec.releaseOutputBuffer(outputBufferIndex, true);
                                        outputBufferIndex = m_Codec.dequeueOutputBuffer(bufferInfo, 0);
                                    }

                                    System.out.println("read next frame");
                                }
                        //*/
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
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


        //m_SurfaceHolder.setOnPreparedListener();
        //m_Surface.setOnPreparedListener();

        //*/


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
