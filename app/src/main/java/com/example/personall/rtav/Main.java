package com.example.personall.rtav;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        while(true)
                        {
                            int ct = netInputStream.read(buffcontent);

                            System.out.println("read %");
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }



                    Log.e("Decodc", "Thread is running.begin");
                    try {
                        Log.e("Decodc", "Thread is running.");
                        System.out.println("Runnable running ");
                        sleep(10);
                    }
                    catch (Exception e)
                    {
                        Log.e("Thread", "Thread error occour.");
                        e.printStackTrace();
                    }
                    System.out.println("Thread Running end!");
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
