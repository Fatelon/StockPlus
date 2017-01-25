package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class TabCustomButton extends LinearLayout {

    private Context context;

    private ImageView icon;

    private TextView name;

    private TypedArray typedArray;

    public TabCustomButton(Context context) {
        super(context);
        init(context);
    }

    public TabCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttr(context, attrs);
    }

    public TabCustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        setAttr(context, attrs);
    }

    public void makeBlue(Context ctx) {

        Drawable drawable = typedArray.getDrawable(R.styleable.TabCustomButton_tab_icon);
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.blue), PorterDuff.Mode.SRC_ATOP);
        if (drawable != null) {
            icon.setBackgroundDrawable(drawable);
        }
        name.setTextColor(ContextCompat.getColor(ctx,R.color.blue));
    }

    public void makeWhite(Context ctx) {
        Drawable drawable = typedArray.getDrawable(R.styleable.TabCustomButton_tab_icon);
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.whiteTextColor), PorterDuff.Mode.SRC_ATOP);
        if (drawable != null) {
            icon.setBackgroundDrawable(drawable);
        }
        name.setTextColor(ContextCompat.getColor(ctx, R.color.whiteTextColor));
    }

    private void init(Context context) {
        this.context = context;
        inflate(getContext(), R.layout.custom_tab_button, this);
        this.icon = (ImageView) findViewById(R.id.custom_tab_button_icon);
        this.name = (TextView) findViewById(R.id.custom_tab_button_name);

    }

    private void setAttr(Context context, AttributeSet attrs) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabCustomButton);
        String customText = typedArray.getString(R.styleable.TabCustomButton_nameText);
        Drawable drawable = typedArray.getDrawable(R.styleable.TabCustomButton_tab_icon);
        if (customText != null) {
            name.setText(customText);
        }
        if (drawable != null) {
            icon.setBackgroundDrawable(drawable);
        }
    }

}
