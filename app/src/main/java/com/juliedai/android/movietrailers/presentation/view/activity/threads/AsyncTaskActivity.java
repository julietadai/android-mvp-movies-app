package com.juliedai.android.movietrailers.presentation.view.activity.threads;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.presentation.utils.CounterAsyncTask;
import com.juliedai.android.movietrailers.presentation.view.fragment.thread.CounterFragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author  Julie Dai
 * Activity that present AsyncTask performance - create, start and cancel
 */

public class AsyncTaskActivity extends AppCompatActivity implements IAsyncTaskEvents {
    private static FragmentManager fragmentManager;
    private CounterFragment counterFragment;
    private CounterAsyncTask counterAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null){
            counterFragment = new CounterFragment();
            Bundle data = new Bundle();
            data.putString(CounterFragment.MSG_CONTENT, getString(R.string.activity_async_task));
            counterFragment.setArguments(data);
            fragmentManager.beginTransaction().replace(R.id.fragmentCounter, counterFragment).commit();
        }
    }

    /////////////// IAsyncTaskEvents's methods implementation //////////////////////
    @Override
    public void onPreExecute() {
        Toast.makeText(this,R.string.thread_pre_execute, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this,R.string.thread_post_execute, Toast.LENGTH_SHORT).show();
        counterFragment.updateFragmentCounterStatus(getString(R.string.done));
        counterAsyncTask = null;

    }

    @Override
    public void onProgressUpdate(Integer num) {
        counterFragment.updateFragmentCounterStatus(String.valueOf(num));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this,R.string.async_task_cancelled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createAsyncTask() {
        Toast.makeText(this,R.string.async_task_created, Toast.LENGTH_SHORT).show();
        counterAsyncTask = new CounterAsyncTask(this);
    }

    @Override
    public void startAsyncTask() {
        if(counterAsyncTask == null || counterAsyncTask.isCancelled()){
            Toast.makeText(this, R.string.click_create, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,  R.string.async_task_started, Toast.LENGTH_SHORT).show();
            counterAsyncTask.execute(10);
        }
    }

    @Override
    public void cancelAsyncTask() {
        if(counterAsyncTask == null || counterAsyncTask.isCancelled()){
            Toast.makeText(this,  R.string.click_create, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.async_task_cancelled, Toast.LENGTH_SHORT).show();
            counterAsyncTask.cancel(true);
        }
    }
}
