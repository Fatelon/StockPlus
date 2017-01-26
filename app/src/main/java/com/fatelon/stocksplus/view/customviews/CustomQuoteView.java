package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.quotes.OneQuoteDTO;
import com.fatelon.stocksplus.view.callbacks.MyHorizontalViewCallBack;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class CustomQuoteView extends LinearLayout {

    private String quoteId = "";

    private CustomTextView customQuoteTitle;
    private CustomTextView customQuotePrice;
    private CustomTextView customQuoteChageNum;
    private CustomTextView customQuoteChangePercent;
    private CustomTextView customQuoteDelete;

    private MyHorizontalViewCallBack pressBackCallBack = null;

    public CustomQuoteView(Context context) {
        super(context);
        init(context);
    }

    public CustomQuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomQuoteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setDeletVisible() {
        customQuoteDelete.setVisibility(View.VISIBLE);
    }

    public void setDeletGone() {
        customQuoteDelete.setVisibility(View.GONE);
    }

    public void setPressDeleteCallBack(MyHorizontalViewCallBack pressBackCallBack) {
        this.pressBackCallBack = pressBackCallBack;
    }

    public void setQuote(OneQuoteDTO oneQuoteDTO) {
        quoteId = oneQuoteDTO.getId();
        customQuoteTitle.setText(oneQuoteDTO.get2());
        customQuotePrice.setText(oneQuoteDTO.getPrice());
        customQuoteChageNum.setText(oneQuoteDTO.getChageNum());
        customQuoteChangePercent.setText(oneQuoteDTO.getChangePercent());
        customQuoteDelete.setOnClickListener(v -> {if (pressBackCallBack != null) pressBackCallBack.onPressDeleteCross(quoteId);});
        try {
            if (oneQuoteDTO.getChageNum().charAt(0) == '-') customQuoteChageNum.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            else customQuoteChageNum.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            if (oneQuoteDTO.getChangePercent().charAt(0) == '-') customQuoteChangePercent.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            else customQuoteChangePercent.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init(Context context) {
        inflate(getContext(), R.layout.custom_quote_view, this);
        customQuoteTitle = (CustomTextView) findViewById(R.id.custom_quote_title);
        customQuotePrice = (CustomTextView) findViewById(R.id.custom_quote_price);
        customQuoteChageNum = (CustomTextView) findViewById(R.id.custom_quote_chage_num);
        customQuoteChangePercent = (CustomTextView) findViewById(R.id.custom_quote_change_percent);
        customQuoteDelete = (CustomTextView) findViewById(R.id.custom_quote_delete);
    }
}
