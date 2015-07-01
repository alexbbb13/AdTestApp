package com.adviator.adviatortestapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    BroadcastReceiver br, mUIBroadcastReceiver;
    static final String TAG ="ADTest";
    RelativeLayout mLayout;
    Activity mActivity;

    TextView tv;
    Button btStartAds,btInitializeAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
        tv = (TextView) findViewById(R.id.tvTextView);
        btStartAds = (Button) findViewById(R.id.btStartAds);
        btInitializeAds = (Button) findViewById(R.id.btInitializeAds);
        mActivity=this;






        com.av111439.android.AdService.startAds(getApplicationContext());



        btStartAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.av111439.android.AdService.initialize(getApplicationContext());
                com.av111439.android.AdService.testStartAds(getApplicationContext());

            }
        });


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
                    Bundle b = intent.getExtras();
                    ArrayList<ElementView> arrayElements= null;

                    if(null!=b && null!=b.getParcelableArrayList(BRConstants.PARAM_RESULT)) {
                        arrayElements= b.getParcelableArrayList(BRConstants.PARAM_RESULT);

                        if(null!=arrayElements) {
                            for (ElementView e:arrayElements) mLayout.addView(e.createView(mActivity));
                        }
                    }


                }
        }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BRConstants.BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);

        mUIBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int action = intent.getIntExtra(BRConstants.PARAM_UI_ACTION, 0);
                Log.d(TAG, "onReceive:  action = " + action);
                if (action  == BRConstants.ACTION_BUTTON_PRESSED ) {
                    Log.i(TAG, "Button onClick broadcast RECEIVED:button id:"+intent.getIntExtra(BRConstants.PARAM_UI_ID, 0));

                }
            }
        };
        // создаем фильтр для BroadcastReceiver
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(mUIBroadcastReceiver, new IntentFilter(BRConstants.BROADCAST_UI_ACTION));

        //creating new textView
        ArrayList<ElementView> arrayElements = new ArrayList<ElementView>();

        ElementView Elementtv = new ElementView();
        Elementtv.setType(Element.TEXTVIEW);
        Elementtv.setText("Element text 1  view from parcel");
        Elementtv.addRule(RelativeLayout.BELOW, R.id.tvTextView);
        arrayElements.add(Elementtv);


        ElementView Elementtv2 = new ElementView();
        Elementtv2.setType(Element.TEXTVIEW);
        Elementtv2.setText("Element text 2  view from parcel");
        Elementtv2.addRule(RelativeLayout.RIGHT_OF, R.id.tvTextView);
        arrayElements.add(Elementtv2);

        ElementView Element3 = new ElementView();
        Element3.setType(Element.BUTTON);
        Element3.setText("Button 1");
        Element3.setId(1111111);
        Element3.setIntentfiltername(BRConstants.BROADCAST_UI_ACTION);
        Element3.addRule(RelativeLayout.RIGHT_OF, R.id.tvTextView);

        arrayElements.add(Element3);

        //intent to broadcast
        Context _ctx = this;
        Intent intent = new Intent(BRConstants.BROADCAST_ACTION);
        intent.putExtra(BRConstants.PARAM_STATUS, BRConstants.STATUS_INSTALLED);
        intent.putExtra(BRConstants.PARAM_RESULT,  arrayElements);
        _ctx.sendBroadcast(intent);

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
