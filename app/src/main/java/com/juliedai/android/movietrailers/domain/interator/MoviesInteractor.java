package com.juliedai.android.movietrailers.domain.interator;

import android.content.Context;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.data.repository.MovieDataRepository;
import com.juliedai.android.movietrailers.domain.repository.MovieRepository;


import java.util.ArrayList;
import java.util.List;

/**
 * @author  Julie Dai
 */

public class MoviesInteractor {

    private List<MovieModel> movies;
    private Context context;

    public MoviesInteractor(Context viewContext) {
            this.context = viewContext;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public Context getContext() {
        return context;
    }

    public boolean isItemPositionCorrect(int itemPosition) {
        return itemPosition >= 0 || itemPosition < movies.size();
    }

    public void retrieveMovies(final Listener<List<MovieModel>> listener) {
        MovieDataRepository.getMovies(context, new Listener<List<MovieModel>>() {
            @Override
            public void onSuccess(List<MovieModel> movieModels) {
                if(movieModels != null){
                    setMovies(movieModels);
                }else{
                    setMovies(new ArrayList<MovieModel>());
                }
                listener.onSuccess(getMovies());
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }
}
