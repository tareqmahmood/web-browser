package model;

import controller.HistoryViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tareq on 12/3/2015.
 */
public class WebPage {
    CheckBox checkBox;
    SimpleStringProperty url;
    SimpleStringProperty title;
    SimpleStringProperty time;

    Date date;

    public WebPage(String url, String title, String time) {
        this.url = new SimpleStringProperty(url);
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleStringProperty(time);
        checkBox = new CheckBox();
        checkBox.setText(null);
        checkBox.setOnAction(event -> {
            System.out.println(checkBox.isSelected() + " " + getTitle());
            if(checkBox.isSelected() == true)
            {
                HistoryViewController.deletePageList.add(this);
            }
            else
            {
                HistoryViewController.deletePageList.remove(this);
            }
        });
        checkBox.setSelected(false);
    }

    public WebPage(String url, String title, Date date) {
        this(url, title, date.toString());
    }

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getTime() { return time.get(); }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
