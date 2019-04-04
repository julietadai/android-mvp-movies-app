package com.juliedai.android.movietrailers.data.localdb.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.juliedai.android.movietrailers.data.localdb.dao.MovieDao;
import com.juliedai.android.movietrailers.data.localdb.dao.VideoDao;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.VideoModel;

/**
 * @author  Julie Dai
 */

@Database(entities = {MovieModel.class, VideoModel.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movies-db";
    static AppDatabase INSTANCE;

    public abstract MovieDao movieDao();
    public abstract VideoDao videoDao();

    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //Destructively recreate database tables if Migrations that would migrate old database schemas to the latest schema version are not found.
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyDbInstance() {
        INSTANCE = null;
    }

}
