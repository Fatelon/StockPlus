package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.widget.TableLayout;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 29.01.2017.
 */

public class CustomStockInfoTable extends TableLayout {
    public CustomStockInfoTable(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_stock_info_table, this);
    }
}
