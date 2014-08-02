/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nexustools.gui.Activateable;
import net.nexustools.gui.wrap.WrappedWidget;

/**
 *
 * @author katelyn
 */
public class AWTActivateableWidget<C extends Component> extends AWTWidget<C> {

    protected final ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ((Activateable)_w()).activate();
        }
    };
    protected AWTActivateableWidget(C component, WrappedWidget wrap) {
        super(component, wrap);
    }
    
}
