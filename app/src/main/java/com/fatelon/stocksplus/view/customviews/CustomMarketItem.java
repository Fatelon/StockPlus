package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;

/**
 * Created by User on 22.01.2017.
 */

public class CustomMarketItem extends LinearLayout {

    private ImageView marketItemIcon;

    private TextView marketItemName;

    private PressBackCallBack pressBackCallBack = null;

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