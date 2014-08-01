/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nexustools.gui.provider.awt.AWTPlatform;
import javax.swing.JTabbedPane;
import net.nexustools.gui.TabWidget;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.platform.PlatformException;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.utils.Pair;

/**
 *
 * @author katelyn
 */
public class SwingTabWidget extends AWTWidgetImpl<javax.swing.JTabbedPane> implements TabWidget {

    public class Native extends JTabbedPane {
        public Native() {
            setName("TabWidget");
        }
    }
    
    public static enum Orientation {
        Top,
        Left,
        Right,
        Bottom
    }
    
    public SwingTabWidget() {
        super(AWTPlatform.instance());
    }
    SwingTabWidget(AWTPlatform platform) {
        super(platform);
    }

    @Override
    protected JTabbedPane create() {
        return new Native();
    }

    @Override
    public void add(final Widget widget, final String name) {
        act(new Runnable() {
            @Override
            public void run() {
                component.add(name, _n(widget));
            }
        });
    }

    @Override
    public void remove(final Widget widget) {
        act(new Runnable() {
            @Override
            public void run() {
                component.remove(_n(widget));
            }
        });
    }

    @Override
    public void remove(String tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setOrientation(Orientation orientation) {
        switch(orientation) {
            case Top:
                component.setTabPlacement(JTabbedPane.TOP);
                break;
            case Left:
                component.setTabPlacement(JTabbedPane.LEFT);
                break;
            case Right:
                component.setTabPlacement(JTabbedPane.RIGHT);
                break;
            case Bottom:
                component.setTabPlacement(JTabbedPane.BOTTOM);
                break;
        }
    }

    public void addUnique(final Widget content, final String title) {
        final java.awt.Component cContent = platform().nativeFor(content);
        act(new Runnable() {
            public void run() {
                
            }
        });
    }

    public boolean addUnique(Widget content, String title, boolean replace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateTitle(Widget content, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iterate(ContentIterator<Pair<Widget, String>> it) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect contentBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Point contentOffset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Size contentSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterator<Pair<Widget, String>> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
