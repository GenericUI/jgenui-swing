/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JButton;
import net.nexustools.gui.wrap.NativeButton;
import net.nexustools.gui.wrap.WrappedButton;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingButton extends SwingAbstractButton implements NativeButton {

    public static Creator<NativeButton, WrappedButton> CREATOR = new Creator<NativeButton, WrappedButton>() {
        public NativeButton create(WrappedButton button) {
            return new SwingButton(button);
        }
    };
    
    public SwingButton(WrappedButton button) {
        super(new JButton(), button);
    }
    
}
