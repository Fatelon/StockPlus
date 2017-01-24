package com.fatelon.stocksplus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.customviews.CustomTitle;

/**
 * Created by User on 21.01.2017.
 */

public class Settings extends FragmentActivity implements PressBackCallBack {

    private FragmentManager fragmentManager;

    private CustomTitle customTitle;

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
    }

    private void onClickLogoutButton(View v) {
        PreferencesHelper.storeIsUserLogin(this, false);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        try {
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
