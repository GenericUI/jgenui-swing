/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JLabel;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.SizeConstraints;
import net.nexustools.gui.provider.awt.AWTWidget;
import net.nexustools.gui.wrap.NativeLabel;
import net.nexustools.gui.wrap.WrappedLabel;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingLabel extends AWTWidget<JLabel> implements NativeLabel {

    public static Creator<NativeLabel, WrappedLabel> CREATOR = new Creator<NativeLabel, WrappedLabel>() {
        public NativeLabel create(WrappedLabel label) {
            return new SwingLabel(label);
        }
    };
    
    protected SwingLabel(WrappedLabel label) {
        super(new JLabel(), label);
    }
    
    public void nativeSetText(String text) {
        _c().setText(text);
    }
    
}
