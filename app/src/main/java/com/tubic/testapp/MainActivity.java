package com.tubic.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tubic.testapp.favorites.FavoritesFragment;
import com.tubic.testapp.fb.FacebookFragment;
import com.tubic.testapp.google.GoogleSearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(this, getSupportFragmentManager()));
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] titles;

        ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            titles = new String[]{
                    context.getString(R.string.google),
                    context.getString(R.string.facebook),
                    context.getString(R.string.favorites),
            };
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GoogleSearchFragment();
                case 1:
                    return new FacebookFragment();
                case 2:
                    return new FavoritesFragment();
                default:
                    throw new IllegalArgumentException("unexpected position " + position);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
