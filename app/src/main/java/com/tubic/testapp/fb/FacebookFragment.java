package com.tubic.testapp.fb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.*;
import com.tubic.testapp.data.Image;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FacebookFragment extends BaseFragment implements FacebookContract.View {

    @Inject
    FacebookPresenter facebookPresenter;


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button loginButton;
    private ImagesAdapter imagesAdapter;

    private boolean isLoggedIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        DaggerFacebookComponent.builder()
                .appComponent(getAppComponent())
                .facebookPresenterModule(new FacebookPresenterModule(this))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);

        loginButton = (Button) view.findViewById(R.id.facebook_login);
        loginButton.setOnClickListener(__ -> {
            FacebookLoginActivity.Picker.getInstance(getActivity()).login().subscribe(token -> {
                if (token != null) facebookPresenter.facebookLoggedIn();
            });
        });

        imagesAdapter = new ImagesAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.facebook_list);
        recyclerView.setLayoutManager(LayoutManagerHelper.getLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.facebook_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> facebookPresenter.refresh());

        facebookPresenter.start();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.facebook, menu);

        menu.findItem(R.id.facebook_logout).setVisible(isLoggedIn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.facebook_logout:
                facebookPresenter.facebookLoggedOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyRefreshingComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setVisibleItem(int offset) {
        recyclerView.getLayoutManager().scrollToPosition(offset);
    }

    @Override
    public int getVisibleItem() {
        return Math.max(0, ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
    }


    @Override
    public void facebookLoggedIn() {
        isLoggedIn = true;
        getActivity().invalidateOptionsMenu();

        loginButton.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void facebookLoggedOut() {
        isLoggedIn = false;
        getActivity().invalidateOptionsMenu();

        loginButton.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        imagesAdapter.clear();
        recyclerView.clearOnScrollListeners();
    }

    @Override
    public void showSearchResults(List<Image> images, int oldSize, int newItemsCount) {
        if (newItemsCount > 0) {
            recyclerView.clearOnScrollListeners();
            imagesAdapter.add(images, oldSize, newItemsCount);
            RecyclerViewScrollListener.attach(recyclerView, () -> facebookPresenter.loadNextPage());
        } else {
            recyclerView.clearOnScrollListeners();
        }

    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchNoResults() {

    }

    @Override
    public void onStop() {
        super.onStop();
        facebookPresenter.stop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("state", facebookPresenter.getSaveState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            facebookPresenter.restoreSaveState((FacebookState) savedInstanceState.getSerializable("state"));
        }
    }

}
