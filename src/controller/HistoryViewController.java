package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.WebPage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by tareq on 12/4/2015.
 */
public class HistoryViewController implements Initializable
{
    public Main main;
    public static ArrayList<WebPage> deletePageList = new ArrayList<>();

    @FXML
    private TableView<WebPage> tableHistory;

    @FXML
    private TableColumn<WebPage, CheckBox> columnSelect;

    @FXML
    private TableColumn<WebPage, Date> columnTime;

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
        columnTime.setCellValueFactory(new PropertyValueFactory<WebPage, Date>("date"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<WebPage, String>("title"));
        columnURL.setCellValueFactory(new PropertyValueFactory<WebPage, String>("url"));
        //main.webPageList.add(new WebPage("http://google.com", "Google", new Date()));
        columnTime.setStyle("-fx-alignment: center");
        columnTitle.setStyle("-fx-alignment: center");
        tableHistory.setItems(Main.webPageList);
    }

    public void setMain(Main main)
    {
        this.main = main;
    }
}
