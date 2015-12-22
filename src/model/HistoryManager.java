package model;

import javafx.collections.ObservableList;
import main.Main;

import java.io.*;
import java.util.Date;

/**
 * Created by tareq on 12/4/2015.
 */
public class HistoryManager implements Runnable
{
    String filename = "/files/history.txt";
    public final File file = new File("res/files/history.txt");
    int length;
    FileInputStream fis;
    BufferedReader br;
    FileOutputStream fos;
    BufferedWriter bw;
    Thread thr;
    WebPage page;
    String url;
    String title;
    String time;
    Date date;
    public HistoryManager()
    {
        System.out.println("opening " + file.getName());
        try {
            fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {
        try {

            length = Integer.valueOf(br.readLine());
            for(int i = 0; i < length; i++)
            {
                time = br.readLine();
                title = br.readLine();
                url = br.readLine();
                Main.webPageList.add(new WebPage(url, title, time));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveHistory()
    {
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            length = Main.webPageList.size();
            System.out.println(length);
            bw.write(length + "\n");
            for(int i = 0; i < length; i++)
            {
                page = Main.webPageList.get(i);
                bw.write(page.getTime() + "\n");
                bw.write(page.getTitle() + "\n");
                bw.write(page.getUrl() + "\n");
            }
            bw.close();
            Main.webPageList.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
