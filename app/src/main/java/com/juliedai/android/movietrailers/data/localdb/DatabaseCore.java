package com.juliedai.android.movietrailers.data.localdb;

import android.content.Context;

import com.juliedai.android.movietrailers.data.localdb.database.AppDatabase;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.VideoModel;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class DatabaseCore {

    public static List<MovieModel> retrieveAllMovies(Context context) {
        return AppDatabase
                .getDbInstance(context)
                .movieDao()
                .getAll();

    }

    public static void saveMovies(Context context, List<MovieModel> movies) {
        deleteAllMovies(context);
        AppDatabase
                .getDbInstance(context)
                .movieDao()
                .insertAll(movies);
    }

    public static void deleteAllMovies(Context context) {
        AppDatabase
                .getDbInstance(context)
                .movieDao()
                .deleteAll();
    }

    public static VideoModel getVideo(Context context, int movieId) {
        return AppDatabase
                .getDbInstance(context)
                .videoDao()
                .getVideo(movieId);
    }

    public static void saveVideo(Context context, VideoModel video) {
        AppDatabase
                .getDbInstance(context)
                .videoDao()
                .insert(video);
    }

}
