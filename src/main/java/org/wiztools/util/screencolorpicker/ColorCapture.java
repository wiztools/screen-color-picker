package org.wiztools.util.screencolorpicker;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 *
 * @author subwiz
 */
class ColorCapture implements Runnable {

    public void run() {
        try {
            // Find the screen dimension:
            final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

            // Screen capture:
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(new Rectangle(d));

            // Create the window with the captured image:
            JWindow jw = new JWindow();
            Container c = jw.getContentPane();
            c.setLayout(new BorderLayout());
            JLabel jl = new JLabel(new ImageIcon(img));
            jl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Robot robot = new Robot();
                        Color c = robot.getPixelColor(e.getX(), e.getY());
                        final int c_red = c.getRed();
                        final int c_green = c.getGreen();
                        final int c_blue = c.getBlue();
                        System.out.println(c_red + ", " + c_green + ", " + c_blue);
                        System.out.println("#" + Integer.toHexString(c_red) + Integer.toHexString(c_green) + Integer.toHexString(c_blue));
                        System.exit(0);
                    } catch (AWTException ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                }
            });
            c.add(jl, BorderLayout.CENTER);
            Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
            c.setCursor(cursor);
            jw.setSize(d);
            jw.setVisible(true);
        } catch (AWTException ex) {
            ex.printStackTrace();
            System.exit(2);
        }
    }
}
