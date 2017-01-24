package com.fatelon.stocksplus.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.LoadingCallBack;
import com.fatelon.stocksplus.view.callbacks.UserActionsCallBack;
import com.fatelon.stocksplus.view.fragments.BaseFragmentActivity;
import com.fatelon.stocksplus.view.fragments.LoginScreen;
import com.fatelon.stocksplus.view.fragments.RegistrationScreen;

/**
 * Created by User on 21.01.2017.
 */

public class LoginActivity extends BaseFragmentActivity implements LoadingCallBack, UserActionsCallBack {

    private FrameLayout loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        replaceFragment(new LoginScreen(), false, true);
    }

    private void init() {
        container = R.id.container;
        loadingIndicator = (FrameLayout) findViewById(R.id.loading_indicator);
    }

    @Override
    public void showLoading() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void loginAction() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void forgotPasswordAction() {

    }

    @Override
    public void registrationAction() {
        replaceFragment(new RegistrationScreen(), true, false);
    }

}
