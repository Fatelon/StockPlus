package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fatelon.stocksplus.R;

/**
 * Created by User on 21.01.2017.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        init();
        applyCustomFont(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        applyCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        applyCustomFont(context, attrs);
    }

    private void init() {

    }

    private void applyCustomFont(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        Integer fontType = a.getInteger(R.styleable.CustomTextView_font_type, 1);
        switch(fontType) {
            case 1: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirLTStd-Book.otf")); break;
            case 2: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirLTStd-Heavy.otf")); break;
            case 3: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirLTStd-Light.otf")); break;
            case 4: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirLTStd-Medium.otf")); break;
            case 5: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirNextLTPro-Demi.otf")); break;
            case 6: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirNextLTPro-DemiCn.otf")); break;
            case 7: setTypeface(Typeface.createFromAsset(context.getAssets(), "AvenirNextLTPro-Regular.otf")); break;
        }
    }
}
