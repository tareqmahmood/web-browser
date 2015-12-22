package model;

import main.Main;

import java.io.*;

/**
 * Created by tareq on 12/11/2015.
 */
public class BookmarkManager implements Runnable{
    String filename = "/files/bookmark.txt";
    public final File file = new File("res/files/bookmark.txt");
    int length;
    FileInputStream fis;
    BufferedReader br;
    FileOutputStream fos;
    BufferedWriter bw;
    Thread thr;
    Link link;
    String url;
    String title;

    public BookmarkManager()
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
                title = br.readLine();
                url = br.readLine();
                Main.bookmarkList.add(new Link(title, url));
                Main.bookmarkedURL.add(url);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBookmark()
    {
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            length = Main.bookmarkList.size();
            System.out.println(length);
            bw.write(length + "\n");
            for(int i = 0; i < length; i++)
            {
                link = Main.bookmarkList.get(i);
                bw.write(link.getTitle() + "\n");
                bw.write(link.getUrl() + "\n");
            }
            Main.bookmarkList.clear();
            Main.bookmarkedURL.clear();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
