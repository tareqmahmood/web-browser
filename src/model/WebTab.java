package model;

import controller.BrowserViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.event.EventHandler;
import main.Main;
import model.download.Download;
import model.download.DownloadManager;

import javax.imageio.ImageIO;
import javax.security.auth.callback.Callback;
import javax.swing.event.HyperlinkListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Created by tareq on 11/26/2015.
 */
public class WebTab {
    WebTab my;
    public WebView webView;
    public WebEngine webEngine;
    Tab tab;
    Date date;
    int idx;
    String url;
    String tabText;
    static Main main;
    java.net.CookieManager manager;
    static BrowserViewController controller;
    boolean downloadProperty = false;
    public boolean isBookmarked = false;
    public Link link;
    public Stack<String> forwardStack;
    public Stack<String> backwardStack;
    boolean backPressed = false;
    boolean forPressed = false;
    ProgressIndicator pi;
    public ImageView ImgWarning;
    public WebTab(BrowserViewController controller)
    {
        this(controller, "Home");
    }

    public WebTab(BrowserViewController controller, boolean downloadProperty)
    {
        this(controller, "Home");
        this.downloadProperty = downloadProperty;
    }

    public WebTab(BrowserViewController controller, String url)
    {
        this.controller = controller;
        this.url = url;
        my = this;
        tab = new Tab("Home");
        pi = new ProgressIndicator();
        forwardStack = new Stack<>();
        backwardStack = new Stack<>();
        controller.tabPane.getTabs().add(tab);
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                controller.setBookMark(isBookmarked);
                controller.txtUrlBar.setText(my.url);
            }
        });
        tab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    webEngine.load(null);
                }
                catch (Exception e)
                {
                    System.out.println("Tab closed");
                }
            }
        });
        webView = new WebView();

        webView.getEngine().setCreatePopupHandler(new javafx.util.Callback<PopupFeatures, WebEngine>() {
            @Override
            public WebEngine call(PopupFeatures param) {
                WebTab webTab = new WebTab(controller, true);
                return webTab.webEngine;
            }
        });
        // setup webview
        //webView.setContextMenuEnabled(false);
        //creatContextMenu(webView);

        manager = new java.net.CookieManager();

        webEngine = webView.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            tab.setText(webEngine.getTitle());
                            Tab currentTab = controller.tabPane.getSelectionModel().getSelectedItem();
                            if (tab == currentTab) controller.txtUrlBar.setText(webEngine.getLocation());
                            else {
                                //System.out.println("Not selected : url not shown");
                            }
                            if (backPressed) {
                                backPressed = false;
                            } else if (forPressed) {
                                forPressed = false;
                            } else {
                                backwardStack.push(webEngine.getLocation());
                                forwardStack.clear();
                            }
                            //System.out.println("load call : " + webEngine.getLocation());
                            if (webEngine.getTitle() != null)
                                tab.setGraphic(Favicon.getFavicon(webEngine.getLocation().toString()));
                            date = new Date();
                            if (webEngine.getTitle() != null) {
                                main.webPageList.add(0, new WebPage(webEngine.getLocation(), webEngine.getTitle(), date));
//                                if(isBookmarked)
//                                {
//                                    link = new Link(webEngine.getTitle(), webEngine.getLocation());
//                                    main.bookmarkList.add(0, link);
//                                }

                                isBookmarked = main.bookmarkedURL.contains(webEngine.getLocation());
                                my.url = webEngine.getLocation();
                                if(controller.tabPane.getSelectionModel().getSelectedItem() == tab)
                                {
                                    controller.txtUrlBar.setText(my.url);
                                    controller.setBookMark(isBookmarked);
                                }
                            }
                        } else if (newState == State.FAILED) {
                            System.out.println("Error in connection");
                            tab.setText("Error loading page");
                            if (ImgWarning == null) ImgWarning = new ImageView("/images/warning.png");
                            tab.setGraphic(ImgWarning);
                            tab.setContent(ErrorPage.showErrorPage());
                        } else if (newState == State.SCHEDULED) {
                            try {
                                downloadProperty = false;
                                my.url = webEngine.getLocation();
                                idx = my.url.indexOf("//") + 2;
                                if (my.url.length() > 30) tabText = my.url.substring(idx, 30);
                                else tabText = my.url.substring(idx, my.url.length() - 1);
                                tab.setText(tabText);
                                //System.out.println("ready call : " + webEngine.getLocation());
                                tab.setGraphic(pi);
                                tab.setContent(webView);
                            } catch (Exception e) {
                                System.out.println("Tab closed");
                            }
                        } else if (newState == State.RUNNING) {
                            if(downloadProperty) webEngine.getLoadWorker().cancel();
                            if (webEngine.getTitle() != null) tab.setText(webEngine.getTitle());
                        }
                    }
                });
        //webEngine.getLoadWorker().stateProperty().addListener();
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(String ext: main.downloadableExtensions)
                {
                    if(newValue.endsWith(ext))
                    {
                        try {
                            URL fileURL = new URL(newValue);
                            System.out.println("Download : " + fileURL);
                            downloadProperty = true;
                            DownloadManager.startDownload(fileURL, main);
                            break;
                            //webEngine.getLoadWorker().cancel();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    downloadProperty = false;
                }
            }
        });
        main.tabWebTabHashtable.put(tab, this);
        pi.setPrefSize(16, 16);
        if(this.url.equals("Home"))
        {
            tab.setGraphic(new ImageView("/images/home.png"));
            tab.setContent(HomePage.showHomepage(this));
        }
        else
        {
            load(url);
        }
    }

    public void load(String url)
    {
        webEngine.load(url);
        tab.setContent(webView);
        this.url = url;
    }

    public void back()
    {
        if(backwardStack.size() < 2) return;
        String url = backwardStack.pop();
        forwardStack.push(url);
        url = backwardStack.peek();
        System.out.println("back call : " + url);
        backPressed = true;
        webEngine.load(url);
    }

    public void forward()
    {
        if(forwardStack.size() == 0) return;
        String url = forwardStack.pop();
        backwardStack.push(url);
        System.out.println(url);
        forPressed = true;
        webEngine.load(url);
    }

    public void manageRightClick()
    {

    }

    public static void setMain(Main m) {main = m;}
    public static void setController(BrowserViewController c) {controller = c;}
}
