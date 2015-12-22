package model.download;


import controller.DownloadController;
import javafx.application.Platform;
import main.Main;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Observable;

//Created by Coder on 01-Dec-15.

public class Download extends Observable implements Runnable {

    // Maximum size of a download chunk in byte.
    private static final int MAX_CHUNK_SIZE = 1024;
    public Thread thr;
    // Status names.
    public static final String STATUSES[] = {"Downloading", "Paused", "Complete", "Cancelled", "Error"};

    // Status codes.
    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETE = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;

    private URL url; // requested download URL
    private int size; // size of the requested download file in bytes
    private int downloaded; // number of bytes downloaded
    private int status; // current status of download

    public static Main main;
    public static void setMain(Main m) { main = m;}

    public static DownloadController controller;
    public static void setController(DownloadController c) { controller = c;}

    public Data t;
    public void setTable(Data data) { t = data; }

    // Constructor of Download.
    public Download(URL url) {
        this.url = url;
        System.out.println(url);
        size = -1;
        downloaded = 0;
        status = DOWNLOADING;
        thr = new Thread(this);
        thr.start();
        // Begin the download.
        //download();
    }

    // Get this download's URL.
    public String getUrl() {
        return url.toString();
    }

    // Get this download's size.
    public double getSize() {
        double d = (double)size/1000000;
        d = Double.parseDouble(new DecimalFormat("##.##").format(d));
        return d;
    }

    // Get this download's progress.
    synchronized public double getProgress() {
        double value = ((double) downloaded / size) * 100;
        value = Double.parseDouble(new DecimalFormat("##").format(value));
        return value ;
    }

    // Get this download's status.
    public int getStatus() {
        return status;
    }

    // Pause this download.
    public void pause() {
        status = PAUSED;
        stateChanged();
    }

    // Resume this download.
    public void resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }

    // Cancel this download.
    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }

    // Mark this download as having an error.
    private void error() {
        status = ERROR;
        stateChanged();
    }

    // Start or resume downloading.
    private void download() {
        Thread thread = new Thread(this);
        thread.start();
    }

    // Get file name portion of URL.
    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);//http://www.tutorialspoint.com/java/java_string_substring.htm
                                                                 //http://stackoverflow.com/questions/605696/get-file-name-from-url
    }

    // Download file.
    public void run() {
        RandomAccessFile file = null;//https://docs.oracle.com/javase/tutorial/essential/io/rafs.html
        InputStream stream = null;//http://stackoverflow.com/questions/1830698/what-is-inputstream-output-stream-why-do-we-use-them-and-when-do-we-use-each

        try {
            // Open connection to URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Specify what portion of file to download.
            connection.setRequestProperty("Range","bytes=" + downloaded + "-");

            // Connect to server.
            connection.connect();

            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                error();
            }

            // Check for valid content length.
            int contentLength = connection.getContentLength();//The content length represents the number of bytes in the requested file.

            if (contentLength < 1) {
                error();
            }

            /* Set the size for this download */
            if (size == -1) {
                size = contentLength;
                t.setSize(String.valueOf(getSize())+"MB");
                stateChanged();
            }

            file = new RandomAccessFile(directory+getFileName(url), "rw");
            file.seek(downloaded);

            stream = connection.getInputStream();
            while (status == DOWNLOADING) {
                /* Size chunk according to how much of the
                   file is left to download. */
                byte chunk[];
                if (size - downloaded > MAX_CHUNK_SIZE) {
                    chunk = new byte[MAX_CHUNK_SIZE];
                } else {
                    chunk = new byte[size - downloaded];
                }

                // Read from server into chunk.
                int read = stream.read(chunk);
                if (read == -1)
                    break;

                // Write chunk to file.
                file.write(chunk, 0, read);
                downloaded += read;
                t.setProgress(getProgress());
                stateChanged();
            }

      /* Change status to complete if this point was
         reached because downloading has finished. */
            if (status == DOWNLOADING) {
                status = COMPLETE;
                t.setStatus(STATUSES[status]);
                System.out.println(url.getFile());
                stateChanged();
            }
        } catch (Exception e) {
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {}
            }

            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {}
            }
        }
    }

    String directory = null;
    public void setDirectory(String s) {
        directory = s+"\\";
    }

    // Notify observers that this download's status has changed.
    private void stateChanged() {
        if(status == 2){
            Platform.runLater(() -> t.controlBtn.getButtons().remove(0));
            Platform.runLater(() -> t.cancel_clearBtn.setText("CLEAR"));
        }
        setChanged();
        notifyObservers();
    }
}