package model.download;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.Main;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tareq on 12/10/2015.
 */

public class Data {

    private StringProperty url;
    private StringProperty size;
    private ProgressBar bar;
    private StringProperty status;
    public ButtonBar controlBtn;
    private Text text = new Text();
    Button cancel_clearBtn = new Button("CANCEL");
    ToggleButton pause_resumeBtn = new ToggleButton("PAUSE");
    private Data my;

    public Data(String url, String size, String status, ProgressBar bar) {
        this.url = new SimpleStringProperty(url);
        this.size = new SimpleStringProperty(size);
        this.status = new SimpleStringProperty(status);
        this.bar = bar;

        bar.getStylesheets().add(
                getClass().getResource(
                        "/style/striped-progress.css"
                ).toExternalForm()
        );

        controlBtn = new ButtonBar();
        my = this;
        my.bar.setStyle("-fx-accent: green;");

        bar.setMaxWidth(5000.00);
        controlBtn.setButtonData(pause_resumeBtn, ButtonBar.ButtonData.LEFT);
        controlBtn.setButtonData(cancel_clearBtn, ButtonBar.ButtonData.LEFT);
        // Add buttons to the ButtonBar
        controlBtn.getButtons().addAll(pause_resumeBtn, cancel_clearBtn);
        pause_resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (pause_resumeBtn.getText() == "PAUSE") {
                    pause_resumeBtn.setText("RESUME");
                    my.bar.setStyle("-fx-accent: gold;");
                    download.pause();
                    my.setStatus("Paused");
                }
                else if(pause_resumeBtn.getText() == "RESTART"){
                    String s = download.getUrl();
                    URL url = null;
                    try {
                        url = new URL(s);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Download d = new Download(url);
                    int i = d.getStatus();
                    String status = d.STATUSES[i];
                    Data t = new Data(s, String.valueOf(d.getSize()), status, new ProgressBar(0));
                    d.setTable(t);
                    t.setDownload(d);
                    int index = main.downloadList.indexOf(my);
                    main.downloadList.set(index , t);
                    pause_resumeBtn.setText("PAUSE");
                }
                else {
                    pause_resumeBtn.setText("PAUSE");
                    my.bar.setStyle("-fx-accent: green;");
                    download.resume();
                    my.setStatus("Downloading");
                }
            }
        });

        cancel_clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cancel_clearBtn.getText() == "CANCEL") {
                    download.pause();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("CONFIRMATION");
                    alert.setHeaderText("You are about to cancel this download.");
                    alert.setContentText("Are you sure ? ");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            cancel_clearBtn.setText("CLEAR");
                            download.thr.stop();
                            my.setStatus("Cancelled");
                            my.bar.setStyle("-fx-accent: red;");
                            my.pause_resumeBtn.setText("RESTART");
                            my.pause_resumeBtn.setSelected(false);
                        } else if(pause_resumeBtn.getText() == "PAUSE")download.resume();
                    });
                } else {
                    main.downloadList.remove(my);
                }
            }
        });
    }

    public ButtonBar getBtnbar() { return controlBtn; }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public ProgressBar getBar() {
        return bar;
    }

    public void setBar(ProgressBar bar) {
        this.bar = bar;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setProgress(double p)
    {
        bar.setProgress(p / 100.0);
    }

    public double getProgress()
    {
        return bar.getProgress();
    }

    public Download download;
    public void setDownload(Download d){download = d;}

    public static Main main;
    public static void setMain(Main m) { main = m;}
}
