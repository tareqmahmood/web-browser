package model;

import main.Main;

import java.io.*;

/**
 * Created by tareq on 12/4/2015.
 */
public class HistoryManager implements Runnable
{
    String filename = "/files/history.txt";
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;
    Thread thr;
    WebPage page;
    public HistoryManager()
    {
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            while(true)
            {
                page = (WebPage) ois.readObject();
                if(page == null) break;
                Main.webPageList.add(page);
            }
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e)
        {
            try {
                ois.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void saveHistory()
    {
        int len = Main.webPageList.size();
        //Main.webPageList.
        for(int i = 0; i < len; i++)
        {
            Main.webPageList.iterator();
        }
    }
}
