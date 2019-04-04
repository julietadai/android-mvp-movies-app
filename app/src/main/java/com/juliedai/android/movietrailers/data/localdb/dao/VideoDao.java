package com.juliedai.android.movietrailers.data.localdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.juliedai.android.movietrailers.data.VideoModel;

/**
 * @author  Julie Dai
 */

@Dao
public interface VideoDao {

    @Query("SELECT * FROM VIDEOMODEL WHERE movieId = :movieId")
    VideoModel getVideo(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VideoModel videoModel);

}
