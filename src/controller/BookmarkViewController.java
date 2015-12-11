package controller;

/**
 * Created by tareq on 12/9/2015.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Link;
import model.WebTab;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookmarkViewController implements Initializable{

    public static BrowserViewController controller;
    public static ArrayList<Link> deleteBookmarkList = new ArrayList<>();
    public static ArrayList<String> deleteBookmarkURL = new ArrayList<>();

    @FXML
    private TableView<Link> tableBookmark;

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
        Main.bookmarkList.clear();
        Main.bookmarkedURL.clear();
        deleteBookmarkList.clear();
        deleteBookmarkURL.clear();
    }

    @FXML
    void deleteSelectedAction(ActionEvent event) {
        Main.bookmarkedURL.removeAll(deleteBookmarkURL);
        Main.bookmarkList.removeAll(deleteBookmarkList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnSelect.setCellValueFactory(new PropertyValueFactory<Link, CheckBox>("checkBox"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Link, String>("title"));
        columnURL.setCellValueFactory(new PropertyValueFactory<Link, String>("url"));
        columnTitle.setStyle("-fx-alignment: center");
        tableBookmark.setItems(Main.bookmarkList);
        tableBookmark.setRowFactory(tv -> {
            TableRow<Link> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    Link rowData = row.getItem();
                    new WebTab(controller, rowData.getUrl());
                }
            });
            return row;
        });
    }

    public static void setController(BrowserViewController c)
    {
        controller = c;
    }
}
