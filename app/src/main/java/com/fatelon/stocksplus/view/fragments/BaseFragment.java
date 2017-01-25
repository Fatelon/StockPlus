package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Fatelon on 19.01.2017.
 */

public class BaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
