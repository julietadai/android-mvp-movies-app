package com.juliedai.android.movietrailers.presentation.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.juliedai.android.movietrailers.presentation.view.fragment.details.MovieDetailsFragment;
import com.juliedai.android.movietrailers.data.MovieModel;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class SlidesPagerAdapter extends FragmentPagerAdapter {
    private List<MovieModel> movies;

    public SlidesPagerAdapter(FragmentManager fm, List<MovieModel> movies) {
        super(fm);
        this.movies = movies;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieDetailsFragment.newInstance(movies.get(position));
    }

    @Override
    public int getCount() {
        return movies.size();
    }
}
