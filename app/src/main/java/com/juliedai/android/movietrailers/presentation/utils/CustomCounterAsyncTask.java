package com.juliedai.android.movietrailers.presentation.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.juliedai.android.movietrailers.presentation.view.activity.threads.IAsyncTaskEvents;

/**
 * @author  Julie Dai
 */

public class CustomCounterAsyncTask {
    private volatile boolean isTaskCancelled = false;
    private Thread backgroundThread;
    private IAsyncTaskEvents iAsyncTaskEvents;

    public CustomCounterAsyncTask(IAsyncTaskEvents iAsyncTaskEvents){
        this.iAsyncTaskEvents = iAsyncTaskEvents;
    }

    public boolean isTaskCancelled() {
        return isTaskCancelled;
    }

    //Runs on the UI thread before doInBackground()
    private void onPreExecute(){
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPreExecute();
        }
    }

    //Runs on worker thread, after onPreExecute() and before onPostExecute()
    private void doInBackground(Integer... integers){
        int count = 0;
        if(integers.length == 1){
            count = integers[0];
        }else{
            count =10;
        }

        for(int i=0; i<=count; i++){
            // Escape early if cancel() is called
            if (isTaskCancelled) { break; }
            else {
                publishProgress(i);
                SystemClock.sleep(500);
            }
        }

    }

    //Runs on the UI thread after doInBackground()
    private void onPostExecute(){
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPostExecute();
        }
    }

    public void execute(final Integer count){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                backgroundThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doInBackground(count);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute();
                            }
                        });
                    }
                });
                backgroundThread.start();
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);

    }

    private void onProgressUpdate(Integer num){
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onProgressUpdate(num);
        }
    }

    public void cancel(){
        this.isTaskCancelled = true;
        if(backgroundThread != null) {
            this.backgroundThread.interrupt();
        }
    }

    private void publishProgress(final Integer progress){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }
}
