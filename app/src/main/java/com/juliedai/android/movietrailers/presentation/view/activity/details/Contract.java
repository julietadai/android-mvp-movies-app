package com.juliedai.android.movietrailers.presentation.view.activity.details;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;

import java.util.List;

/**
 * @author  Julie Dai
 */

public interface Contract {
    interface View {
        void loadData(List<MovieModel> movies);

        void onError();

        Context getViewContext();
    }

    interface Presenter {
    }
}
