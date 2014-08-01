/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.impl;

import javax.swing.AbstractButton;
import net.nexustools.gui.Activateable;
import net.nexustools.gui.Shortcut;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.event.EventDispatcher;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ButtonImpl<B extends AbstractButton> extends WidgetImpl<B> {

    public ButtonImpl(SwingPlatform platform) {
        super(platform);
    }
    
    public void setText(final String text) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setText(text);
            }
        });
    }

    public String text() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getText();
            }
        });
    }
    
    public final EventDispatcher<SwingPlatform, ActionListener, ActionListener.ActionEvent> actionDispatcher = new EventDispatcher(platform()) {

        private java.awt.event.ActionListener actionListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                activate0();
            }
        };
        @Override
        public void connect() {
            component.addActionListener(actionListener);
        }

        @Override
        public void disconnect() {
            component.removeActionListener(actionListener);
        }
    };

    public Shortcut shortcut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setShortcut(Shortcut shortcut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addActionListener(ActionListener actionListener) {
        actionDispatcher.add(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        actionDispatcher.remove(actionListener);
    }

    protected void activate0() {
        actionDispatcher.dispatch(new EventDispatcher.Processor<ActionListener, ActionListener.ActionEvent>() {
            @Override
            public ActionListener.ActionEvent create() {
                return new ActionListener.ActionEvent((Activateable)ButtonImpl.this);
            }
            @Override
            public void dispatch(ActionListener listener, ActionListener.ActionEvent event) {
                listener.activated(event);
            }
        });
    }
    
    public void activate() {
        act(new Runnable() {
            @Override
            public void run() {
                activate0();
            }
        });
    }

    public boolean selectable() {
        return false;
    }

    public boolean isSelected() {
        return false;
    }
    
    public void setSelected(boolean selected) {
        throw new UnsupportedOperationException();
    }
    
}