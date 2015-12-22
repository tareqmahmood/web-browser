package model;

import controller.DownloadController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import main.Main;

import java.io.IOException;

/**
 * Created by tareq on 12/17/2015.
 */
public class DownloadTab implements Runnable {
    Thread thr;
    TabPane tabPane;
    Main main;
    DownloadController dController;
    public DownloadTab(TabPane tabPane, Main main)
    {
        this.main = main;
        this.tabPane = tabPane;
        thr = new Thread(this);
        thr.setName("Download");
        thr.start();
    }
    @Override
    public void run() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            Parent downloadRoot = null;
            try {
                downloadRoot = loader.load(getClass().getResource("/view/DownloadView.fxml"));
                dController = loader.getController();

                dController.setMain(main);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tab tab = new Tab("Downloads");
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    dController.controller.setBookMark(false);
                    dController.controller.txtUrlBar.setText("Downloads");
                }
            });
            tab.setContent(downloadRoot);
            tab.setGraphic(new ImageView("/images/download.png"));
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        });
    }
}
