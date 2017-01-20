package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.presenter.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 19.01.2017.
 */

public class LoginScreen extends BaseFragment {

    private static String TAG = LoginScreen.class.getSimpleName();

    LoginPresenter loginPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        loginPresenter = new LoginPresenter(view);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @OnClick(R.id.login_button)
    public void onClickLoginButton(View v) {
        Log.d(TAG, "click");
        if (loginPresenter != null) {
            Log.d(TAG, "loginPresenter is not null");
            loginPresenter.onLoginClick();
        } else {
            Log.d(TAG, "loginPresenter is null");
        }
    }
}
