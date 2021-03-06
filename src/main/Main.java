package main; /**
 * Created by tareq on 12/3/2015.
 */

import controller.BrowserViewController;
import controller.DownloadController;
import controller.HistoryViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import model.download.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Main extends Application {

    public Stage stage;
    public String searchEngine = "google";
    public BrowserViewController browserController;
    public final Hashtable<Tab, WebTab> tabWebTabHashtable = new Hashtable<>();
    public static ObservableList<WebPage> webPageList = FXCollections.observableArrayList();
    public static ObservableList<Link> bookmarkList = FXCollections.observableArrayList();
    public static ArrayList<String> bookmarkedURL = new ArrayList<>();
    public static ObservableList<Data> downloadList = FXCollections.observableArrayList();
    public String[] downloadableExtensions = {".doc", ".xls", ".zip", ".tgz", ".jar" , ".pdf", ".mp3"};
    //public static ObservableList
    public HistoryManager historyManager;
    public BookmarkManager bookmarkManager;

    @Override
    public void init()
    {
        System.out.println("Starting...");
        historyManager = new HistoryManager();
        bookmarkManager = new BookmarkManager();
    }


    @Override
    public void start(Stage primaryStage) throws IOException
    {
        stage = primaryStage;
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        primaryStage.setTitle("Web Browser");
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/view/BrowserView.fxml"));
        browserController = loader.getController();
        BrowserViewController.setMain(this);
        WebTab.setMain(this);
        HistoryViewController.setMain(this);
        DownloadController.setMain(this);
        Data.setMain(this);
        HomePage.setMain(this);
        Scene scene = new Scene(root, 663, 508);
        String css = this.getClass().getResource("/style/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        //scene.getStylesheets().add("sample/progress.css");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                System.out.println("minimized:" + newValue.booleanValue());
//                if(newValue.booleanValue())
//                {
//                    Platform.setImplicitExit(false);
//                }
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Out");
                theEnd();
            }
        });
        primaryStage.show();
    }

    @Override
    public void stop()
    {
        theEnd();
    }

    private void theEnd()
    {
        System.out.println("Exiting...");
        historyManager.saveHistory();
        bookmarkManager.saveBookmark();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
