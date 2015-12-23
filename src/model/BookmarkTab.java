package model;

import controller.BookmarkViewController;
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
 * Created by tareq on 12/9/2015.
 */
public class BookmarkTab implements Runnable{
    static public boolean isOpened = false;
    Thread thr;
    TabPane tabPane;
    Main main;
    BookmarkViewController bController;

    public BookmarkTab(TabPane tabPane, Main main)
    {
        isOpened = true;
        this.main = main;
        this.tabPane = tabPane;
        thr = new Thread(this);
        thr.setName("Bookmark");
        thr.start();
    }

    @Override
    public void run()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                Parent bookmarkRoot = null;
                try {
                    bookmarkRoot = loader.load(getClass().getResource("/view/BookmarkView.fxml"));
                    bController = loader.getController();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tab tab = new Tab("Bookmarks");
                tab.setOnSelectionChanged(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        bController.controller.setBookMark(false);
                        bController.controller.txtUrlBar.setText("Bookmarks");
                    }
                });
                tab.setContent(bookmarkRoot);
                tab.setGraphic(new ImageView("/images/bookmark.png"));
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
