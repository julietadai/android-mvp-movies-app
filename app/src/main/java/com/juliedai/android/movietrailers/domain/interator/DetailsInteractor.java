package com.juliedai.android.movietrailers.domain.interator;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.data.repository.MovieDataRepository;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class DetailsInteractor {
    private Context context;

    public DetailsInteractor(Context context) {
        this.context = context;
    }

    public void retrieveMovies(Listener<List<MovieModel>> listener) {
        MovieDataRepository.getMovies(context, listener);
    }
}
