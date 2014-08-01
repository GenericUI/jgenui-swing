/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.impl;

import java.awt.Component;
import javax.swing.JScrollPane;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.impl.AbstractContainerImpl;
import net.nexustools.gui.provider.swing.impl.ScrollDeligateImpl.Native;

/**
 *
 * @author katelyn
 */
public abstract class ScrollDeligateImpl<V extends java.awt.Component> extends AbstractContainerImpl<Native<V>> {

    public static class Native<V extends java.awt.Component> extends JScrollPane {
        public final V view;
        protected Native(V view) {
            setName(view.getName());
            view.setName("View");
            
            setViewportView(view);
            this.view = view;
        }

        @Override
        public void setEnabled(boolean enabled) {
            view.setEnabled(enabled);
        }

        @Override
        public boolean isEnabled() {
            return view == null ? true : view.isEnabled();
        } 
    }
    
    public ScrollDeligateImpl(AWTPlatform platform) {
        super(platform);
    }

    protected abstract V createView();

    @Override
    protected Native<V> create() {
        return new Native<V>(createView());
    }

    @Override
    protected Component menuTarget() {
        return component.view;
    }
    
    
}
