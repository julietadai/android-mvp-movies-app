package com.juliedai.android.movietrailers.data.network.services;

import com.juliedai.android.movietrailers.data.network.model.MovieResponse;
import com.juliedai.android.movietrailers.data.network.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author  Julie Dai
 */

public interface RestMovieApi {

    String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String MOVIE_COVER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    //API key
    String apiKey = "f453f49d458a7818caaec0a67158d0ee";
    String keyQuery= "?api_key=" + apiKey;

    String POPULAR_MOVIES_PATH = "movie/popular" + keyQuery;

    String MOVIE_ID_KEY = "movie_id";
    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";
    String MOVIE_VIDEOS_PATH = VIDEOS + keyQuery;

    @GET(POPULAR_MOVIES_PATH)
    Call<MovieResponse> findAllMovies();

    @GET(MOVIE_VIDEOS_PATH)
    Call<VideoResponse> findMovieVideos(@Path(MOVIE_ID_KEY) int movieId);
}
