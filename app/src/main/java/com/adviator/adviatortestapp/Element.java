package com.adviator.adviatortestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexburov on 29.06.15.
 */
public abstract class Element {
    public static final int TEXTVIEW = 1;
    public static final int BUTTON = 2;
    private int type;
    private int id;
    private String intentfiltername;
    private ArrayList<Integer> rules=null;
    private int gravity;


    //private BroadcastReceiver broadcastReceiver=null;
    //private RelativeLayout.LayoutParams layoutParams;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public RelativeLayout.LayoutParams getLayoutParams() {
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<rules.size();i+=2) {
            relativeParams.addRule(rules.get(i), rules.get(i+1));
        }
        return relativeParams;
    }

    public void addRule(int rule, int element_id){
        if (null==rules) rules=new ArrayList<Integer>();
        rules.add(rule);
        rules.add(element_id);
    }


    abstract View createView(Context c);

    public void setIntentfiltername(String intentfiltername) {
        this.intentfiltername = intentfiltername;
    }

    public void setRules(ArrayList<Integer> rules) {
        this.rules = rules;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public String getIntentfiltername() {
        return intentfiltername;
    }

    public ArrayList<Integer> getRules() {
        return rules;
    }

    public int getGravity() {
        return gravity;
    }
}


