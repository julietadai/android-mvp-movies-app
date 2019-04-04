package com.juliedai.android.movietrailers.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author  Julie Dai
 */

@Entity
public class VideoModel {
    @PrimaryKey
    private int movieId;
    private String id;
    private String key;

    public VideoModel(){}

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "movieId=" + movieId +
                ", id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
