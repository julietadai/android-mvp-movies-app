package com.juliedai.android.movietrailers.presentation.view.activity.download;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.presentation.utils.DownloadService;
import com.juliedai.android.movietrailers.presentation.presenter.DownloadPresenterImpl;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author  Julie Dai
 */

public class DownloadActivity extends AppCompatActivity implements Contract.View {
    private static final String LOG_TAG = "_DownloadActivity_";

    public static final int PERMISSIONS_REQUEST_CODE = 42;
    public static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String ARG_MOVIE_MODEL = "Movie-Model-Data";
    public static final String ARG_FILE_PATH = "Image-File-Path";
    private BroadcastReceiver broadcastReceiver;
    private Contract.Presenter present;

    public static void startActivity(Context context, MovieModel movieModel){
        Log.d(LOG_TAG, "> startActivity, movieModel= " + movieModel);
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra(ARG_MOVIE_MODEL, (Parcelable)movieModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String filePath = intent.getStringExtra(ARG_FILE_PATH);
                Log.d(LOG_TAG, "> onReceive, filePath: " + filePath);

                if (!TextUtils.isEmpty(filePath)) {
                    showImage(filePath);
                }
            }
        };

        present = new DownloadPresenterImpl(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Register local broadcast
        IntentFilter filter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop(){
        // Unregister local broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    public void showImage(String filePath) {
        ImageView imgView = findViewById(R.id.ivDownloadCoverMovieImg);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imgView.setImageBitmap(bitmap);
    }

    @Override
    public void startDownloadService() {
        MovieModel movieModel = (MovieModel) getIntent().getParcelableExtra(ARG_MOVIE_MODEL);
        Log.d(LOG_TAG,"> startDownloadService: movieModel=" + movieModel);
        if(movieModel == null){
            return;
        }

        Log.d(LOG_TAG, "> onRequestPermissionsResult, startDownloadService");
        DownloadService.startService(this, movieModel.getCoverImageResource());
    }

    @Override
    public void requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)){ // Should we show explanation?
            // Show an explanation to the user
            // After the user sees the explanation, will request the permission
            showExplainingRationalDialog();
        }else{
            // No explanation needed, we can request the permission
            requestWritePermission();
        }
    }

    private void showExplainingRationalDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.download_dialog_title);
        builder.setMessage(R.string.download_dialog_message);
        builder.setPositiveButton(R.string.download_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestWritePermission();
            }
        });
        builder.setNegativeButton(R.string.download_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // No Permission - finish activity
                finishActivity();
            }
        });
        builder.create().show();
    }

    private void requestWritePermission() {
        // PERMISSIONS_REQUEST_CODE is an app-defined int constant.
        // The callback method gets the result of the request.
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.something_went_wrong_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    private void finishActivity() {
        this.finish();
    }
}
