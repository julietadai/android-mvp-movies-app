package com.juliedai.android.movietrailers.data.network;

import android.util.Log;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.VideoModel;
import com.juliedai.android.movietrailers.data.network.model.MovieResponse;
import com.juliedai.android.movietrailers.data.network.model.Video;
import com.juliedai.android.movietrailers.data.network.model.VideoResponse;
import com.juliedai.android.movietrailers.data.network.model.convertors.*;
import com.juliedai.android.movietrailers.data.network.services.MovieService;
import com.juliedai.android.movietrailers.data.network.services.RestMovieApi;
import com.juliedai.android.movietrailers.data.repository.Listener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author  Julie Dai
 */

public class NetworkCore {

    private static final String LOG_TAG = "_NetworkCore_";

    public static void getMovies(final Listener<List<MovieModel>> listener) {
        RestMovieApi restMovieApiInstance = MovieService.getMoviesServiceInstance();
        if(restMovieApiInstance != null){
            Call<MovieResponse> call = restMovieApiInstance.findAllMovies();
            // Asynchrony run on each call
            call.enqueue(new Callback<MovieResponse>() {

                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Log.d(LOG_TAG, "Response code = " + String.valueOf(response.code()));
                    if(response != null && response.isSuccessful() && response.body() != null){
                        ArrayList<MovieModel> movies = ModelConvertor.getMovieModelListFromMovieListResponse(response.body());
                        Log.d(LOG_TAG, "Loaded " + movies.size() +" movies successfully");
                        listener.onSuccess(movies);
                    }else {
                        listener.onFailure();
                    }
                }

                //On exception case
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(LOG_TAG,"Failed to load movies, e= [" + t.getCause().getMessage() +"]");
                    listener.onFailure();
                }
            });
        }

    }

    public static void getVideos(int movieId, final Listener<VideoModel> listener) {
        RestMovieApi restMovieApiInstance = MovieService.getMoviesServiceInstance();
        if(restMovieApiInstance != null){
            Call<VideoResponse> call = restMovieApiInstance.findMovieVideos(movieId);
            //Asynchrony run on each call
            call.enqueue(new Callback<VideoResponse>() {

                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    Log.d(LOG_TAG, "Response code = " + String.valueOf(response.code()));
                    if(response != null && response.isSuccessful()) {
                        VideoResponse body = response.body();
                        if (body != null) {
                            List<Video> results = body.getResults();
                            if (results != null && !((List) results).isEmpty()) {
                                VideoModel video = ModelConvertor.getVideoModelFromVideoListResponse(body);
                                if(video != null){
                                    Log.d(LOG_TAG, "Loaded the movie's video successfully");
                                    listener.onSuccess(video);
                                }else{
                                    listener.onFailure();
                                }
                            }else{
                                listener.onFailure();
                            }
                        }else{
                            listener.onFailure();
                        }
                    }else{
                        listener.onFailure();
                    }
                }

                //On exception case
                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    Log.d(LOG_TAG,"Failed to load movie's video, e= [" + t.getCause().getMessage() +"]");
                    listener.onFailure();
                }
            });
        }
    }
}
