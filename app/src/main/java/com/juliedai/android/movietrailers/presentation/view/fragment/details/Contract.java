package com.juliedai.android.movietrailers.presentation.view.fragment.details;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;

/**
 * @author  Julie Dai
 */

public interface Contract {

    interface View {
        void loadMovieData(MovieModel movieModel);

        void playTrailer(String videoKey);

        void downloadMovieImage();

        void showLoading();

        void hideLoading();

        void onError();

        Context getViewContext();
    }

    interface Presenter {
        void onTrailerButtonClick();

        void onDownloadCoverImageButtonClick();
    }
}
