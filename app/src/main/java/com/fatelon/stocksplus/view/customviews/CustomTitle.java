package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;

/**
 * Created by User on 22.01.2017.
 */

public class CustomTitle extends LinearLayout {

    private ImageView customTitleBackButton;

    private TextView customTitleMainText;

    private CustomTextView rightTextView;

    private PressBackCallBack pressBackCallBack = null;

    public CustomTitle(Context context) {
        super(context);
        init();
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttr(context, attrs);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttr(context, attrs);
    }

    public void setRightTextVisible() {
        rightTextView.setVisibility(View.VISIBLE);
    }

    public void setRightTextInvisible() {
        rightTextView.setVisibility(View.INVISIBLE);
    }

    public void setPressBackCallBack(Context context) {
        this.pressBackCallBack = (PressBackCallBack) context;
    }

    public void setCustomText(String customText) {
        this.customTitleMainText.setText(customText);
    }

    private void init() {
        inflate(getContext(), R.layout.custom_title, this);
        this.customTitleBackButton = (ImageView)findViewById(R.id.custom_title_back_button);
        this.customTitleMainText = (TextView) findViewById(R.id.custom_title_main_text);
        this.customTitleBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pressBackCallBack != null)
                    pressBackCallBack.onPressBack();
            }
        });
//        this.customTitleBackButton.setOnClickListener(v -> {if (pressBackCallBack != null) pressBackCallBack.onPressBack();});
        this.rightTextView = (CustomTextView) findViewById(R.id.custom_title_right_text);
    }

    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitle);
        String customText = a.getString(R.styleable.CustomTitle_main_text);
        if (customText != null) {
            this.customTitleMainText.setText(customText);
        }
    }
}
