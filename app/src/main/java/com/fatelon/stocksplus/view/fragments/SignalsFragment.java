package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomTitle;

/**
 * Created by User on 23.01.2017.
 */

public class SignalsFragment extends BaseSignalsFragment {

    private CustomTitle signalsTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        init(view);

        Bundle args = getArguments();
        int number = args.getInt("number", 0);
        return view;
    }

    private void init(View view) {
        signalsTitle = (CustomTitle) view.findViewById(R.id.signals_fragment_title);
        signalsTitle.setRightTextVisible();
    }
}
