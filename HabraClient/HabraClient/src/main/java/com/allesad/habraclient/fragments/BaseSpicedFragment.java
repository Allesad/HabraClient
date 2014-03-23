package com.allesad.habraclient.fragments;

import android.app.Activity;
import android.app.Fragment;

import com.allesad.habraclient.interfaces.ISpiceManagerProvider;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Allesad on 23.03.2014.
 */
public class BaseSpicedFragment extends Fragment {

    private ISpiceManagerProvider mSpiceManagerProvider;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mSpiceManagerProvider = (ISpiceManagerProvider) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement ISpiceManagerProvider interface!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mSpiceManagerProvider = null;
    }

    protected SpiceManager getSpiceManager(){
        return mSpiceManagerProvider.getSpiceManager();
    }
}
