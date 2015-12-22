package model.download;

import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import main.Main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tareq on 12/18/2015.
 */
public class DownloadManager
{
    public static void startDownload(URL url, Main main)
    {
        String s = url.toString();
        if(url != null) {
            String sd = null;
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            Window stage = null;
            final File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                sd = selectedDirectory.getAbsolutePath();
                Download d = new Download(url);
                int i = d.getStatus();
                String status = d.STATUSES[i];
                Data t = new Data(s, String.valueOf(d.getSize()), status, new ProgressBar(0));
                d.setTable(t);
                d.setDirectory(sd);
                t.setDownload(d);
                main.downloadList.add(t);
            }
        }
    }
}
