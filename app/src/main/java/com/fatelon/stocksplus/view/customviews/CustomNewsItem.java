package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;

import static com.fatelon.stocksplus.Constants.NEWS_TEXT_MAX_LENGTH;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class CustomNewsItem extends LinearLayout {

    private FrameLayout itemClickFlash;

    private CustomTextView customNewsItemText;
    private CustomTextView customNewsItemTime;

    public CustomNewsItem(Context context) {
        super(context);
        init();
    }

    public CustomNewsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttr(context, attrs);
    }

    public CustomNewsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttr(context, attrs);
    }

    public void setTextAndTime(String text, String time) {
        Integer n = NEWS_TEXT_MAX_LENGTH;
        if (text.length() > n) {
            text = text.substring(0, n);
            text += "..";
        }
        customNewsItemText.setText(text);
        customNewsItemTime.setText(time);
    }



    private void init() {
        inflate(getContext(), R.layout.custom_news_item_view, this);
        this.customNewsItemText = (CustomTextView)findViewById(R.id.custom_news_item_text);
        this.customNewsItemTime = (CustomTextView) findViewById(R.id.custom_news_item_time);
        this.itemClickFlash = (FrameLayout) findViewById(R.id.news_item_click_flash);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        itemClickFlash.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        itemClickFlash.setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        itemClickFlash.setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        itemClickFlash.setVisibility(View.GONE);
                        break;
                    default :
//                        itemClickFlash.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    private void setAttr(Context context, AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMarketItem);
//        String customText = a.getString(R.styleable.CustomMarketItem_market_item_name);
//        Drawable drawable = a.getDrawable(R.styleable.CustomMarketItem_market_item_icon);
//        if (customText != null) {
//            this.marketItemName.setText(customText);
//        }
//        if (drawable != null) {
//            marketItemIcon.setBackgroundDrawable(drawable);
//        }
    }
}
