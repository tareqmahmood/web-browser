package controller;

/**
 * Created by tareq on 12/9/2015.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Link;

import java.net.URL;
import java.util.ResourceBundle;

public class BookmarkViewController implements Initializable{

    @FXML
    private TableView<Link> tableHistory;

    @FXML
    private TableColumn<Link, CheckBox> columnSelect;

    @FXML
    private TableColumn<Link, String> columnTitle;

    @FXML
    private TableColumn<Link, String> columnURL;

    @FXML
    private Button btnDeleteSelected;

    @FXML
    private Button btnDeleteAll;

    @FXML
    void deleteAllAction(ActionEvent event) {

    }

    @FXML
    void deleteSelectedAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
