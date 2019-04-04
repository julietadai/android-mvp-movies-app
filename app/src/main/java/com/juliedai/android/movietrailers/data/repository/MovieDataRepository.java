package com.juliedai.android.movietrailers.data.repository;

import android.content.Context;

import com.juliedai.android.movietrailers.data.localdb.DatabaseCore;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.VideoModel;
import com.juliedai.android.movietrailers.data.network.NetworkCore;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.domain.repository.MovieRepository;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class MovieDataRepository {

    public static void getMovies(final Context context, final Listener<List<MovieModel>> listener) {
        // Get movies from the DB
        List<MovieModel> dbMovies = DatabaseCore.retrieveAllMovies(context);
        if(dbMovies != null && !dbMovies.isEmpty()){
            listener.onSuccess(dbMovies);
        }else{ // Get movies from the server
            NetworkCore.getMovies(new Listener<List<MovieModel>>() {
                @Override
                public void onSuccess(List<MovieModel> movieModels) {
                    // Save the movies into the local DB
                    DatabaseCore.saveMovies(context, movieModels);
                    listener.onSuccess(movieModels);
                }

                @Override
                public void onFailure() {
                    listener.onFailure();
                }
            });
        }
    }

    public static void getVideos(final Context context, int movieId, final Listener<String> listener) {
        // Get movie's video from the DB
        VideoModel video = DatabaseCore.getVideo(context, movieId);
        if(video != null){
            listener.onSuccess(video.getKey());
        } else{ // Get movie's video from the server
            NetworkCore.getVideos(movieId, new Listener<VideoModel>() {

                @Override
                public void onSuccess(VideoModel videoModel) {
                    // Save the movie's video into the local DB
                    DatabaseCore.saveVideo(context, videoModel);
                    listener.onSuccess(videoModel.getKey());
                }

                @Override
                public void onFailure() {
                    listener.onFailure();
                }
            });


        }
    }
}
