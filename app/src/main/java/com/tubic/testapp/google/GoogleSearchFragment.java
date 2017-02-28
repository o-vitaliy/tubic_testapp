package com.tubic.testapp.google;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.BaseFragment;
import com.tubic.testapp.common.ImagesAdapter;
import com.tubic.testapp.common.LayoutManagerHelper;
import com.tubic.testapp.common.RecyclerViewScrollListener;
import com.tubic.testapp.common.State;
import com.tubic.testapp.data.Image;

import java.util.List;

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


    ImagesAdapter imagesAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagesAdapter = new ImagesAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.google_list);
        recyclerView.setLayoutManager(LayoutManagerHelper.getLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);

        searchView = (SearchView) view.findViewById(R.id.google_search);
        searchView.setOnQueryTextListener(onQueryTextListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.google_main);
        swipeRefreshLayout.setOnRefreshListener(() -> googleSearchPresenter.refresh());
    }

    @Override
    public void refresh() {
        imagesAdapter.clear();
    }

    @Override
    public void showSearchNoResults() {
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
    public void showSearchResults(List<Image> images, int oldSize, int newItemsCount) {
        imagesAdapter.add(images, oldSize, newItemsCount);
        if (newItemsCount > 0)
            RecyclerViewScrollListener.attach(recyclerView, () -> googleSearchPresenter.loadNextPage());
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

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            googleSearchPresenter.search(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("state", googleSearchPresenter.getSaveState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            googleSearchPresenter.restoreSaveState((State) savedInstanceState.getSerializable("state"));
        }
    }
}