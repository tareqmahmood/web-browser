package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * Created by tareq on 12/3/2015.
 */
public class ErrorPage {
    public static StackPane showErrorPage()
    {
        StackPane pane = new StackPane();
        FlowPane fpane = new FlowPane();
        fpane.setStyle("-fx-alignment: center");
        Label label = new Label();
        label.setText("Error Loading Page");
        label.setFont(new Font(20));
        ImageView imv = new ImageView("/images/error.png");
        fpane.getChildren().add(imv);
        fpane.getChildren().add(label);
        pane.getChildren().add(fpane);
        StackPane.setAlignment(fpane, Pos.CENTER);
        return pane;
    }
}
