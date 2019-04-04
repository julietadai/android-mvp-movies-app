package com.juliedai.android.movietrailers.presentation.presenter;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.data.repository.Listener;
import com.juliedai.android.movietrailers.domain.interator.DetailsFragmentInteractor;
import com.juliedai.android.movietrailers.presentation.view.fragment.details.Contract;

/**
 * @author  Julie Dai
 */

public class DetailsFragmentPresenterImpl implements Contract.Presenter {
    private final DetailsFragmentInteractor interactor;
    private final Contract.View view;

    public DetailsFragmentPresenterImpl(Contract.View view, MovieModel movieModel) {
        interactor = new DetailsFragmentInteractor(view.getViewContext(), movieModel);
        this.view = view;

        if (interactor.isMovieModelValid()) {
            this.view.loadMovieData(movieModel);
        } else {
            this.view.onError();
        }

    }

    @Override
    public void onTrailerButtonClick() {
        view.showLoading();
        interactor.fetchVideo(new Listener<String>() {

            @Override
            public void onSuccess(String movieKey) {
                view.hideLoading();
                view.playTrailer(movieKey);
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.onError();
            }
        });
    }

    @Override
    public void onDownloadCoverImageButtonClick() {
        view.downloadMovieImage();
    }
}
