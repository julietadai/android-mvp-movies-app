package com.juliedai.android.movietrailers.presentation.view.activity.details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.presentation.presenter.DetailsPresenterImpl;
import com.juliedai.android.movietrailers.presentation.view.adapter.SlidesPagerAdapter;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class DetailsActivity extends AppCompatActivity implements Contract.View{
    private static final String LOG_TAG = "_DetailsActivity_";
    public static final String EXTRA_ITEM_POSITION = "init-position-data";
    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        presenter = new DetailsPresenterImpl(this);
    }

    @Override
    public void loadData(List<MovieModel> movies) {
        ViewPager mdPager = findViewById(R.id.vpMovieDetailsContainer);
        PagerAdapter mdPagerAdapter = new SlidesPagerAdapter(getSupportFragmentManager(), movies);
        mdPager.setAdapter(mdPagerAdapter);

        int startPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, 0);
        mdPager.setCurrentItem(startPosition, false);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.something_went_wrong_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
