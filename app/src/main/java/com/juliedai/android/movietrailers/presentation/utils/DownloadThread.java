package com.juliedai.android.movietrailers.presentation.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author  Julie Dai
 */

public class DownloadThread extends Thread {

    private static final String LOG_TAG = "_DownloadThread_";
    private static final int BUFFER_SIZE = 8192;
    private static final int KB_SIZE = 1024;
    private final String imgUrl;
    private final DownloadCallBack downloadCallBack;
    private int progress = 0;
    private long lastUpdateTime;


    public DownloadThread(String url, DownloadCallBack downloadCallBack){
        this.imgUrl = url;
        this.downloadCallBack = downloadCallBack;
    }


    @Override
    public void run(){
        Log.d(LOG_TAG,"> run");

        File file = createFile();
        if(file == null){
            downloadCallBack.onError("Can't create file");
            return;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(imgUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                downloadCallBack.onError("Server return HTTP response code: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }

            int fileLength = connection.getContentLength();
            Log.d(LOG_TAG, "File size: " + fileLength/KB_SIZE +" KB");

            inputStream = new BufferedInputStream(url.openStream(), BUFFER_SIZE); // Input stream (downloading file)
            fos = new FileOutputStream(file.getPath()); // Output stream (Saving file)

            int next;
            byte[] data = new byte[KB_SIZE];
            while((next = inputStream.read(data)) != -1){
                fos.write(data, 0, next);

                updateProgress(fos, fileLength);
            }

            downloadCallBack.onDownloadFinished(file.getPath());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            downloadCallBack.onError("MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            downloadCallBack.onError("IOException: " + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    private void updateProgress(FileOutputStream fos, int fileLength) throws IOException{
        if(lastUpdateTime == 0 || System.currentTimeMillis() > lastUpdateTime + 500){
            int count = ((int) fos.getChannel().size()) * 100 / fileLength;
            if (count > progress) {
                progress = count;
                lastUpdateTime = System.currentTimeMillis();
                downloadCallBack.onProgressUpdate(progress);
            }
        }
    }

    private File createFile() {
        File mediaStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator);
        if(!mediaStorageDirectory.exists()){ // If the storage directory doesn't exist, create it
            if(!mediaStorageDirectory.mkdirs()){
                Log.e(LOG_TAG,"Failed to create storage directory");
                return null;
            }
        }
        String imgName = createImageFileName() + ".jpg"; // Create a media file name
        return new File(mediaStorageDirectory.getPath() + File.separator + imgName);
    }

    @NonNull
    private String createImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return "IMG_" + timeStamp;
    }

    public interface DownloadCallBack{
        void onProgressUpdate(int percent);
        void onDownloadFinished(String filePath);
        void onError(String error);
    }
}
