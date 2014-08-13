/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Button;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nexustools.gui.impl.Image;
import net.nexustools.gui.provider.awt.AWTButton.NAButton;
import net.nexustools.gui.provider.awt.AWTLabel.NALabel;
import net.nexustools.gui.wrap.WButton;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NButton;

/**
 *
 * @author katelyn
 */
public class AWTButton<N extends NAButton> extends WButton<N> {

    protected AWTButton(String tag, WPlatform platform) {
        super(tag, platform);
    }
    protected AWTButton(WPlatform platform) {
        this("Button", platform);
    }
    public AWTButton() {
        this(AWTPlatform.instance());
    }
    
    public static abstract class NAButton<C extends Component, WW extends WButton> extends NALabel<C, WW> implements NButton<WW> {
        public NAButton(C component) {
            super(component);
        }
    }

    @Override
    protected N createNative() {
        return (N) new NAButton<Button, WButton>(new Button()) {
            ActionListener actionListener;
            public void nativeSetText(String text) {
                component.setLabel(text);
            }
            public void nativeSetIcon(Image icon) {}

            public void attachActionListener(final Runnable callback) {
                if(actionListener != null)
                    return;
                
                component.addActionListener(actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        callback.run();
                    }
                });
            }

            public void detachActionListener() {
                if(actionListener == null)
                    return;
                
                component.removeActionListener(actionListener);
                actionListener = null;
            }
        };
    }
    
}
