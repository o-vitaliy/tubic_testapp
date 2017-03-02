package com.tubic.testapp.google;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.BaseFragment;
import com.tubic.testapp.common.ImageEventProvider;
import com.tubic.testapp.common.ImagesAdapter;
import com.tubic.testapp.common.LayoutManagerHelper;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.common.RecyclerViewScrollListener;
import com.tubic.testapp.common.State;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.image.ImageActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class GoogleSearchFragment extends BaseFragment implements GoogleSearchContract.View {

    private static final int REQUEST_CODE = 100;

    @Inject
    GoogleSearchPresenter googleSearchPresenter;


    ImagesAdapter imagesAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;

    private BroadcastReceiver imageChangeBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            googleSearchPresenter.validateFavorite(intent.getStringExtra(ImageEventProvider.EXTRA_LINK));
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerGoogleSearchComponent.builder()
                .appComponent(getAppComponent())
                .googleSearchPresenterModule(new GoogleSearchPresenterModule(this))
                .build().inject(this);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(imageChangeBroadCastReceiver, new IntentFilter(ImageEventProvider.EVENT_IMAGE_CHANGED));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagesAdapter = new ImagesAdapter(viewItemClickListener, favoriteItemClickListener);

        recyclerView = (RecyclerView) view.findViewById(R.id.google_list);
        recyclerView.setLayoutManager(LayoutManagerHelper.getLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);

        searchView = (SearchView) view.findViewById(R.id.google_search);
        searchView.setOnQueryTextListener(onQueryTextListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.google_main);
        swipeRefreshLayout.setOnRefreshListener(() -> googleSearchPresenter.refresh());
    }

    private final RecyclerViewClickListener<Image> viewItemClickListener = ((value, position) -> startActivityForResult(ImageActivity.create(getContext(), position, value), REQUEST_CODE));
    private final RecyclerViewClickListener<String> favoriteItemClickListener = ((value, position) -> googleSearchPresenter.makeFavoriteUnFavorite(position, value));

    @Override
    public void refresh() {
        recyclerView.clearOnScrollListeners();
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
    public void notifyItemChangedAtPosition(int position) {
        imagesAdapter.notifyItemChanged(position);
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
            hideKeyboard();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                googleSearchPresenter.clear();
                return true;
            } else {
                return false;
            }
        }
    };

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            googleSearchPresenter.validateFavorite(data.getIntExtra("position", 0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(imageChangeBroadCastReceiver);
    }
}
