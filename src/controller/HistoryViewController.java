package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.WebPage;
import model.WebTab;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by tareq on 12/4/2015.
 */
public class HistoryViewController implements Initializable
{
    public static Main main;
    public static BrowserViewController controller;
    public static ArrayList<WebPage> deletePageList = new ArrayList<>();

    @FXML
    private TableView<WebPage> tableHistory;

    @FXML
    private TableColumn<WebPage, CheckBox> columnSelect;

    @FXML
    private TableColumn<WebPage, String> columnTime;

    @FXML
    private TableColumn<WebPage, String> columnTitle;

    @FXML
    private TableColumn<WebPage, String> columnURL;

    @FXML
    private Button btnDeleteSelected;

    @FXML
    private Button btnDeleteAll;

    @FXML
    void deleteAllAction(ActionEvent event) {
        Main.webPageList.clear();
        HistoryViewController.deletePageList.clear();

    }

    @FXML
    void deleteSelectedAction(ActionEvent event) {
        int len = deletePageList.size();
        Main.webPageList.removeAll(deletePageList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnSelect.setCellValueFactory(new PropertyValueFactory<WebPage, CheckBox>("checkBox"));
        columnTime.setCellValueFactory(new PropertyValueFactory<WebPage, String>("time"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<WebPage, String>("title"));
        columnURL.setCellValueFactory(new PropertyValueFactory<WebPage, String>("url"));
        //main.webPageList.add(new WebPage("http://google.com", "Google", new Date()));
        columnTime.setStyle("-fx-alignment: center");
        columnTitle.setStyle("-fx-alignment: center");
        tableHistory.setItems(Main.webPageList);
        tableHistory.setRowFactory(tv -> {
            TableRow<WebPage> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    WebPage rowData = row.getItem();
                    new WebTab(controller, rowData.getUrl());
                }
            });
            return row;
        });
    }

    public static void setMain(Main m)
    {
        main = m;
    }

    public static void setController(BrowserViewController c)
    {
        controller = c;
    }
}
