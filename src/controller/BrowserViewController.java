package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import main.Main;
import main.Widged;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserViewController implements Initializable
{
    static private Main main;
    static String newTabDefault = "New tab";
    public String searchURL = "http://www.google.com/search?q=";
    public String[] avoidTabs = {"Home", "History", "Bookmark"};

    public final ImageView googleImage = new ImageView("/images/google_search.png");
    public final ImageView yahooImage = new ImageView("/images/yahoo_search.png");
    public final ImageView googleMenuImage = new ImageView("/images/google_search.png");
    public final ImageView yahooMenuImage = new ImageView("/images/yahoo_search.png");
    public final ImageView backwardImage = new ImageView("/images/Back.png");
    public final ImageView forwardImage = new ImageView("/images/For.png");
    public final ImageView newTabImage = new ImageView("/images/NewTab.png");
    public final ImageView bookmarkFalse = new ImageView("/images/Bookmark_false.png");
    public final ImageView bookmarkTrue = new ImageView("/images/Bookmark_true.png");
    public final ImageView Google = new ImageView("/images/google.png");
    public final ImageView Yahoo = new ImageView("/images/yahoo.png");

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
    public ToggleButton btnBookmark;

    @FXML
    private MenuButton searchMenu;

    @FXML
    private MenuItem menuGoogle;

    @FXML
    private MenuItem menuYahoo;

    @FXML
    void openDownloadTab(ActionEvent event) {
        new DownloadTab(tabPane, main);
    }

    @FXML
    void bookmarkAction(ActionEvent event) {
        if(tabPane.getTabs().size() == 0) return;
        System.out.println("Bookmarking");
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        for(String str : avoidTabs)
        {
            if(currentTab.getText().equals(str)) {
                btnBookmark.setSelected(false);
                return;
            }
        }
        WebTab currentWebTab = main.tabWebTabHashtable.get(currentTab);
        if(btnBookmark.isSelected())
        {
            btnBookmark.setGraphic(bookmarkTrue);
            currentWebTab.isBookmarked = true;
            currentWebTab.link = new Link(currentWebTab.webEngine.getTitle(), currentWebTab.webEngine.getLocation());
            main.bookmarkList.add(0, currentWebTab.link);
            main.bookmarkedURL.add(currentWebTab.webEngine.getLocation());
        }
        else
        {
            btnBookmark.setGraphic(bookmarkFalse);
            currentWebTab.isBookmarked = false;
            if(currentWebTab.link != null) main.bookmarkList.remove(currentWebTab.link);
            main.bookmarkedURL.remove(currentWebTab.webEngine.getLocation());
        }
    }

    @FXML
    void createNewTab(ActionEvent event) {
        new WebTab(this);
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
        main.searchEngine = "google";
        searchURL = "http://www.google.com/search?q=";
    }

    @FXML
    void searchYahooAction(ActionEvent event) {
        searchMenu.setGraphic(yahooImage);
        main.searchEngine = "yahoo";
        searchURL = "search.yahoo.com/search?p=";
    }

    @FXML
    void openHistoryTab(ActionEvent event) {
        new HistoryTab(tabPane, main);
    }

    @FXML
    void openBookmarkTab(ActionEvent event) {
        new BookmarkTab(tabPane, main);
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

    public void setBookMark(boolean state)
    {
        if(state == true)
        {
            btnBookmark.setGraphic(bookmarkTrue);
            btnBookmark.setSelected(true);
        }
        else
        {
            btnBookmark.setGraphic(bookmarkFalse);
            btnBookmark.setSelected(false);
        }
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
        btnBackword.setGraphic(backwardImage);
        btnForward.setGraphic(forwardImage);
        btnNewTab.setGraphic(newTabImage);
        btnBookmark.setGraphic(bookmarkFalse);
        WebTab.setController(this);
        HistoryViewController.setController(this);
        BookmarkViewController.setController(this);
        DownloadController.setController(this);
        BrowserViewController c = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(Widged.window.size() == 0)
                {
                    new WebTab(c);
                }
                else
                {
                    int len = Widged.window.size();
                    for(int i = 0; i < len; i++)
                    {
                        new WebTab(c, Widged.window.get(i).webView.getEngine().getLocation());
                    }
                }
            }
        });

    }

    public static void setMain(Main m)
    {
        main = m;
    }
}
