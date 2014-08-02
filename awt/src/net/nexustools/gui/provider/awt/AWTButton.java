/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Button;
import net.nexustools.gui.wrap.NativeButton;
import net.nexustools.gui.wrap.WrappedButton;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class AWTButton extends AWTActivateableWidget<Button> implements NativeButton {

    public static Creator<NativeButton, WrappedButton> CREATOR = new Creator<NativeButton, WrappedButton>() {
        public NativeButton create(WrappedButton button) {
            return new AWTButton(button);
        }
    };
    
    public AWTButton(WrappedButton wrap) {
        super(new Button(), wrap);
    }

    public void nativeSetText(String text) {
        _c().setLabel(text);
    }

    public void nativeAddActivateListener() {
        _c().addActionListener(actionListener);
    }

    public void nativeRemoveActivateListener() {
        _c().removeActionListener(actionListener);
    }
    
}
