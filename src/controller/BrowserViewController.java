package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import main.Main;
import model.HistoryTab;
import model.WebTab;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserViewController implements Initializable
{
    static private Main main;
    static String newTabDefault = "New tab";
    public String searchURL = "http://www.google.com/search?q=";
    public String searchEngine = "google";
    public final ImageView googleImage = new ImageView("/images/google_search.png");
    public final ImageView yahooImage = new ImageView("/images/yahoo_search.png");
    public final ImageView googleMenuImage = new ImageView("/images/google_search.png");
    public final ImageView yahooMenuImage = new ImageView("/images/yahoo_search.png");

    @FXML
    public Button btnForward;

    @FXML
    public Button btnBackword;

    @FXML
    public TextField txtUrlBar;

    @FXML
    private TextField txtSearchBox;

    @FXML
    public TabPane tabPane;

    @FXML
    private Button btnNewTab;

    @FXML
    private ToggleButton btnBookmark;

    @FXML
    private MenuButton searchMenu;

    @FXML
    private MenuItem menuGoogle;

    @FXML
    private MenuItem menuYahoo;

    @FXML
    void bookmarkAction(ActionEvent event) {

    }

    @FXML
    WebTab createNewTab(ActionEvent event) {
        return new WebTab(this);
    }

    @FXML
    void webBackwardAction(ActionEvent event) {
        System.out.println("Back");
        if(tabPane.getTabs().size() == 0) return;
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        WebTab currentWebTab = main.tabWebTabHashtable.get(currentTab);
        currentWebTab.back();
    }

    @FXML
    void webForwardAction(ActionEvent event) {
        System.out.println("Front");
        if(tabPane.getTabs().size() == 0) return;
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        WebTab currentWebTab = main.tabWebTabHashtable.get(currentTab);
        currentWebTab.forward();
    }

    @FXML
    void webSearchBoxAction(ActionEvent event) {
        String search = txtSearchBox.getText();
        String url = searchURL;
        search.trim().toLowerCase();
        search = search.replace(' ', '+');
        url = url + search;
        loadURL(url);
    }

    @FXML
    void webUrlBarAction(ActionEvent event) {
        loadURL(txtUrlBar.getText());
    }

    @FXML
    void searchGoogleAction(ActionEvent event) {
        searchMenu.setGraphic(googleImage);
        searchEngine = "google";
        searchURL = "http://www.google.com/search?q=";
    }

    @FXML
    void searchYahooAction(ActionEvent event) {
        searchMenu.setGraphic(yahooImage);
        searchEngine = "yahoo";
        searchURL = "search.yahoo.com/search?p=";
    }

    @FXML
    void openHistoryTab(ActionEvent event) {
        new HistoryTab(tabPane, main);
    }

    void loadURL(String url)
    {
        url = smoothUrl(url);
        if(tabPane.getTabs().size() != 0)
        {
            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            WebTab currentWebTab = main.tabWebTabHashtable.get(currentTab);
            if(currentWebTab == null)
            {
                new WebTab(this, url);
            }
            else
            {
                currentWebTab.load(url);
            }
        }
        else
        {
            WebTab currentWebTab = new WebTab(this, url);
        }
        if(txtUrlBar.getText().equals(newTabDefault) || txtUrlBar.getText().length() == 0){
            txtUrlBar.setText(url);
        }
    }

    String smoothUrl(String url)
    {
        if(!url.startsWith("http") && !url.startsWith("https")){
            url = "http://" + url;
        }
        return url;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setStyle("-fx-alignment: center-left");
        tabPane.setTabMinWidth(100.2);
        tabPane.setTabMaxWidth(120);
        //tabPane.setTabMinHeight(40);
        searchMenu.setGraphic(googleImage);
        menuGoogle.setGraphic(googleMenuImage);
        menuYahoo.setGraphic(yahooMenuImage);
        WebTab.setController(this);
        BrowserViewController c = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new WebTab(c);
            }
        });
    }

    public static void setMain(Main m)
    {
        main = m;
    }
}
