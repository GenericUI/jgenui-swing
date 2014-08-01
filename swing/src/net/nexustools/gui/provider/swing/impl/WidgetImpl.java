/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.impl;

import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class WidgetImpl<C extends java.awt.Component> extends AWTWidgetImpl<C> {

    public WidgetImpl(SwingPlatform platform) {
        super(platform);
    }
    
}
