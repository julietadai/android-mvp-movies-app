package com.juliedai.android.movietrailers.data.network.model.convertors;

import android.util.Log;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.VideoModel;
import com.juliedai.android.movietrailers.data.network.model.Movie;
import com.juliedai.android.movietrailers.data.network.model.MovieResponse;
import com.juliedai.android.movietrailers.data.network.model.Video;
import com.juliedai.android.movietrailers.data.network.model.VideoResponse;
import com.juliedai.android.movietrailers.data.network.services.RestMovieApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Julie Dai
 */

public class ModelConvertor {

    private static final String LOG_TAG = "_MovieModelConvertor_";

    private static MovieModel getMovieModelFromMovieResponse(Movie movieRsult){
        MovieModel movie = new MovieModel();
        movie.setMovieId(movieRsult.getId());
        movie.setName(movieRsult.getTitle());
        movie.setImageResource(RestMovieApi.MOVIE_IMAGE_BASE_URL + movieRsult.getPosterPath());
        movie.setCoverImageResource(RestMovieApi.MOVIE_COVER_IMAGE_BASE_URL + movieRsult.getBackdropPath());
        movie.setOverview(movieRsult.getOverview());
        movie.setReleaseDate(movieRsult.getReleaseDate());
        movie.setPopularity(movieRsult.getPopularity());

        return movie;
    }

    public static ArrayList<MovieModel> getMovieModelListFromMovieListResponse(MovieResponse movieResponse){
        Log.i(LOG_TAG, "> In getMovieModelListFromMovieListResponse");
        ArrayList<MovieModel> result = new ArrayList<>();
        for(Movie movie : movieResponse.getResults()){
            result.add(getMovieModelFromMovieResponse(movie));
        }
        Log.i(LOG_TAG, "Converted " + result.size() + " movies from MovieListResult");
        return result;
    }

    private static VideoModel getVideoModelFromVideoRessponse(Video videoResponse, int movieId){
        VideoModel video = new VideoModel();
        video.setMovieId(movieId);
        video.setId(videoResponse.getId());
        video.setKey(videoResponse.getKey());

        return video;
    }

    public static VideoModel getVideoModelFromVideoListResponse(VideoResponse videoResponse){
        List<Video> movieVideos = videoResponse.getResults();
        if(movieVideos != null && !movieVideos.isEmpty()){
            Video videoResult = movieVideos.get(0); //Getting the first movie trailer in the list
            return getVideoModelFromVideoRessponse(videoResult, videoResponse.getId());
        }
        return null;
    }
}
