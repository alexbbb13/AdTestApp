package com.adviator.adviatortestapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    BroadcastReceiver br;
    static final String TAG ="adviatortestapp";

    TextView tv;
    Button btStartAds,btInitializeAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.av111439.android.AdService.startAds(getApplicationContext());

        tv = (TextView) findViewById(R.id.tvTextView);
        btStartAds = (Button) findViewById(R.id.btStartAds);
        btStartAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.av111439.android.AdService.initialize(getApplicationContext());
                com.av111439.android.AdService.testStartAds(getApplicationContext());

            }
        });

        btInitializeAds = (Button) findViewById(R.id.btInitializeAds);
        btInitializeAds .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.av111439.android.AdService.initialize(getApplicationContext());


            }
        });

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(BRConstants.PARAM_STATUS, 0);
                Log.d(TAG, "onReceive:  status = " + status);
                String s;
                if (status  == BRConstants.STATUS_INSTALLED || status == BRConstants.STATUS_NOT_INSTALLED ) {
                    s= intent.getStringExtra(BRConstants.PARAM_RESULT);
                    if (s==null || "".equals(s)) s="NO PARAM RESULT";
                    tv.setText(s);
                }
        }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BRConstants.BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);

    }


    protected void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
