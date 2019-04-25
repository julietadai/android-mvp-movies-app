package com.juliedai.android.movietrailers.presentation.view.activity.list;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;

import java.util.List;

/**
 * @author  Julie Dai
 */

public interface Contract {
    interface View {
        void onMoviesLoaded(List<MovieModel> movies);

        void openMovieDetails(int position);

        void showLoading();

        void hideLoading();

        void clearAdapter();

        Context getViewContext();

        void onError();

    }

    interface Presenter {
        void onMovieClicked(int position);

        void onMenuDeleteClicked();

        void onMenuReloadClicked();
    }

}
