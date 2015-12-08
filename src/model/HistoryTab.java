package model;

import controller.HistoryViewController;
import javafx.application.Platform;
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
    Thread thr;
    TabPane tabPane;
    Main main;
    HistoryViewController hController;
    public HistoryTab(TabPane tabPane, Main main)
    {
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
                    tab.setContent(historyRoot);
                    tab.setGraphic(new ImageView("/images/history.png"));
                    tabPane.getTabs().add(tab);
                }
            });
    }
}
