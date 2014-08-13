/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import net.nexustools.gui.impl.Image;
import net.nexustools.gui.provider.awt.AWTButton.NAButton;
import net.nexustools.gui.provider.swing.SwingButton.NSButton;
import net.nexustools.gui.wrap.WButton;
import net.nexustools.gui.wrap.WPlatform;

/**
 *
 * @author katelyn
 */
public class SwingButton<N extends NSButton> extends WButton<N> {
    
    public static class NSButton<C extends AbstractButton, WW extends WButton> extends NAButton<C, WW> {
        ActionListener actionListener;
        public NSButton(C component) {
            super(component);
        }

        public void nativeSetText(String text) {
            component.setText(text);
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
        }
    }

    public SwingButton(String tag, SwingPlatform platform) {
        super(tag, platform);
    }
    public SwingButton(SwingPlatform platform) {
        this("Button", platform);
    }
    public SwingButton() {
        this(SwingPlatform.instance());
    }

    @Override
    protected N createNative() {
        return (N) new NSButton(new JButton());
    }
    
}
