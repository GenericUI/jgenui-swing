/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.SizeConstraints;
import net.nexustools.gui.wrap.NativeWidget;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public class AWTWidget<C extends Component> implements NativeWidget {

    protected final C component;
    protected final WrappedWidget wrap;
    protected AWTWidget(C component, WrappedWidget wrap) {
        this.component = component;
        this.wrap = wrap;
    }
    
    public void nativeSetTag(String tag) {
        component.setName(tag);
    }

    public void nativeSetVisible(boolean visible) {
        component.setVisible(visible);
    }

    public void nativeMove(Point pos) {
        component.setLocation(new java.awt.Point((int)pos.x, (int)pos.y));
    }

    public void nativeResize(Size size) {
        component.setSize(new Dimension((int)size.w, (int)size.h));
    }

    public void nativeReshape(Shape shape) {
        component.setBounds(new Rectangle());
    }

    public SizeConstraints nativeConstraints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public WrappedWidget _w() {
        return wrap;
    }
    
    public C _c() {
        return component;
    }
    
}
