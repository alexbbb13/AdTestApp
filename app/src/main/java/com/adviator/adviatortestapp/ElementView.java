package com.adviator.adviatortestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexburov on 29.06.15.
 */
public class ElementView extends Element implements Parcelable {

    private String text;
    private Context mContext;
    private String className;
    private final String TAG = "ADTest";

    //private int type;
    private int id;
    private String intentfiltername;
    private ArrayList<Integer> rules=new ArrayList<Integer>();
    //private int gravity;


    View createView(Context context) {
        mContext=context;
        switch(super.getType()) {
            case Element.TEXTVIEW:
                Log.d(TAG,"createView:textView");
                TextView textView = new TextView(context);
                textView.setLayoutParams(super.getLayoutParams());
                textView.setText(text);
                Log.e(TAG, "createView:return textview" + textView.toString());
            return textView;
            //break;
            case Element.BUTTON:
                Log.i(TAG, "createView:Button");
                Button button=new Button(context);
                Log.d(TAG, "createView:new button created");
                button.setLayoutParams(super.getLayoutParams());
                button.setText(text);
                id=super.getId();
                intentfiltername=super.getIntentfiltername();
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        Intent intent = new Intent(intentfiltername);
                        intent.putExtra(BRConstants.PARAM_UI_ACTION, BRConstants.ACTION_BUTTON_PRESSED);
                        Log.i(TAG, "Button onClick intent.putExtra, id=" + id);
                        intent.putExtra(BRConstants.PARAM_UI_ID, id);
                        Log.i(TAG, "Button onClick broadcast SENT:"+intentfiltername);
                        mContext.sendBroadcast(intent);
                    }
                });
                Log.i(TAG, "createView:return button" + button.toString());
                return button;
            //break;
        }
        Log.e(TAG,"createView:return NULL");
    return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ElementView(){

    }

    public ElementView(Parcel in) {
        className = in.readString();
        Log.i(TAG, "Constructor: " + this.getClass().getSimpleName() + "; In parcel: " + className);
        text = in.readString();
        super.setType(in.readInt());
        super.setId(in.readInt());
        super.setIntentfiltername(in.readString());
        if (in.readByte() == 0x01) {
            rules = new ArrayList<Integer>();
            in.readList(rules, Integer.class.getClassLoader());
            super.setRules(rules);
        } else {
            rules = null;
            super.setRules(rules);
        }
        super.setGravity(in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getClass().getName());
        dest.writeString(text);
        dest.writeInt(super.getType());
        dest.writeInt(super.getId());
        dest.writeString(super.getIntentfiltername());
        if (super.getRules() == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(super.getRules());
        }
        dest.writeInt(super.getGravity());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ElementView> CREATOR = new Parcelable.Creator<ElementView>() {
        @Override
        public ElementView createFromParcel(Parcel in) {
            return new ElementView(in);
        }

        @Override
        public ElementView[] newArray(int size) {
            return new ElementView[size];
        }
    };

    public String getClassName() {
        return className;
    }

    @Override
    public void setIntentfiltername(String intentfiltername) {
        this.intentfiltername = intentfiltername;
        super.setIntentfiltername(intentfiltername);
    }


}