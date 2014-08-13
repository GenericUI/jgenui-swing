/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import net.nexustools.gui.provider.awt.AWTContainer.NAContainer;
import net.nexustools.gui.wrap.WWindow;
import net.nexustools.gui.wrap.impl.NBody;
import net.nexustools.gui.wrap.impl.NWindow;

/**
 *
 * @author katelyn
 */
public class AWTWindow {
    
    public static abstract class NAWindow<C extends Window, WW extends WWindow> extends NAContainer<C, WW> implements NWindow<WW> {

        protected NAWindow(final C component) {
            super(component);
            component.addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent e) {}
                public void windowClosing(WindowEvent e) {
                    component.dispose();
                }
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
            });
        }

        public void nativeSetRaisedBorder(boolean raisedBorder) {
        }
        
    }
}
