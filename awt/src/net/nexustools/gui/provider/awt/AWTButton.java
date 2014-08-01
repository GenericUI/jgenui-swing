/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.ActivateableAction;
import net.nexustools.gui.Button;
import net.nexustools.gui.Shortcut;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;

/**
 *
 * @author katelyn
 */
public class AWTButton extends AWTWidgetImpl<java.awt.Button> implements Button {
    
    private class NativeButton extends java.awt.Button {
        
        public NativeButton() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Button");
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender(g))
                super.paint(g);
        }
        
    }

    public AWTButton() {
        this(AWTPlatform.instance());
    }
    public AWTButton(String text) {
        this();
        setText(text);
    }
    AWTButton(Button other, AWTPlatform platform) {
        super(platform);
        inherit(other);
    }
    AWTButton(AWTPlatform platform) {
        super(platform);
    }

    @Override
    protected java.awt.Button create() {
        return new NativeButton();
    }

    public AbstractAction action() {
        return new ActivateableAction(this) {
            @Override
            public String text() {
                return AWTButton.this.text();
            }
        };
    }

    public String text() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getLabel();
            }
        });
    }

    public void setText(final String text) {
        act(new Runnable() {
            public void run() {
                component.setLabel(text);
            }
        });
    }

    public Shortcut shortcut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setShortcut(Shortcut shortcut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addActionListener(ActionListener actionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeActionListener(ActionListener actionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean selectable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelected(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void activate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
