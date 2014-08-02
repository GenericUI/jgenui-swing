/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JPanel;
import net.nexustools.gui.provider.awt.AWTContainer;
import net.nexustools.gui.wrap.NativeContainer;
import net.nexustools.gui.wrap.WrappedContainer;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingContainer extends AWTContainer<JPanel> {
    
    public static Creator<NativeContainer, WrappedContainer> CREATOR = new Creator<NativeContainer, WrappedContainer>() {
        public NativeContainer create(WrappedContainer container) {
            return new SwingContainer(container);
        }
    };
    
    public SwingContainer(WrappedContainer wrap) {
        super(wrap);
    }
    
}
