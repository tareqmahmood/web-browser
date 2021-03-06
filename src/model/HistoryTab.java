package model;

import controller.HistoryViewController;
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
 * Created by tareq on 12/4/2015.
 */
public class HistoryTab implements Runnable {
    static public boolean isOpened = false;
    Thread thr;
    TabPane tabPane;
    Main main;
    HistoryViewController hController;
    public HistoryTab(TabPane tabPane, Main main)
    {
        isOpened = true;
        this.main = main;
        this.tabPane = tabPane;
        thr = new Thread(this);
        thr.setName("History");
        thr.start();
    }
    @Override
    public void run()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                Parent historyRoot = null;
                try {
                    historyRoot = loader.load(getClass().getResource("/view/HistoryView.fxml"));
                    hController = loader.getController();

                    //hController.setMain(main);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tab tab = new Tab("History");
                tab.setOnSelectionChanged(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        hController.controller.setBookMark(false);
                        hController.controller.txtUrlBar.setText("History");
                    }
                });
                tab.setContent(historyRoot);
                tab.setGraphic(new ImageView("/images/history.png"));
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
                tab.setOnClosed(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        isOpened = false;
                    }
                });
            }
        });
    }
}
