package dac28.view;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Defines the scroll bar the application will be using.
 * Even by overriding the methods and doing nothing, the scroll bars will
 * be less 'clunky' looking.
 * 
 * @author Dan Cornwell
 *
 */
class CustomScrollBar extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    }
}
