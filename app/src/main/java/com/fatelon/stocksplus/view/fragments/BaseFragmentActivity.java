package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;

/**
 * Created by User on 22.01.2017.
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements PressBackCallBack {

    protected int container = R.id.base_fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //
    // Public methods.
    //

    /**
     * Adds a {@link Fragment} to the container.
     *
     * @param fragment       the new {@link Fragment} to add.
     * @param addToBackStack true to add transaction to back stack; false otherwise.
     */
    public void addFragment(Fragment fragment, boolean addToBackStack) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Replaces a {@link Fragment} in the container.
     *
     * @param fragment         the new {@link Fragment} used to replace the current.
     * @param addToBackStack   true to add transaction to back stack; false otherwise.
     * @param popPreviousState true to pop the previous state from the back stack; false otherwise.
     */
    public void replaceFragment(Fragment fragment, boolean addToBackStack, boolean popPreviousState) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (popPreviousState) {
//                fragmentManager.popBackStackImmediate();
//                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
            }
            fragmentManager = getSupportFragmentManager();
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(container, fragment);
            if (addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Shows a {@link DialogFragment}.
     *
     * @param fragment the new {@link DialogFragment} to show.
     */
    public void showDialogFragment(DialogFragment fragment) {
        fragment.show(getSupportFragmentManager(), null);
    }


    public void popFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void onPressBack() {
        popFragment();
    }
}
