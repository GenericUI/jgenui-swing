/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Container;
import net.nexustools.gui.wrap.NativeWidget;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public abstract class AWTAbstractContainer<C extends Component> extends AWTWidget<C> {

    protected AWTAbstractContainer(C component, WrappedWidget wrap) {
        super(component, wrap);
    }
    
    public abstract Container _t();

    public void nativeAdd(NativeWidget nativeWidget) {
        _t().add(((AWTWidget)nativeWidget)._c());
    }

    public void nativeRemove(NativeWidget nativeWidget) {
        _t().remove(((AWTWidget)nativeWidget)._c());
    }

    public void nativeReplace(NativeWidget what, NativeWidget with) {
        nativeRemove(what);
        nativeAdd(with);
    }
}
