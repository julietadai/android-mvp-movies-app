package com.juliedai.android.movietrailers.presentation.presenter;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.domain.interator.DetailsInteractor;
import com.juliedai.android.movietrailers.presentation.view.activity.details.Contract;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class DetailsPresenterImpl implements Contract.Presenter{
    private final DetailsInteractor interactor;
    private final Contract.View view;

    public DetailsPresenterImpl(Contract.View view) {
        this.interactor = new DetailsInteractor(view.getViewContext());
        this.view = view;

        this.interactor.retrieveMovies(new Listener<List<MovieModel>>() {
            @Override
            public void onSuccess(List<MovieModel> movieModels) {
                getView().loadData(movieModels);
            }

            @Override
            public void onFailure() {
                getView().onError();
            }
        });
    }

    public Contract.View getView() {
        return view;
    }
}
