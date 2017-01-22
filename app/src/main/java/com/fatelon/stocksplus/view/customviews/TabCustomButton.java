package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;

/**
 * Created by User on 21.01.2017.
 */

public class TabCustomButton extends LinearLayout {

    private ImageView icon;

    private TextView name;

    public TabCustomButton(Context context) {
        super(context);
        init();
    }

    public TabCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttr(context, attrs);
    }

    public TabCustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttr(context, attrs);
    }

    private void init() {
        inflate(getContext(), R.layout.custom_tab_button, this);
        this.icon = (ImageView)findViewById(R.id.custom_tab_button_icon);
        this.name = (TextView) findViewById(R.id.custom_tab_button_name);

    }

    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabCustomButton);
        String customText = a.getString(R.styleable.TabCustomButton_nameText);
        Drawable drawable = a.getDrawable(R.styleable.TabCustomButton_tab_icon);
        if (customText != null) {
            name.setText(customText);
        }
        if (drawable != null) {
            icon.setBackgroundDrawable(drawable);
        }
    }

}
