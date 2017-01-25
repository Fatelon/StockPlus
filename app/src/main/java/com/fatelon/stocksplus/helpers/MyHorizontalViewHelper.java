package com.fatelon.stocksplus.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomQuoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class MyHorizontalViewHelper {

    private Context context;

    private LinearLayout rowOne;
    private LinearLayout rowTwo;

    private Integer rowOneCounter;
    private Integer rowTwoCounter;

    private float dpHeight;
    private float dpWidth;

    private List<CustomQuoteView> deleteList = new ArrayList<CustomQuoteView>();

    public MyHorizontalViewHelper(LinearLayout rowOne, LinearLayout rowTwo, Context context) {
        this.rowOne = rowOne;
        this.rowTwo = rowTwo;
        rowOneCounter = 0;
        rowTwoCounter = 0;
        this.context = context;
        getScreenSize(context);
    }

    public void clear() {
        rowOne.removeAllViews();
        rowTwo.removeAllViews();
        deleteList.clear();
    }

    public void setDeleteVisibility(boolean visible) {
        for (CustomQuoteView elem : deleteList) {
            if (visible) {
                elem.setDeletVisible();
            } else {
                elem.setDeletGone();
            }
        }
    }

    public void setGlobalViewSize(LinearLayout globalLayout) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)dpWidth, getH() * 2 + 1);
        globalLayout.setLayoutParams(lp);
    }

    public void addView(CustomQuoteView newLayout) {
        deleteList.add(newLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getW(), getH());
        lp.setMargins(0, 0, 2, 2);
        newLayout.setLayoutParams(lp);
        if (rowOneCounter > rowTwoCounter) {
            rowTwo.addView(newLayout);
            newLayout.setBackgroundResource(R.color.light_indexes_background);
            rowTwoCounter++;
        } else {
            rowOne.addView(newLayout);
            newLayout.setBackgroundResource(R.color.indexes_background);
            rowOneCounter++;
        }
    }

    private int getH() {
        return (int)(dpHeight / 4 - 1);
    }

    private int getW() {
        return (int)(dpWidth / 3 - 1);
    }

    private void getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels;// / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels; // displayMetrics.density;
    }
}
