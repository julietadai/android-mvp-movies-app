package com.juliedai.android.movietrailers.data.localdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.juliedai.android.movietrailers.data.MovieModel;

import java.util.Collection;
import java.util.List;

/**
 * @author  Julie Dai
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM MovieModel ORDER BY popularity DESC")
    List<MovieModel> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<MovieModel> movies);

    @Query("DELETE FROM MovieModel")
    void deleteAll();
}

