package main; /**
 * Created by tareq on 12/3/2015.
 */

import controller.BrowserViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.WebPage;
import model.WebTab;

import java.io.IOException;
import java.util.Hashtable;

public class Main extends Application {

    public Stage stage;
    public static BrowserViewController browserController;
    public final Hashtable<Tab, WebTab> tabWebTabHashtable = new Hashtable<>();
    public static ObservableList<WebPage> webPageList = FXCollections.observableArrayList();

    @Override
    public void init()
    {

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
        Scene scene = new Scene(root, 663, 508);
        //scene.getStylesheets().add("sample/progress.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
