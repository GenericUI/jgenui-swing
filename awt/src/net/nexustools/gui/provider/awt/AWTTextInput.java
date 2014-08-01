/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextField;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.ActivateableAction;
import net.nexustools.gui.Shortcut;
import net.nexustools.gui.SingleInput;
import net.nexustools.gui.TextInput;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.event.ValueListener;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;

/**
 *
 * @author katelyn
 */
public class AWTTextInput extends AWTWidgetImpl<JTextField> implements TextInput {
    
    private class Native extends JTextField {
        public Native() {
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Input[Type=Text]");
        }
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    public AWTTextInput() {
        super(AWTPlatform.instance());
        setContextMenu(buildEditMenu(this));
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
    protected JTextField create() {
        return new Native();
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
                component.setText(value);
            }
        });
    }

    @Override
    public String value() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getText();
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
                component.cut();
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
                component.copy();
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
                component.paste();
            }
        };
    }

    public AbstractAction action() {
        return new ActivateableAction(this);
    }
    
}
