/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import javax.swing.JTextArea;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.Shortcut;
import net.nexustools.gui.SingleInput;
import net.nexustools.gui.TextArea;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.event.ValueListener;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.provider.swing.impl.ScrollDeligateImpl;

/**
 *
 * @author katelyn
 */
public class SwingTextArea extends ScrollDeligateImpl<JTextArea> implements TextArea {

    public SwingTextArea() {
        super(AWTPlatform.instance());
        setContextMenu(buildEditMenu(this));
        setMinimumSize(new Size(20, 20));
    }
    
    public SwingTextArea(String text) {
        this();
        setValue(text);
    }

    public void addValueListener(ValueListener<String, SingleInput<String>> valueListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeValueListener(ValueListener<String, SingleInput<String>> valueListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getSelectionEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getSelectionStart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelection(long start, long end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectionStart(long start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectionEnd(long end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    protected JTextArea createView() {
        return new JTextArea();
    }

    @Override
    public String template() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTemplate(String template) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(final String value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.view.setText(value);
            }
        });
    }

    @Override
    public String value() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.view.getText();
            }
        });
    }

    public int selectionEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int selectionStart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectionRange(int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectionStart(int start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectionEnd(int end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AbstractAction cutAction() {
        return new AbstractAction() {
            @Override
            public String text() {
                return "Cut";
            }
            public boolean selectable() {
                return false;
            }
            public boolean isSelected() {
                return false;
            }
            @Override
            public void activate() {
                super.activate();
                component.view.cut();
            }
        };
    }

    public AbstractAction copyAction() {
        return new AbstractAction() {
            @Override
            public String text() {
                return "Copy";
            }
            public boolean selectable() {
                return false;
            }
            public boolean isSelected() {
                return false;
            }
            @Override
            public void activate() {
                super.activate();
                component.view.copy();
            }
        };
    }

    public AbstractAction pasteAction() {
        return new AbstractAction() {
            @Override
            public String text() {
                return "Paste";
            }
            public boolean selectable() {
                return false;
            }
            public boolean isSelected() {
                return false;
            }
            @Override
            public void activate() {
                super.activate();
                component.view.paste();
            }
        };
    }
    
}
