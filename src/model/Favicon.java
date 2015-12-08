package model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tareq on 12/3/2015.
 */
public class Favicon {

    public Favicon(String addr)
    {

    }
    public static ImageView getFavicon(String addr)
    {
        ImageView imv = new ImageView();
        try {
            URL url = new URL(addr);
            URL imgurl = new URL("http://www.google.com/s2/favicons?domain=" + url.getHost());
            BufferedImage bi = ImageIO.read(imgurl);
            WritableImage wi = new WritableImage(bi.getWidth(), bi.getHeight());
            Image image = SwingFXUtils.toFXImage(bi, wi);
            imv.setImage(image);
            return imv;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ImageView("/images/icon.png");
        } catch (IOException e) {
            e.printStackTrace();
            return new ImageView("/images/icon.png");
        }
    }
}
