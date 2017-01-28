package com.fatelon.stocksplus.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 28.01.2017.
 */

public class DynamicChartActivity extends FragmentActivity {

    private WebView dynamicChartWebView;

    private ImageView dynamicChartBackButton;

    private boolean staticMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_chart);
        init();
    }

    private void init() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dynamicChartWebView = (WebView) findViewById(R.id.dynamic_chart_web_view);
        dynamicChartBackButton = (ImageView) findViewById(R.id.dynamic_chart_back_button);
        dynamicChartBackButton.setOnClickListener(v -> finish());
    }




}
