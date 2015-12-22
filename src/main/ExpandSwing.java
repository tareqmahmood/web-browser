package main;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;


public class ExpandSwing extends JFrame {

    JButton close,min,max;
    Font f=new Font("Arial",Font.PLAIN,14);
    JToggleButton top, opac;
    JPanel p;
    java.util.Timer animTimer = new java.util.Timer();
    private JPanel controlPanel;
    JMenuBar mb;
    int pX,pY;
    int windowMaxLength=200;
    Widged widged;
    String searchURL = "http://www.google.com/search?q=";
    JFXPanel jfxPanel = null;
    public WebView webView = null;
    Scene scene = null;

    public ExpandSwing(boolean left, Widged wid) {
        widged = wid;
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){}

        //%%%%%%%%%%%%%%%%%%%%%%%
        setUndecorated(true); //Borderless: uncomment when program finishes
        //%%%%%%%%%%%%%%%%%%%%%%%

        //BUTTON        //http://www.tutorialspoint.com/swing/swing_jbutton.htm
        JButton button = new JButton("Go!");
        top = new JToggleButton("T");
        min=new JButton("-");
        max=new JButton("+");
        close=new JButton("x");
        opac = new JToggleButton("O");
        min.setFont(f);
        max.setFont(f);
        close.setFont(f);
        top.setFont(f);
        opac.setFont(f);
        button.setFont(f);
        min.addActionListener(e -> setState(ICONIFIED));
        max.addActionListener(e -> maximize());
        close.addActionListener(e -> {
            Widged.window.remove(this);
            dispose();
        });
        top.addActionListener(
                e -> {
                    JToggleButton but = (JToggleButton)e.getSource();
                    if (but.isSelected()){
                        setAlwaysOnTop(true);
                    }
                    else{
                        setAlwaysOnTop(false);
                    }
                }
        );
        opac.addActionListener(
                e -> {
                    JToggleButton but = (JToggleButton)e.getSource();
                    if (but.isSelected()){
                        setAlwaysOnTop(true);
                        setOpacity(.35f);
                    }
                    else{
                        setAlwaysOnTop(false);
                        setOpacity(1.0f);
                    }
                }
        );


        //TEXTFIELD
        JTextField field = new PTextField("Enter url or search...");
        field.setFont(new Font("Georgia", Font.PLAIN, 12));
        /*field.addFocusListener(new FocusListener() { // Not perfect, but we can use it in case there is problem with searching
          public void focusGained(FocusEvent e) {
                field.setText("");
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });*/

        // creating menu bar
        mb=new JMenuBar();
        mb.setLayout(new BorderLayout());
        mb.setFont(f);

        // Title Bar
        p=new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(1, 3));
        p.add(close);
        p.add(max);
        p.add(min);
        p.add(top);
        p.add(opac);



        // PANEL
        controlPanel = new JPanel(new GridBagLayout());//http://stackoverflow.com/questions/
        // 5555355/creating-a-text-field-that-dynamically-resizes-its-width
        GridBagConstraints c = new GridBagConstraints();
        c.weightx=1.0;
        c.fill=GridBagConstraints.BOTH;
        controlPanel.add(field,c);
        controlPanel.add(button);
        controlPanel.setBounds(0, 100, 500,500);

        //Menue Bar
        mb.add(p,BorderLayout.WEST);
        mb.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me)
            {
                pX=me.getX();
                pY=me.getY();
            }
        });

        mb.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });


        //JFRAME
        setJMenuBar(mb);
        setSize(200, 67);
        setVisible(true);
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        //setLocationRelativeTo(null); // Centering opening window

        //SREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setSize(100, 200);
        add(jfxPanel);
        Platform.runLater(() -> {
            webView = new WebView();
            webView.setZoom(0.8);
            jfxPanel.setScene(new Scene(webView));
        });

        //WINDOW POSITIONING ON SCREEN
        //getLocationOnScreen();
        //setLocation(500, 0);

        // Expand first
        TimerTask ttaskWidth = new TimerTask() {

            int i = 0;

            @Override
            public void run() {
                if (i < 90) {
                    setShape(new java.awt.geom.RoundRectangle2D.Double(0,0,getWidth(),getHeight(),5,5));
                    setSize(getWidth() + 3, getHeight());
                    if(left) setLocation((int)getLocationOnScreen().getX()-3, (int)getLocationOnScreen().getY());
                } else {
                    this.cancel();
                }
                i++;
            }
        };


        animTimer.scheduleAtFixedRate(ttaskWidth, 500, 10);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getHeight() < windowMaxLength) {
                    TimerTask ttaskWidth = new TimerTask() {

                        int i = 0;

                        @Override
                        public void run() {
                            if (i < 150) {
                                setSize(getWidth(), getHeight() + 3);
                                setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
                                if ((int) getLocationOnScreen().getY() + getHeight() > height - 31)
                                    setLocation((int) getLocationOnScreen().getX(), (int) getLocationOnScreen().getY() - 3);
                            } else {
                                this.cancel();
                            }
                            i++;
                        }
                    };
                    animTimer.scheduleAtFixedRate(ttaskWidth, 500, 10);
                    windowMaxLength = getHeight();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String search = field.getText();
                        String url = searchURL;
                        search.trim().toLowerCase();
                        search = search.replace(' ', '+');
                        url = url + search;
                        System.out.println(url);
                        webView.getEngine().load(url);
                    }
                });
            }
        });
    }

    //BUTTON FUNCTION
    private void maximize()
    {
        //This is for maximizing window, add method for opening the main browser here
        JFrame frm = new JFrame("Hello");
        frm.setSize(600, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setLocation(dim.width / 2 - frm.getSize().width / 2, dim.height / 2 - frm.getSize().height / 2);
        widged.cleanWindow();
        //setVisible(false);
        widged.setVisible(false);
        Main.main(null);
    }

   /* public static void main(String[] args) {
        new ExpandSwing();
    }*/
}

class PTextField extends JTextField {

    public PTextField(final String proptText) {
        super(proptText);
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty()) {
                    setText(proptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(proptText)) {
                    setText("");
                }
            }
        });

    }

}