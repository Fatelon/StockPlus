package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class CustomColoredStripeBox extends LinearLayout {

    public CustomColoredStripeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_colores_stripe_view, this);
    }
}
