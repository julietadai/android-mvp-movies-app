package com.juliedai.android.movietrailers.domain.interator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author  Julie Dai
 */

public class DownloadInteractor {

    public static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private Context context;

    public DownloadInteractor(Context viewContext) {
        this.context = viewContext;
    }

    public boolean isPermissionGranted(){
        return ContextCompat.checkSelfPermission(context, PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }
}
