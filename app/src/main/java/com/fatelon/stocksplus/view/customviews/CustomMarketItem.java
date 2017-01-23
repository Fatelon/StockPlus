package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;

/**
 * Created by User on 22.01.2017.
 */

public class CustomMarketItem extends LinearLayout {

    private ImageView marketItemIcon;

    private TextView marketItemName;

    private FrameLayout itemClickFlash;

    public CustomMarketItem(Context context) {
        super(context);
        init();
    }

    public CustomMarketItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttr(context, attrs);
    }

    public CustomMarketItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttr(context, attrs);
    }

    private void init() {
        inflate(getContext(), R.layout.market_list_view, this);
        this.marketItemIcon = (ImageView)findViewById(R.id.market_item_icon);
        this.marketItemName = (TextView) findViewById(R.id.market_item_name_text);
        itemClickFlash = (FrameLayout) findViewById(R.id.item_click_flash);
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMarketItem);
        String customText = a.getString(R.styleable.CustomMarketItem_market_item_name);
        Drawable drawable = a.getDrawable(R.styleable.CustomMarketItem_market_item_icon);
        if (customText != null) {
            this.marketItemName.setText(customText);
        }
        if (drawable != null) {
            marketItemIcon.setBackgroundDrawable(drawable);
        }
    }
}
