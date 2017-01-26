package com.fatelon.stocksplus.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.MyHorizontalViewCallBack;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.customviews.CustomQuoteView;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class MyHorizontalViewHelper implements PressBackCallBack {

    private Context context;

    private LinearLayout rowOne;
    private LinearLayout rowTwo;

    private Integer rowOneCounter;
    private Integer rowTwoCounter;

    private float dpHeight;
    private float dpWidth;

    private List<CustomQuoteView> deleteList = new ArrayList<CustomQuoteView>();

    private MyHorizontalViewCallBack actionCallBack = null;

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
        rowOneCounter = 0;
        rowTwoCounter = 0;
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

    public void setActionCallBack(MyHorizontalViewCallBack pressBackCallBack) {
        this.actionCallBack = pressBackCallBack;
    }

    public void setGlobalViewSize(LinearLayout globalLayout) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)dpWidth, getH() * 2 + 1);
        globalLayout.setLayoutParams(lp);
    }

    public void addEmptyQuote() {
        CustomTextView emptyQuote = new CustomTextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        emptyQuote.setLayoutParams(layoutParams);
        emptyQuote.setText("+");
        emptyQuote.setTextColor(ContextCompat.getColor(context, R.color.lightGrayBackground));
        emptyQuote.setTextSize(35);
        RelativeLayout newLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(getW(), getH());
        lp.setMargins(0, 0, 2, 2);
        newLayout.setLayoutParams(lp);
        newLayout.addView(emptyQuote);
        newLayout.setOnClickListener(v->{if (actionCallBack != null)actionCallBack.onPressAddNew();});
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

    public void addView(CustomQuoteView newLayout) {
        newLayout.setPressDeleteCallBack(actionCallBack);
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

    @Override
    public void onPressBack() {

    }
}
