package com.juliedai.android.movietrailers.presentation.presenter;

import android.util.Log;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.domain.interator.MoviesInteractor;
import com.juliedai.android.movietrailers.presentation.view.activity.list.Contract;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class MoviesPresenterImpl implements Contract.Presenter{

    private static final String LOG_TAG = "_MoviesActivity_";
    private final MoviesInteractor interactor;
    private final Contract.View view;

    public MoviesPresenterImpl(Contract.View view) {
      this.interactor = new MoviesInteractor(view.getViewContext());
      this.view = view;

      loadData();
  }

    private void loadData(){
      view.showLoading();
      this.interactor.retrieveMovies(new Listener<List<MovieModel>>() {
        @Override
        public void onSuccess(List<MovieModel> movieModels) {
          view.hideLoading();
          view.loadMovies(movieModels);
        }

        @Override
        public void onFailure() {
          view.hideLoading();
          view.onError();
        }
      });
    }

    @Override
    public void onMovieClicked(int position) {
      if (interactor.isItemPositionCorrect(position)) {
        view.openMovieDetails(position);
      }else{
        Log.d(LOG_TAG, "> Presenter > onMovieClicked: Item position incorrect");
      }
    }

    @Override
    public void onMenuDeleteClicked() {
      view.clearAdapter();
    }

  @Override
  public void onMenuReloadClicked() {
    view.clearAdapter();
    loadData();
  }
}
