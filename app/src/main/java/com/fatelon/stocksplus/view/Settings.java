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

/**
 * Created by User on 21.01.2017.
 */

public class Settings extends FragmentActivity {

    private FragmentManager fragmentManager;

    private Button backButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        backButton = (Button) findViewById(R.id.b_button);
        backButton.setOnClickListener(v -> onClickBackButton());
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> onClickLogoutButton(v));
    }

    private void onClickLogoutButton(View v) {
        this.finish();
        Intent intent = new Intent(this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void onClickBackButton() {
        this.finish();
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.settings_container, fragment);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
