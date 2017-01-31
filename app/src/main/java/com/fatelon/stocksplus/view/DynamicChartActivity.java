package com.fatelon.stocksplus.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by Fatelon on 28.01.2017.
 */

public class DynamicChartActivity extends FragmentActivity {

    private RelativeLayout titleLayout;

    private WebView dynamicChartWebView;

    private CustomTextView changeModButton;

    private ImageView dynamicChartBackButton;

    private ImageViewTouch dynamicChartView;

    private boolean staticMode = true;

    private String chartUrl = "";

    private String currentStockName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_chart);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            chartUrl = b.getString("chart_url");
            currentStockName = b.getString("stock_name");
        }
        init();
        checkMode();
    }

    private void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titleLayout = (RelativeLayout) findViewById(R.id.dynamic_chart_title);
        dynamicChartWebView = (WebView) findViewById(R.id.dynamic_chart_web_view);
        dynamicChartWebView.setWebChromeClient(new WebChromeClient());
        dynamicChartWebView.getSettings().setJavaScriptEnabled(true);
        dynamicChartBackButton = (ImageView) findViewById(R.id.dynamic_chart_back_button);
        dynamicChartBackButton.setOnClickListener(v -> {
            try {
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        dynamicChartView = (ImageViewTouch) findViewById(R.id.dynamic_chart_image_view);
        dynamicChartView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        changeModButton = (CustomTextView) findViewById(R.id.dynamic_chart_right_text);
        changeModButton.setOnClickListener(v->{staticMode = !staticMode; checkMode();});
    }

    private void checkMode() {
        if (staticMode) {
            changeModButton.setText(getResources().getString(R.string.dynamic_chart_dynamic));
            dynamicChartView.setVisibility(View.VISIBLE);
            dynamicChartWebView.setVisibility(View.GONE);

//            Picasso.with(this)
//                .load(chartUrl)
//                .into(dynamicChartView, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
////                        chartLoadingIndicator.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
////                        chartLoadingIndicator.setVisibility(View.GONE);
//                    }
//                });

            Glide.with(this)
                    .load(chartUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(dynamicChartView);

        } else {
            changeModButton.setText(getResources().getString(R.string.dynamic_chart_static));
            dynamicChartView.setVisibility(View.GONE);
            dynamicChartWebView.setVisibility(View.VISIBLE);
            dynamicChartWebView.loadData(getJavascriptCode(), "text/html", "utf-8");
        }
    }

    private String getJavascriptCode() {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dpHeight = (int)((displayMetrics.heightPixels ) / displayMetrics.density - titleLayout.getHeight());
        int dpWidth = (int)(displayMetrics.widthPixels / displayMetrics.density);

        return "<script type=\"text/javascript\" src=\"https://s3.tradingview.com/tv.js\"> \n" +
                "\t\n" +
                "</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "\tnew TradingView.widget({\"width\": \""+ dpWidth + "\", \"height\": \"" + dpHeight + "\",\"symbol\": \"" + currentStockName + "\",\n" +
                "\t\"interval\": \"1D\",\"timezone\": \"Etc/UTC\",\"theme\": \"White\",\"style\": \"1\",\"locale\": \"en\",\n" +
                "\t\"toolbar_bg\": \"#f1f3f6\",\"enable_publishing\": \"false\",\"save_image\": \"false\",\"hideideas\": \"true\"});\n" +
                "</script>";
    }


}
