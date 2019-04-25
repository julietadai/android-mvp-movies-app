package com.juliedai.android.movietrailers.presentation.view.activity.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.presentation.view.activity.details.DetailsActivity;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.presentation.view.adapter.MoviesBaseAdapter;
import com.juliedai.android.movietrailers.presentation.presenter.MoviesPresenterImpl;
import com.juliedai.android.movietrailers.presentation.view.activity.threads.AsyncTaskActivity;
import com.juliedai.android.movietrailers.presentation.view.activity.threads.ThreadsActivity;

import java.util.List;

/**
 * @author  Julie Dai
 * Activity that contains list of movies with their details
 */

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener, Contract.View {
    private static final String LOG_TAG = "_MoviesActivity_";

    private RecyclerView recyclerView;
    private View progressBar;
    private MoviesBaseAdapter adapter;
    private Contract.Presenter presenter;
    private Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = this.getApplicationContext();
        setContentView(R.layout.activity_movies);

        progressBar = findViewById(R.id.main_progress);

        recyclerView = findViewById(R.id.rlvMovies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new MoviesPresenterImpl(this);
    }

    @Override
    public void onMovieClicked(int itemPosition) {
        presenter.onMovieClicked(itemPosition);
    }

    @Override
    public void onMoviesLoaded(List<MovieModel> movies) {
        // Create adapter and set the movies list into it
        adapter = new MoviesBaseAdapter(appContext, movies, MoviesActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void openMovieDetails(int position) {
        Intent detailsActivityIntent = new Intent(MoviesActivity.this, DetailsActivity.class);
        detailsActivityIntent.putExtra(DetailsActivity.EXTRA_ITEM_POSITION, position);
        startActivity(detailsActivityIntent);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void clearAdapter() {
        adapter.clearData();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onError() {
        Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_reload:
                presenter.onMenuReloadClicked();
                break;
            case R.id.action_delete:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustomeTheme));
                alertBuilder.setTitle(R.string.delete_all_movies_dialog_title)
                        .setMessage(R.string.delete_all_movies_dialog_message)
                        .setPositiveButton(R.string.delete_all_movies_dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.onMenuDeleteClicked();
                            }
                        })
                        .setNegativeButton(R.string.delete_all_movies_dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL
                            }
                        })
                        .show();
                break;
            case R.id.actionAsyncTask:
                Toast.makeText(MoviesActivity.this, "Action Async Task clicked", Toast.LENGTH_LONG).show();
                startActivity( new Intent(MoviesActivity.this, AsyncTaskActivity.class));
                return true;
            case R.id.actionThreadHandler:
                Toast.makeText(MoviesActivity.this, "Action Thread Handler clicked", Toast.LENGTH_LONG).show();
                startActivity( new Intent(MoviesActivity.this, ThreadsActivity.class));
                return true;
        }
        return true;
    }
}
