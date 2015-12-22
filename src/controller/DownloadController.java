package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import main.Main;
import model.download.Data;
import model.download.Download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadController implements Initializable{

    @FXML
    private TextField txtURL;

    @FXML
    public TableView<Data> downloadTable;

    @FXML
    private TableColumn<Data, String> urlColumn;

    @FXML
    private TableColumn<Data, String > sizeColumn;

    @FXML
    public TableColumn<Data, ProgressBar> progressColumn;

    @FXML
    public TableColumn<Data, String> statusColumn;

    @FXML
    private TableColumn<Data, ButtonBar> controlColumn;

    @FXML
    private Button btnStart;

    public static Download download;
    public static void setDownload(Download d){download = d;}

    public static Main main;
    public static void setMain(Main m) { main = m;}
    public static BrowserViewController controller;
    public static void setController(BrowserViewController c) {controller = c;}

    @FXML
    void startAction(ActionEvent event){
        String s = txtURL.getText();
        txtURL.clear();
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(url != null) {
            String sd = null;
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            Window stage = null;
            final File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                sd = selectedDirectory.getAbsolutePath();
                Download d = new Download(url);
                int i = d.getStatus();
                String status = d.STATUSES[i];
                Data t = new Data(s, String.valueOf(d.getSize()), status, new ProgressBar(0));
                d.setTable(t);
                d.setDirectory(sd);
                t.setDownload(d);
                main.downloadList.add(t);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        urlColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("url"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("size"));
        sizeColumn.setStyle("-fx-alignment: CENTER;");
        progressColumn.setCellValueFactory(new PropertyValueFactory<Data, ProgressBar>("bar"));
        progressColumn.setStyle( "-fx-alignment : CENTER;");
        statusColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("status"));
        statusColumn.setStyle("-fx-alignment: CENTER;");
        controlColumn.setCellValueFactory(new PropertyValueFactory<Data, ButtonBar>("btnbar"));
        controlColumn.setStyle( "-fx-alignment: CENTER;");
        downloadTable.setItems(main.downloadList);
    }

}


