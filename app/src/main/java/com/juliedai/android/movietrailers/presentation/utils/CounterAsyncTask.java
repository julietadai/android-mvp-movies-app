package com.juliedai.android.movietrailers.presentation.utils;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.juliedai.android.movietrailers.presentation.view.activity.threads.IAsyncTaskEvents;

/**
 * @author  Julie Dai
 */

public class CounterAsyncTask extends AsyncTask<Integer, Integer, Void> {

    private IAsyncTaskEvents iAsyncTaskEvents;

    public CounterAsyncTask(IAsyncTaskEvents iAsyncTaskEvents){
        this.iAsyncTaskEvents = iAsyncTaskEvents;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int count = 0;
        if(integers.length == 1){
            count = integers[0];
        }else{
            count =10;
        }

        for(int i=0; i<=count; i++){
            // Escape early if cancel() is called
            if (isCancelled()) { break; }
            else {
                publishProgress(i);
               SystemClock.sleep(500);
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPreExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onProgressUpdate(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPostExecute();
        }
    }
}