package model;

import controller.HistoryViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tareq on 12/3/2015.
 */
public class WebPage implements Serializable {
    CheckBox checkBox;
    SimpleStringProperty url;
    SimpleStringProperty title;
    Date date;

    public WebPage(String url, String title, Date date) {
        this.url = new SimpleStringProperty(url);
        this.title = new SimpleStringProperty(title);
        this.date = date;
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
}
