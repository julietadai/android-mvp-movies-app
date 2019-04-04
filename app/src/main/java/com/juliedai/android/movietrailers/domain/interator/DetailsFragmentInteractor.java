package com.juliedai.android.movietrailers.domain.interator;

import android.content.Context;
import android.util.Log;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.data.repository.MovieDataRepository;

/**
 * @author  Julie Dai
 */

public class DetailsFragmentInteractor {
    private static final String LOG_TAG = "_MovieDetailsFragment_";
    private final Context context;
    private MovieModel movieModel;
    private String videoKey;


    public DetailsFragmentInteractor(Context context, MovieModel movieModel) {
        this.context = context;
        this.movieModel = movieModel;

        Log.d(DetailsFragmentInteractor.class.getSimpleName(), "movieModel: " + movieModel);
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public boolean isMovieModelValid() {
        return movieModel != null;
    }

    public void fetchVideo(final Listener<String> listener) {
        if (videoKey == null) {
            MovieDataRepository.getVideos(context, movieModel.getMovieId(), new Listener<String>() {
                @Override
                public void onSuccess(String videoKey) {
                    setVideoKey(videoKey);
                    listener.onSuccess(videoKey);
                }

                @Override
                public void onFailure() {
                    listener.onFailure();
                }
            });
        }else {
            listener.onSuccess(videoKey);
        }
    }
}
