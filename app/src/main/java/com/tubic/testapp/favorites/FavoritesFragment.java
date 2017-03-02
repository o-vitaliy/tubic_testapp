package com.tubic.testapp.favorites;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tubic.testapp.R;
import com.tubic.testapp.common.BaseFragment;
import com.tubic.testapp.common.ImageEventProvider;
import com.tubic.testapp.common.LayoutManagerHelper;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.image.ImageActivity;

import javax.inject.Inject;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FavoritesFragment extends BaseFragment implements FavoritesContract.View {


    @Inject
    FavoritesPresenter favoritesPresenter;

    private RecyclerView recyclerView;
    private FavoritesAdapter imagesAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerFavoritesComponent.builder().appComponent(getAppComponent())
                .favoritesPresenterModule(new FavoritesPresenterModule(this, getLoaderManager()))
                .build()
                .inject(this);
        ;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagesAdapter = new FavoritesAdapter(viewItemClickListener, favoriteItemClickListener);

        recyclerView = (RecyclerView) view.findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(LayoutManagerHelper.getLayoutManager(getContext()));
        recyclerView.setAdapter(imagesAdapter);
        recyclerView.setItemAnimator(null);
        LayoutManagerHelper.applyItemDecoration(recyclerView);
    }

    private final RecyclerViewClickListener<Image> viewItemClickListener = ((value, position) -> startActivity(ImageActivity.create(getContext(), position, value)));
    private final RecyclerViewClickListener<String> favoriteItemClickListener = ((value, position) -> favoritesPresenter.makeFavoriteUnFavorite(position, value));


    @Override
    public void onStart() {
        super.onStart();
        favoritesPresenter.start();
    }

    @Override
    public void showNoResults() {

    }

    @Override
    public void showResults(Cursor cursor) {
        imagesAdapter.changeCursor(cursor);
    }

    @Override
    public void notifyItemChanged(String link) {
        ImageEventProvider.notifyImageChangedInFavorites(getContext(), link);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
