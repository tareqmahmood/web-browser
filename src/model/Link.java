package model;

import controller.BookmarkViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

/**
 * Created by tareq on 12/9/2015.
 */
public class Link {
    SimpleStringProperty url;
    SimpleStringProperty title;
    CheckBox checkBox;

    public Link(String title, String url) {
        this.title = new SimpleStringProperty(title);
        this.url = new SimpleStringProperty(url);
        checkBox = new CheckBox();
        checkBox.setText(null);
        checkBox.setOnAction(event -> {
            if(checkBox.isSelected() == true)
            {
                BookmarkViewController.deleteBookmarkList.add(this);
                BookmarkViewController.deleteBookmarkURL.add(getUrl());
            }
            else
            {
                BookmarkViewController.deleteBookmarkList.remove(this);
                BookmarkViewController.deleteBookmarkURL.remove(getUrl());
            }
        });
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

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
