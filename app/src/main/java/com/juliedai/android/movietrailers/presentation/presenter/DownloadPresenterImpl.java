package com.juliedai.android.movietrailers.presentation.presenter;

import android.util.Log;

import com.juliedai.android.movietrailers.domain.interator.DownloadInteractor;
import com.juliedai.android.movietrailers.presentation.view.activity.download.Contract;

/**
 * @author  Julie Dai
 * Presenter that controls communication between views and models of the presentation
 * layer.
 */

public class DownloadPresenterImpl implements Contract.Presenter {
    private static final String LOG_TAG = "_DownloadActivity_";
    private final DownloadInteractor interactor;
    private final Contract.View view;

    public DownloadPresenterImpl(Contract.View view) {
        interactor = new DownloadInteractor(view.getViewContext());
        this.view = view;

        if(this.interactor.isPermissionGranted()) {
            Log.d(LOG_TAG, "> Presenter > Permission granted");
            this.view.startDownloadService();
        }else{
            this.view.requestPermissions();
        }
    }


}
