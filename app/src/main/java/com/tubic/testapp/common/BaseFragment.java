package com.tubic.testapp.common;

import android.support.v4.app.Fragment;

import com.tubic.testapp.App;
import com.tubic.testapp.di.components.AppComponent;

public abstract class BaseFragment extends Fragment {

    public AppComponent getAppComponent() {
        return ((App) getActivity().getApplication()).appComponent();
    }

}
