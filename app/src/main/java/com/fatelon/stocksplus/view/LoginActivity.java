package com.fatelon.stocksplus.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.fragments.LoginScreen;

/**
 * Created by User on 21.01.2017.
 */

public class LoginActivity extends FragmentActivity implements LoadingCallBack {

    private FragmentManager fragmentManager;

    private FrameLayout loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);

        init();



        fragmentManager = getSupportFragmentManager();
        replaceFragment(new LoginScreen(), false);



    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    private void init() {
        loadingIndicator = (FrameLayout) findViewById(R.id.loading_indicator);
    }

    @Override
    public void showLoading() {
//        prBar.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
//        prBar.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
    }

    public void login() {

    }
}
