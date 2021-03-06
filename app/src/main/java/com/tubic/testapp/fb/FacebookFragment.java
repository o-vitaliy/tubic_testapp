package com.tubic.testapp.fb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.BaseFragment;
import com.tubic.testapp.common.ImageEventProvider;
import com.tubic.testapp.common.ImagesAdapter;
import com.tubic.testapp.common.LayoutManagerHelper;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.common.RecyclerViewScrollListener;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.image.ImageActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FacebookFragment extends BaseFragment implements FacebookContract.View {


    private static final int REQUEST_CODE = 100;

    @Inject
    FacebookPresenter facebookPresenter;


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button loginButton;
    private ImagesAdapter imagesAdapter;
    private TextView emptyResultView;
    private ContentLoadingProgressBar progressBar;

    private boolean isLoggedIn;


    private BroadcastReceiver imageChangeBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            facebookPresenter.validateFavorite(intent.getStringExtra(ImageEventProvider.EXTRA_LINK));
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        DaggerFacebookComponent.builder()
                .appComponent(getAppComponent())
                .facebookPresenterModule(new FacebookPresenterModule(this))
                .build().inject(this);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(imageChangeBroadCastReceiver, new IntentFilter(ImageEventProvider.EVENT_IMAGE_CHANGED));
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

        imagesAdapter = new ImagesAdapter(viewItemClickListener, favoriteItemClickListener);

        recyclerView = (RecyclerView) view.findViewById(R.id.facebook_list);
        recyclerView.setLayoutManager(LayoutManagerHelper.getLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);
        LayoutManagerHelper.applyItemDecoration(recyclerView);
        recyclerView.setItemAnimator(null);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.facebook_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> facebookPresenter.refresh());

        emptyResultView = (TextView) view.findViewById(R.id.facebook_empty);

        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.facebook_progress);

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
    public void showSearchNoResults() {
        emptyResultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchNoResults() {
        emptyResultView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.hide();
    }

    @Override
    public void notifyRefreshingComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setVisibleItem(int offset) {
        recyclerView.getLayoutManager().scrollToPosition(offset);
    }

    private final RecyclerViewClickListener<Image> viewItemClickListener = ((value, position) -> startActivityForResult(ImageActivity.create(getContext(), position, value), REQUEST_CODE));
    private final RecyclerViewClickListener<String> favoriteItemClickListener = ((value, position) -> facebookPresenter.makeFavoriteUnFavorite(position, value));

    @Override
    public void notifyItemChangedAtPosition(int position) {
        imagesAdapter.notifyItemChanged(position);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            facebookPresenter.validateFavorite(data.getIntExtra("position", 0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(imageChangeBroadCastReceiver);
    }
}
