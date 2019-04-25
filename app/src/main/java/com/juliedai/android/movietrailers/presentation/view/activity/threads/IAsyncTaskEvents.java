package com.juliedai.android.movietrailers.presentation.view.activity.threads;

/**
 * @author  Julie Dai
 * Interface for threads events
 */

public interface IAsyncTaskEvents {
    void onPreExecute();
    void onPostExecute();
    void onProgressUpdate(Integer num);
    void onCancel();

    void createAsyncTask();
    void startAsyncTask();
    void cancelAsyncTask();
}
