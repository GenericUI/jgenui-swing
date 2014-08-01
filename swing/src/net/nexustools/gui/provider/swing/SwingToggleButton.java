/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JToggleButton;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.ActivateableAction;
import net.nexustools.gui.Button;
import net.nexustools.gui.provider.swing.impl.ToggleButtonImpl;

/**
 *
 * @author katelyn
 */
public class SwingToggleButton extends ToggleButtonImpl<JToggleButton> implements Button {
    
    private class NativeButton extends JToggleButton {
        
        public NativeButton() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
    }

    public SwingToggleButton() {
        this(SwingPlatform.instance());
    }
    public SwingToggleButton(String text) {
        this();
        setText(text);
    }
    SwingToggleButton(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JToggleButton create() {
        return new NativeButton();
    }
    
    public AbstractAction action() {
        return new ActivateableAction(this) {
            @Override
            public String text() {
                return SwingToggleButton.this.text(); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
}
