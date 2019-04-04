package com.juliedai.android.movietrailers.domain.repository;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;

import java.util.List;

/**
 * @author  Julie Dai
 */

public interface MovieRepository {

    void getMovies(final Context context, final Listener<List<MovieModel>> listener);

    void getVideos(final Context context, int movieId, final Listener<String> listener);

}
