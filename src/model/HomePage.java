package model;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import main.Main;

/**
 * Created by tareq on 12/3/2015.
 */
public class HomePage {
    public static Main main;
    public static StackPane showHomepage(WebTab webTab)
    {
        StackPane pane = new StackPane();
        FlowPane fpane = new FlowPane();
        fpane.setStyle("-fx-alignment: center");
        TextField text = new TextField();
        text.setFont(new Font(15));
        //text.setMinWidth(300);
        //text.setMaxWidth(300);
        text.setPrefSize(400, 40);
        text.setPromptText("Search in " + main.searchEngine);
        text.setOnAction(event -> {
            String search = text.getText();
            String url = "http://www.google.com/search?q=";
            search.trim().toLowerCase();
            search = search.replace(' ', '+');
            url = url + search;
            webTab.webEngine.load(url);
        });
        //text.setLayoutX(pane.getHeight() / 2);
        //text.setLayoutY(pane.getWidth() / 2);
        //text.setStyle("-fx-scale-x: 1");
        String path = null;
        if(main.searchEngine.equals("google")) path = "/images/google.png";
        else path = "/images/yahoo.png";
        ImageView imv = new ImageView(path);
        fpane.getChildren().add(imv);
        fpane.getChildren().add(text);
        pane.getChildren().add(fpane);
        StackPane.setAlignment(fpane, Pos.CENTER);
//        pane.setLeftBorder(text, 100.0);
//        pane.setRightStack(text, 100.0);
//        pane.setTopStack(text, 0.0);
//        pane.setBottomStack(text, 0.0);
        //BorderPane borderPane = new BorderPane();
        return pane;
    }

    public static void setMain(Main m)
    {
        main = m;
    }
}
