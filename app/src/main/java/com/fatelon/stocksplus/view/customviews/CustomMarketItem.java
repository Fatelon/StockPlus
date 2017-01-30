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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 22.01.2017.
 */

public class CustomMarketItem extends LinearLayout {

    private ImageView marketItemIcon;

    private TextView marketItemName;

    private FrameLayout itemClickFlash;

    private RelativeLayout background;

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

    public void setInstanceWithoutIcon() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins((int)getResources().getDimension(R.dimen.screen_side_padding), 0, 0, 0);
        marketItemName.setLayoutParams(params);
        marketItemIcon.setVisibility(GONE);
    }

    public void setBackground(int color) {
        background.setBackgroundColor(color);
    }

    private void init() {
        inflate(getContext(), R.layout.market_list_view, this);
        this.marketItemIcon = (ImageView)findViewById(R.id.market_item_icon);
        this.marketItemName = (TextView) findViewById(R.id.market_item_name_text);
        this.itemClickFlash = (FrameLayout) findViewById(R.id.item_click_flash);
        background = (RelativeLayout) findViewById(R.id.market_list_view_background);
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
