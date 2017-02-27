package com.tubic.testapp.google;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.BaseFragment;

import javax.inject.Inject;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class GoogleSearchFragment extends BaseFragment implements GoogleSearchContract.View {


    @Inject
    GoogleSearchPresenter googleSearchPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerGoogleSearchComponent.builder()
                .appComponent(getAppComponent())
                .googleSearchPresenterModule(new GoogleSearchPresenterModule(this))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_search, container, false);
        googleSearchPresenter.loadNextPage("a");
        return view;
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleSearchPresenter.stop();
    }
}
