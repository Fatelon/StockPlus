package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomTitle;

/**
 * Created by Fatelon on 25.01.2017.
 */

public class NewsesWebViewFragment extends BaseFragment {

    private CustomTitle newsesWebViewFragmentTitle;

    private WebView newsesMainWebView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newses_web_view, container, false);
        init(view);

        Bundle args = getArguments();
        String url = args.getString("url");
        if (url != null) {
            newsesMainWebView.setWebViewClient(new WebViewClient());
            newsesMainWebView.getSettings().setJavaScriptEnabled(true);
            newsesMainWebView.loadUrl(url);
        }
        return view;
    }

    private void init(View view) {
        newsesWebViewFragmentTitle = (CustomTitle) view.findViewById(R.id.newses_web_view_fragment_title);
        newsesWebViewFragmentTitle.setCustomText(context.getResources().getString(R.string.tab_news));
        newsesMainWebView = (WebView) view.findViewById(R.id.newses_main_web_view);
        newsesWebViewFragmentTitle.setPressBackCallBack(context);
    }
}
