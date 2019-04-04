package com.juliedai.android.movietrailers.presentation.view.activity.download;

import android.content.Context;

/**
 * @author  Julie Dai
 */

public interface Contract {

    interface View {

        void startDownloadService();

        void requestPermissions();

        void onError();

        Context getViewContext();
    }

    interface Presenter {
    }
}
