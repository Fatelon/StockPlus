package com.fatelon.stocksplus.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.Settings;

/**
 * Created by User on 21.01.2017.
 */

public class Market extends BaseMenuFragment {

    private ImageView settingsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        settingsButton = (ImageView) view.findViewById(R.id.open_settings_button);
        settingsButton.setOnClickListener(v -> onClickSettingsButton(v));
//        ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.market_list_view, mobileArray);
//        ListView listView = (ListView) view.findViewById(R.id.signals_list_view);
//        listView.setAdapter(adapter);
    }

    private void onClickSettingsButton(View v) {
        startActivity(new Intent(context, Settings.class));
    }

}
