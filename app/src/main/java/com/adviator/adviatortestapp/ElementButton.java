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
public class ElementButton extends Element implements Parcelable {

    private String text;
    private String className;
    private Context mContext;
    private final String TAG = "ADTest";

    //private int type;
    private int id;
    private String intentfiltername;
    private ArrayList<Integer> rules=new ArrayList<Integer>();
    //private int gravity;


    View createView(Context context) {
        final Button button=new Button(context);
        button.setLayoutParams(super.getLayoutParams());
        button.setText(text);
        mContext = context;
        intentfiltername=super.getIntentfiltername();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(intentfiltername);
                intent.putExtra(BRConstants.PARAM_UI_ACTION, BRConstants.ACTION_BUTTON_PRESSED);
                intent.putExtra(BRConstants.PARAM_UI_ID, id);
                mContext.sendBroadcast(intent);
            }
        });
        return button;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ElementButton(){

    }

    public ElementButton(Parcel in) {
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
    public static final Creator<ElementButton> CREATOR = new Creator<ElementButton>() {
        @Override
        public ElementButton createFromParcel(Parcel in) {
            return new ElementButton(in);
        }

        @Override
        public ElementButton[] newArray(int size) {
            return new ElementButton[size];
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

    @Override
    public void setId(int id) {
        this.id = id;
        super.setId(id);
    }



}