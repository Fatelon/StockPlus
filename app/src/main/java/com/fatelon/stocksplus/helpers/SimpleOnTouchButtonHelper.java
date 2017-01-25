package com.fatelon.stocksplus.helpers;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Fatelon on 22.01.2017.
 */

public class SimpleOnTouchButtonHelper implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.getBackground().setAlpha(123);
                break;
            case MotionEvent.ACTION_UP:
                v.getBackground().setAlpha(255);
            default :
                v.getBackground().setAlpha(255);
        }
        return false;
    }

}
