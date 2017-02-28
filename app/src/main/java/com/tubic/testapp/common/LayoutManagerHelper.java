package com.tubic.testapp.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tubic.testapp.R;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class LayoutManagerHelper {

    public static RecyclerView.LayoutManager getLayoutManager(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        boolean isLand = context.getResources().getBoolean(R.bool.isLand);
        if (isTablet) {
            if (isLand)
                return new GridLayoutManager(context, 3);
            else
                return new GridLayoutManager(context, 2);
        } else {
            if (isLand)
                return new GridLayoutManager(context, 2);
            else
                return new LinearLayoutManager(context);
        }
    }


}
