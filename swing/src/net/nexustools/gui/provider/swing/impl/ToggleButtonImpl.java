/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.impl;

import javax.swing.JToggleButton;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ToggleButtonImpl<B extends JToggleButton> extends ButtonImpl<B> {

    public ToggleButtonImpl(SwingPlatform platform) {
        super(platform);
    }

    @Override
    public boolean selectable() {
        return true;
    }

    @Override
    public boolean isSelected() {
        return read(new Reader<Boolean>() {
            @Override
            public Boolean read() {
                return component.isSelected();
            }
        });
    }
    
    @Override
    public void setSelected(final boolean selected) {
        act(new Runnable() {

            @Override
            public void run() {
                component.setSelected(selected);
            }
        });
    }
    
}