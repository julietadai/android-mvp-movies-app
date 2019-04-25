package com.juliedai.android.movietrailers.presentation.view.fragment.details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.presentation.presenter.DetailsFragmentPresenterImpl;
import com.juliedai.android.movietrailers.presentation.view.activity.download.DownloadActivity;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.network.services.RestMovieApi;
import com.squareup.picasso.Picasso;

/**
 * @author  Julie Dai
 * Fragment the contains movie's details
 */

public class MovieDetailsFragment extends Fragment implements View.OnClickListener, Contract.View {

    private static final String LOG_TAG = "_MovieDetailsFragment_";
    private static final String ARGS_MOVIE_DETAILS = "args_movie_details";

    private Picasso picasso;
    private MovieModel movieModel;
    private ImageView ivImgMovieProfile;
    private ImageView ivImgMovieCover;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private Button btnMovieTrailer;
    private ImageButton btnDownloadCoverImg;
    private Contract.Presenter presenter;

    public static MovieDetailsFragment newInstance(MovieModel movieModelContent){
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_MOVIE_DETAILS, movieModelContent);
        movieDetailsFragment.setArguments(bundle);

        return movieDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.picasso = Picasso.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImgMovieProfile = view.findViewById(R.id.imgProfileMovie);
        ivImgMovieCover = view.findViewById(R.id.imgCoverMovie);
        tvTitle = view.findViewById(R.id.txtvMovieTitle);
        tvReleaseDate = view.findViewById(R.id.txtvMovieReleasedDate);
        tvOverview = view.findViewById(R.id.txtvMovieDescription);

        btnMovieTrailer = view.findViewById(R.id.btnMovieTrailer);
        if(btnMovieTrailer != null){
            btnMovieTrailer.setOnClickListener(this);
        }

        btnDownloadCoverImg = view.findViewById(R.id.btnDownload);
        if(btnDownloadCoverImg != null){
            btnDownloadCoverImg.setOnClickListener(this);
        }


        movieModel = null;
        if (getArguments() != null) {
            movieModel = getArguments().getParcelable(ARGS_MOVIE_DETAILS);
        }

        presenter = new DetailsFragmentPresenterImpl(this, movieModel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMovieTrailer:
                presenter.onTrailerButtonClick();
                break;
            case R.id.btnDownload:
                presenter.onDownloadCoverImageButtonClick();
                break;
        }
    }

    @Override
    public void loadMovieData(MovieModel movieModel) {
        if(movieModel == null){ return; }

        if(ivImgMovieProfile != null) {
            if(!TextUtils.isEmpty(movieModel.getImageResource())) {
                picasso.load(movieModel.getImageResource())
                        .into(ivImgMovieProfile);
            }
        }

        if(ivImgMovieCover != null) {
            if(!TextUtils.isEmpty(movieModel.getCoverImageResource())) {
                picasso.load(movieModel.getCoverImageResource())
                        .into(ivImgMovieCover);
            }
        }

        if(tvTitle != null){
            tvTitle.setText(movieModel.getName());
        }

        if(tvReleaseDate != null){
            tvReleaseDate.setText(movieModel.getReleaseDate());
        }

        if(tvOverview != null){
            tvOverview.setText(movieModel.getOverview());
        }
    }

    @Override
    public void playTrailer(String videoKey) {
        String movieTrailerUrl = RestMovieApi.YOUTUBE_BASE_URL + videoKey;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieTrailerUrl));
        startActivity(intent);
    }

    @Override
    public void downloadMovieImage() {
        Context context = getContext();
        if (movieModel == null || context == null) {
            Log.e(LOG_TAG, "> downloadImage, failed to download the movie's cover image");
            return;
        }
        Log.d(LOG_TAG, "> downloadImage, movieModel= " + movieModel);
        DownloadActivity.startActivity(context, movieModel);
    }

    @Override
    public void showLoading() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        RotateDrawable rotateDrawable = (RotateDrawable) ContextCompat.getDrawable(context, R.drawable.progress_animation);
        ObjectAnimator anim = ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
        btnMovieTrailer.setText(R.string.details_loading_trailer_text);
        btnMovieTrailer.setCompoundDrawablesWithIntrinsicBounds(rotateDrawable, null, null, null);
    }

    @Override
    public void hideLoading() {
        btnMovieTrailer.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnMovieTrailer.setText(R.string.details_trailer_text);
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), R.string.something_went_wrong_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }
}
