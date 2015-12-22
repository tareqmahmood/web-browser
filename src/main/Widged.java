package main;

import com.sun.awt.AWTUtilities;
import javafx.collections.ObservableArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Widged extends JFrame{

    int posX = 0, posY = 0;
    JButton closeBtn;
    Widged widged;
    public static ArrayList<ExpandSwing> window = new ArrayList<>();
    public Widged()
    {
        widged = this;
        //SREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        // CLOSE BUTTON
        closeBtn = new JButton();
        closeBtn.addActionListener(e -> System.exit(0));
        //closeBtn.setVisible(true);
        closeBtn.setBounds(70, 54, 18,18);
/*        closeBtn.setBorder(new RoundedBorder(18));
        closeBtn.setForeground(Color.BLUE);
        closeBtn.setBackground(Color.RED);*/

        setUndecorated(true);
        setAlwaysOnTop(true);
        setOpacity(.35f);
        setSize(200, 200); // https://www.youtube.com/watch?v=IFIlr6cpX64

        try {
            closeBtn.setIcon(new ImageIcon(ImageIO.read(new File("res/images/cross.png"))));
            BufferedImage img = ImageIO.read(new File("res/images/cute bird.png"));
            BufferedImage img2 = ImageIO.read(new File("res/images/cute bird6.png"));
            ImageIcon icon = new ImageIcon(img);
            ImageIcon icon2 = new ImageIcon(img2);
            JLabel label = new JLabel(icon);

            //setContentPane(new JLabel(icon));

            //====================
            // Mouse hover Option
            //====================

            addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                    System.out.println("Pressed"+ keyEvent);
                }
                /*public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar() == 'm') System.out.println("I am groot!");
                }*/

                @Override
                public void keyTyped(KeyEvent e) {
                    if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                        System.out.println("woot!");
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    setOpacity(1.0f);
                    //Add a Close button here
                }
                @Override
                public void mouseExited(MouseEvent evt) {
                    setOpacity(.3f);
                }
                @Override
                public void mousePressed(MouseEvent e)
                {
                    posX=e.getX();
                    posY=e.getY();
                    label.setIcon(icon2);
                    System.out.println("mouse pressed");
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setIcon(icon);
                }

                @Override
                public void mouseClicked(MouseEvent e){
                    if(e.getClickCount()==2){
                        // your code here
                        int scrX = (int)getLocationOnScreen().getX();//e.getXOnScreen();
                        int scrY = (int)getLocationOnScreen().getY();//e.getYOnScreen();
                        boolean left = scrX> width - 586;

                        System.out.println("The mouse is clicked.");
                        ExpandSwing expandSwing = new ExpandSwing(left, widged);
                        window.add(expandSwing);
                        expandSwing.setLocation(
                            left ? scrX - 160: scrX+160, // ? here
                            scrY+65
                        );
                    }
                }
            });


            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent evt) {
                    setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
                }
            });

            //=====================
            //label.add(closeBtn);
            setContentPane(label);
            //
            //add(label);
            add(closeBtn);
            setIconImage(img);

        }catch (IOException e){
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //addKeyListener(keyListener)

        Shape shape = new Ellipse2D.Float(46, 47, 109, 106);
        AWTUtilities.setWindowShape(this, shape);
    }
    public void cleanWindow()
    {
        int len = window.size();
        for(int i = 0; i < len; i++)
        {
            window.get(i).setVisible(false);
        }
    }
    public static void main(String[] args) {
        new Widged();
    }
}
