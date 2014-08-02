/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Label;
import net.nexustools.gui.wrap.NativeLabel;
import net.nexustools.gui.wrap.WrappedLabel;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class AWTLabel extends AWTWidget<Label> implements NativeLabel {

    public static Creator<NativeLabel, WrappedLabel> CREATOR = new Creator<NativeLabel, WrappedLabel>() {
        public NativeLabel create(WrappedLabel label) {
            return new AWTLabel(label);
        }
    };

    public AWTLabel(WrappedLabel wrap) {
        super(new Label(), wrap);
    }

    public void nativeSetText(String text) {
        component.setText(text);
    }
    
}
