package com.example.personall.rtav;

import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;


public class Main extends Activity {

    private MediaCodec m_Codec;
    private MediaExtractor m_Extractor;
    private SurfaceView m_SurfaceView;
    private Surface m_Surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_SurfaceView=(SurfaceView)this.findViewById(R.id.surfaceView);

        SurfaceHolder SurfaceHolder = m_SurfaceView.getHolder();
        m_Surface = SurfaceHolder.getSurface();

        int width=352;
        int height=177;
        MediaFormat format = m_Extractor.getTrackFormat(0);
        String mime=format.getString(MediaFormat.KEY_MIME);
        MediaFormat mediaFormat = MediaFormat.createVideoFormat(mime, width, height);

        m_Codec = MediaCodec.createDecoderByType("Video/AVC");
        m_Codec.configure(mediaFormat, m_Surface, null, 0);

        if(m_Codec == null)
        {
            Log.e("Decodc","can't find video info");
            return;
        }

        m_Codec.start();

        try {
            new Thread() {
                public void run()
                {
                    Log.e("Decodc", "Thread is running.1");

                    Socket sc;
                    DataInputStream netInputStream;
                    DataOutputStream netOutputStream;

                    String ip = "192.168.1.236";
                    int port = 3000;

                    try {
                        sc = new Socket(ip, port);

                        netInputStream = new DataInputStream(sc.getInputStream());
                        netOutputStream = new DataOutputStream(sc.getOutputStream());


                        ByteBuffer[] inputBuffers = m_Codec.getInputBuffers();
                        ByteBuffer[] outputBuffers = m_Codec.getOutputBuffers();

                        /*
                        String msg = "hello, android";
                        //netOutputStream.println(msg);
                        while(true) {
                            netOutputStream.writeBytes(msg);
                            netOutputStream.flush();
                            sleep(100);
                        }
                        */

                        byte[] buffcontent = new byte[1204];
                        int mCount=0;
                        int length;
                        while(true)
                        {
                            int ct = netInputStream.read(buffcontent);

                            System.out.println("read "+ct+" bytes."+buffcontent[0]+":"+buffcontent[1]);

                            length = ct;

                            if (ct<0)
                            {
                                System.out.println("read error");
                                break;
                            }

                            int inputBufferIndex = m_Codec.dequeueInputBuffer(-1);
                            if (inputBufferIndex>=0){
                                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                                inputBuffer.clear();
                                inputBuffer.put(buffcontent,0,length);
                                m_Codec.queueInputBuffer(inputBufferIndex, 0, length, mCount * 1000000 / 15, 0);
                                mCount++;
                            }

                            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                            int outputBufferIndex = m_Codec.dequeueOutputBuffer(bufferInfo,0);
                            while (outputBufferIndex >= 0) {
                                m_Codec.releaseOutputBuffer(outputBufferIndex, true);
                                outputBufferIndex = m_Codec.dequeueOutputBuffer(bufferInfo, 0);
                            }

                            System.out.println("read next frame");
                        }
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
