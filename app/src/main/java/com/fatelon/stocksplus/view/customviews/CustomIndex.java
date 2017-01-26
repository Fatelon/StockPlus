package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.indexes.IndexesItem;

/**
 * Created by Fatelon on 24.01.2017.
 */

public class CustomIndex extends LinearLayout {

    private CustomTextView customIndexTitle;
    private CustomTextView customIndexPrice;
    private CustomTextView customIndexChageNum;
    private CustomTextView customIndexChangePercent;

    private Context context;

    public CustomIndex(Context context) {
        super(context);
        init(context);
    }

    public CustomIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomIndex(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setIndexTitle(String title) {
        customIndexTitle.setText(title);
    }

    public void setIndex(IndexesItem indexesItem) {
        customIndexPrice.setText(indexesItem.getPrice());
        customIndexChageNum.setText(indexesItem.getChageNum());
        customIndexChangePercent.setText(indexesItem.getChangePercent());
        try {
            if (indexesItem.getChageNum().charAt(0) == '+') customIndexChageNum.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            else customIndexChageNum.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            if (indexesItem.getChangePercent().charAt(0) == '+') customIndexChangePercent.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            else customIndexChangePercent.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init(Context context) {
        inflate(getContext(), R.layout.custom_index, this);
        customIndexTitle = (CustomTextView) findViewById(R.id.custom_index_title);
        customIndexPrice = (CustomTextView) findViewById(R.id.custom_index_price);
        customIndexChageNum = (CustomTextView) findViewById(R.id.custom_index_chage_num);
        customIndexChangePercent = (CustomTextView) findViewById(R.id.custom_index_change_percent);
    }
}
