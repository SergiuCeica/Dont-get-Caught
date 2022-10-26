import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class GameWindow
{
    private JFrame  wndFrame;
    private String  wndTitle;
    private int     wndWidth;
    private int     wndHeight;
    KeyHandler keyH=new KeyHandler();

    public static Canvas  canvas;

    public GameWindow(String title, int width, int height){
        wndTitle    = title;
        wndWidth    = width;
        wndHeight   = height;
        wndFrame    = null;
    }
    public void BuildGameWindow()
    {
        if(wndFrame != null)
        {
            return;
        }
        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(false);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        canvas.setMaximumSize(new Dimension(wndWidth, wndHeight));
        canvas.setMinimumSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        wndFrame.pack();
        wndFrame.addKeyListener(keyH);
        wndFrame.setFocusable(true);

    }

    public int GetWndWidth()
    {
        return wndWidth;
    }
    public int GetWndHeight()
    {
        return wndHeight;
    }
    public Canvas GetCanvas() {
        return canvas;
    }


}
