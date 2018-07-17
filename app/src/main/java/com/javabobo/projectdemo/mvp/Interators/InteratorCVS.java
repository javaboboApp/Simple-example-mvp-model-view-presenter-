package com.javabobo.projectdemo.mvp.Interators;

import android.os.AsyncTask;

import com.javabobo.projectdemo.Models.Play;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luis on 14/02/2018.
 */

public class InteratorCVS {
    private static InteratorCVS uniqueInstance = new InteratorCVS();

    private InteratorCVS() {

    }

    public interface Listener {
        void onSucess(List<Play> string);

        void onFailured();
    }

    public static InteratorCVS getUniqueInstance() {
        return uniqueInstance;
    }

    public void queryCVS(String uri, Listener callBack) {

        DownloadFilesTask downloadFilesTask = new DownloadFilesTask(callBack);
        downloadFilesTask.execute(uri);
    }

    private class DownloadFilesTask extends AsyncTask<String, Void, List<String[]>> {
        private Listener listener;

        public DownloadFilesTask(Listener listener) {
            this.listener = listener;
        }

        protected List<String[]> doInBackground(String... url) {
            return downloadRemoteTextFileContent(url[0]);
        }


        protected void onPostExecute(List<String[]> result) {
            if (result != null) {
               listener.onSucess( printCVSContent(result));
            }else listener.onFailured();
        }
    }

    private List<Play> printCVSContent(List<String[]> result) {
        LinkedList<Play> playList = new LinkedList<>();
        for (int i = 0; i < result.size(); i++) {
            String[] rows = result.get(i);

            //cvsColumn += rows[0] + " " + rows[1] + " " + rows[2] + "\n";
            Play play = new Play();
            play.setDate(rows[1]);
            play.setHomeTeam(rows[2]);
            play.setAwayTeam(rows[3]);
            play.setResult(rows[6]);
            play.setTextToShow(play.getHomeTeam() +"x" + play.getAwayTeam() + ":" + play.getResult() );
            playList.add(play);

        }

        // fileContent.setText(cvsColumn);
        return playList;
    }

    private List<String[]> downloadRemoteTextFileContent(String url) {
        URL mUrl = null;
        List<String[]> csvLine = new ArrayList<>();
        String[] content = null;
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert mUrl != null;
            URLConnection connection = mUrl.openConnection();
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                content = line.split(",");
                csvLine.add(content);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLine;
    }
}
