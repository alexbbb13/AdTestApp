package com.adviator.adviatortestapp;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by alexburov on 29.06.15.
 */
public class ElementTextView extends Element {

    private String text;

    @Override
    View createView(Context context) {
        TextView textView=new TextView(context);
        textView.setLayoutParams(super.getLayoutParams());
        textView.setText(text);
        return textView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
