package com.fatelon.stocksplus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.customviews.CustomMarketItem;
import com.fatelon.stocksplus.view.customviews.CustomTitle;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class Settings extends FragmentActivity implements PressBackCallBack {

    private FragmentManager fragmentManager;

    private CustomTitle customTitle;

    private CustomMarketItem licenceView;
    private CustomMarketItem aboutView;
    private CustomMarketItem emailView;
    private CustomMarketItem rateView;

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> onClickLogoutButton(v));
        customTitle = (CustomTitle) findViewById(R.id.settings_title);
        customTitle.setPressBackCallBack(this);
        licenceView = (CustomMarketItem) findViewById(R.id.settings_licence_view);
        licenceView.setInstanceWithoutIcon();
        licenceView.setBackground(ContextCompat.getColor(this, R.color.mainBackground));
        licenceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        aboutView = (CustomMarketItem) findViewById(R.id.settings_about_view);
        aboutView.setInstanceWithoutIcon();
        aboutView.setBackground(ContextCompat.getColor(this, R.color.mainBackground));
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        emailView = (CustomMarketItem) findViewById(R.id.settings_email_view);
        emailView.setInstanceWithoutIcon();
        emailView.setBackground(ContextCompat.getColor(this, R.color.mainBackground));
        emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rateView = (CustomMarketItem) findViewById(R.id.settings_rate_view);
        rateView.setInstanceWithoutIcon();
        rateView.setBackground(ContextCompat.getColor(this, R.color.mainBackground));
        rateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void onClickLogoutButton(View v) {
        PreferencesHelper.storeIsUserLogin(this, false);
        try {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            if (MenuActivity.menuActivity != null) MenuActivity.menuActivity.finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.settings_container, fragment);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPressBack() {
        this.finish();
    }
}
