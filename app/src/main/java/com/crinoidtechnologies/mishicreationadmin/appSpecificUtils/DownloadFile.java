package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.os.AsyncTask;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.listeners.FileDownloadListener;
import com.crinoidtechnologies.mishicreationadmin.models.DownloadData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;



/**
 * Created by shubham on 4/26/2018.
 */

public class DownloadFile extends AsyncTask<String, Integer, String> {

    private String audioUrl = "";
    private String audioPath = "";
    private DownloadData downloadData;
    private FileDownloadListener fileDownloadListener;

    public DownloadFile(DownloadData downloadData, FileDownloadListener fileDownloadListener) {
        this.audioUrl = downloadData.getAudioFile();

        this.downloadData = downloadData;
        this.fileDownloadListener = fileDownloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//            Toast.makeText(context, "Downloading....", Toast.LENGTH_SHORT).show();
            /*showDownloading(holderPlayer);*/
        fileDownloadListener.onPreExecute();
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        URLConnection connection = null;
        try {
            URL url = new URL(audioUrl);
            connection = (URLConnection) url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();

            /*    audioPath = Environment.getExternalStorageDirectory() + "/" + finalUrl;*/

            output = new FileOutputStream(Constants.STORAGE_PATH + "/" + downloadData.getAudioName() + Constants.FILE_STORAGE_EXTENSION);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            fileDownloadListener.onException(e);
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

//                if (connection != null)
//                    connection.disconnect();
//            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

            /*startPlayer(downloadData, holderPlayer);*/

        fileDownloadListener.onPostExecute();

//            adapterRefreshView.onClick();
           /* Toast.makeText(context, R.string.download_completed, Toast.LENGTH_SHORT).show();*/

    }
}
