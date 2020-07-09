package com.example.presensifacegeofencing.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Cardtext extends TextView {

    public Cardtext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Cardtext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Cardtext(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Basic-Regular.ttf");
            setTypeface(tf);
        }
    }

}