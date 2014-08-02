/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Container;
import net.nexustools.gui.wrap.NativeContainer;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public class AWTContainer<C extends Container> extends AWTAbstractContainer<C> implements NativeContainer {

    protected AWTContainer(C container, WrappedWidget wrap) {
        super(container, wrap);
    }
    public AWTContainer(WrappedWidget wrap) {
        this((C)new Container(), wrap);
    }

    @Override
    public final C _t() {
        return _c();
    }
    
}
