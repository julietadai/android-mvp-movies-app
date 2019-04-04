package com.juliedai.android.movietrailers.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author  Julie Dai
 */

@Entity
public class MovieModel implements Parcelable {

    @PrimaryKey
    private int movieId;
    private String name;
    private String imageResource;
    private String coverImageResource;
    private String overview;
    private String releaseDate;
    private String trailerUrl;
    private Double popularity;

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.movieId = in.readInt();
        this.name = in.readString();
        this.imageResource = in.readString();
        this.coverImageResource = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.trailerUrl = in.readString();
        this.popularity = in.readDouble();
    }

    public MovieModel(int movieId, String name, String profileImageUri, String coverImageUri, String overview, String releaseDate, Double popularity) {
        this.movieId = movieId;
        this.name = name;
        this.imageResource = profileImageUri;
        this.coverImageResource = coverImageUri;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getCoverImageResource() {
        return coverImageResource;
    }

    public void setCoverImageResource(String coverImageResource) {
        this.coverImageResource = coverImageResource;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieId);
        dest.writeString(this.name);
        dest.writeString(this.imageResource);
        dest.writeString(this.coverImageResource);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.trailerUrl);
        dest.writeDouble(this.popularity);
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public String toString() {
        return "MovieModel{" +
                "movieId=" + movieId +
                ", name='" + name + '\'' +
                ", imageResource='" + imageResource + '\'' +
                ", coverImageResource='" + coverImageResource + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
