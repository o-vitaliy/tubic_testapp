package com.tubic.testapp.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private final int visibleThreshold = 5;

    private Runnable endReachedCallback;

    private boolean autoRemove = true;

    public static RecyclerViewScrollListener attach(RecyclerView recyclerView, Runnable endReachedCallback) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
            throw new RuntimeException("recyclerView.getLayoutManager() should be instanceof LinearLayoutManager");

        RecyclerViewScrollListener recyclerViewScrollListener = new RecyclerViewScrollListener();

        recyclerViewScrollListener.endReachedCallback = endReachedCallback;

        recyclerView.addOnScrollListener(recyclerViewScrollListener);

        recyclerView.post(()->recyclerView.scrollBy(0,0));

        return recyclerViewScrollListener;
    }

    private RecyclerViewScrollListener() {
    }

    /**
     * indicate do scroll listener should automatically removed from recyclerView when endReachedCallback called
     * default true
     *
     * @param autoRemove indicate to remove automatically or not
     */
    public RecyclerViewScrollListener setAutoRemove(boolean autoRemove) {
        this.autoRemove = autoRemove;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            endReachedCallback.run();

            if (autoRemove)
                recyclerView.removeOnScrollListener(this);
        }

    }


}
