package com.juliedai.android.movietrailers.data.network.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author  Julie Dai
 */

public class MovieService {
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String BASE_API_URL = BASE_URL + "/3/";

    private static RestMovieApi movieDBService;

    public static RestMovieApi getMoviesServiceInstance() {
        if(movieDBService == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            movieDBService = retrofit.create(RestMovieApi.class);
        }
        return movieDBService;
    }
}
