/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import net.nexustools.gui.geom.Vec4f;
import net.nexustools.gui.provider.awt.AWTAbstractContainer.NAAbstractContainer;
import net.nexustools.gui.provider.awt.AWTWidget.NAComponent;
import net.nexustools.gui.wrap.WAbstractContainer;
import net.nexustools.gui.wrap.impl.NAbstractContainer;
import net.nexustools.gui.wrap.impl.NWidget;

/**
 *
 * @author katelyn
 */
public abstract class AWTAbstractContainer<N extends NAAbstractContainer> extends WAbstractContainer<N> {
    
    public static class NAAbstractContainer<C extends Container, WW extends WAbstractContainer> extends NAComponent<C, WW> implements NAbstractContainer<WW> {

        public NAAbstractContainer(C component) {
            super(component);
        }

        public void nativeAdd(NWidget child) {
            component.add(((NAComponent)child).component);
        }

        public void nativeInsert(NWidget child, int at) {
            component.add(((NAComponent)child).component, at);
        }

        public void nativeSwap(NWidget child, NWidget other) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public void nativeRemove(NWidget child) {
            component.remove(((NAComponent)child).component);
        }

        public Vec4f nativeInsets() {
            Insets insets = component.getInsets();
            return new Vec4f(insets.left, insets.top, insets.right, insets.bottom);
        }
        
    }

    public AWTAbstractContainer(String tag, AWTPlatform platform) {
        super(tag, platform);
    }
    
}
