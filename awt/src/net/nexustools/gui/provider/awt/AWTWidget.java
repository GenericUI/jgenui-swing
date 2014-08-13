/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.impl.Menu;
import net.nexustools.gui.provider.awt.AWTWidget.NAComponent;
import net.nexustools.gui.wrap.WWidget;
import net.nexustools.gui.wrap.impl.NWidget;

/**
 *
 * @author katelyn
 */
public abstract class AWTWidget<N extends NAComponent> extends WWidget<N> {

    public static class NAComponent<C extends Component, WW extends WWidget> implements NWidget<WW> {

        protected final C component;
        protected NAComponent(C component) {
            this.component = component;
        }
        
        public void nativeSetTag(String tag) {
            component.setName(tag);
        }

        public void nativeSetVisible(boolean visible) {
            component.setVisible(visible);
        }

        public void nativeSetFocusable(boolean focusable) {
            component.setFocusable(focusable);
        }

        public void nativeMove(int x, int y) {
            Point location = component.getLocation();
            if(location.x == x && location.y == y)
                return;
            
            component.setLocation(x, y);
        }

        public void nativeResize(int w, int h) {
            Dimension dimension = component.getSize();
            if(dimension.width == w && dimension.height == h)
                return;
            
            component.setSize(w, h);
        }

        public void nativeRebound(int x, int y, int w, int h) {
            Rectangle rect = component.getBounds();
            if(rect.x == x && rect.y == y && rect.width == w && rect.height == h)
                return;
            
            component.setBounds(x, y, w, h);
        }

        public Size nativeMinSize() {
            Dimension dim = component.getMinimumSize();
            return new Size(dim.width, dim.height);
        }

        public Size nativeMaxSize() {
            Dimension dim = component.getMaximumSize();
            return new Size(dim.width, dim.height);
        }

        public Size nativePrefSize() {
            Dimension dim = component.getPreferredSize();
            return new Size(dim.width, dim.height);
        }

        public void nativeSetEnabled(boolean enabled) {
            component.setEnabled(enabled);
        }

        public void nativeBindContextMenu(Menu contextMenu) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public void nativeRequestFocus() {
            component.requestFocus();
        }
        
        public C _c() {
            return component;
        }
        
    }
    
    protected AWTWidget(String tag, AWTPlatform platform) {
        super(tag, platform);
    }
    
}
