/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.AbstractButton;
import net.nexustools.gui.provider.awt.AWTActivateableWidget;
import net.nexustools.gui.provider.awt.AWTWidget;
import net.nexustools.gui.wrap.NativeLabel;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public class SwingAbstractButton<B extends AbstractButton> extends AWTActivateableWidget<B> implements NativeLabel {
    
    protected SwingAbstractButton(B abstractButton, WrappedWidget widget) {
        super(abstractButton, widget);
    }
    
    public void nativeSetText(String text) {
        _c().setText(text);
    }

    public void nativeAddActivateListener() {
        _c().addActionListener(actionListener);
    }

    public void nativeRemoveActivateListener() {
        _c().removeActionListener(actionListener);
    }
    
}
