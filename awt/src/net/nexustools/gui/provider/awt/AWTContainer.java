/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Container;
import net.nexustools.gui.wrap.NativeContainer;
import net.nexustools.gui.wrap.NativeWidget;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public class AWTContainer<C extends Container> extends AWTWidget<C> implements NativeContainer {

    protected AWTContainer(C container, WrappedWidget wrap) {
        super(container, wrap);
    }
    public AWTContainer(WrappedWidget wrap) {
        this((C)new Container(), wrap);
    }

    public void nativeAdd(NativeWidget nativeWidget) {
        _c().add(((AWTWidget)nativeWidget)._c());
    }

    public void nativeRemove(NativeWidget nativeWidget) {
        _c().remove(((AWTWidget)nativeWidget)._c());
    }

    public void nativeReplace(NativeWidget what, NativeWidget with) {
        nativeRemove(what);
        nativeAdd(with);
    }
    
}
