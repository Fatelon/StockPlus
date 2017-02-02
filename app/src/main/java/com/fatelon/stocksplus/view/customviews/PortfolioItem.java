package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.portfolio.PortfolioDTO;

/**
 * Created by Fatelon on 02.02.2017.
 */

public class PortfolioItem extends FrameLayout {

    private Context context;

    RelativeLayout itemContainer;

    private ImageView portfolioMinus;
    private CustomTextView portfolioName;
    private CustomTextView portfolioTotalCost;

    public PortfolioItem(Context context) {
        super(context);
        init(context);
    }

    public PortfolioItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PortfolioItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setMinus(boolean state) {
        if (state) {
            portfolioMinus.setVisibility(VISIBLE);
        } else {
            portfolioMinus.setVisibility(GONE);
        }
    }

    public void setSummary(String coast, String dayProfit, String totalProfit) {

        itemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.mainBackground));
        portfolioName.setText(context.getResources().getString(R.string.portfolio_summary));
        portfolioName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.item_text_size));
    }

    public void setPortfolioView(PortfolioDTO portfolio) {
        portfolioName.setText(portfolio.getName());
        portfolioTotalCost.setText("$" + portfolio.getTotalCost());
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.portfolio_list_view_item, this);

        itemContainer = (RelativeLayout) findViewById(R.id.portfolio_l_v_item_container);
        portfolioMinus = (ImageView) findViewById(R.id.portfolio_l_v_minus);
        portfolioName = (CustomTextView) findViewById(R.id.portfolio_l_v_name);
        portfolioTotalCost = (CustomTextView) findViewById(R.id.portfolio_l_v_total_cost);
    }

}
