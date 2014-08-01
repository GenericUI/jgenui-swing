/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JSpinner;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.RangeInput;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingRangeInput extends AWTWidgetImpl<JSpinner> implements RangeInput {
    
    private class Native extends JSpinner {
        public Native() {
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Input[Type=Number]");
        }
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    public SwingRangeInput() {
        super(AWTPlatform.instance());
        setContextMenu(buildEditMenu(this));
    }

    @Override
    protected JSpinner create() {
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
    public void setValue(final Float value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setValue(value);
            }
        });
    }

    @Override
    public Float value() {
        return read(new Reader<Float>() {
            @Override
            public Float read() {
                return (Float)component.getValue();
            }
        });
    }

    @Override
    public float step() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStep(float step) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float min() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMin(float min) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float max() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMax(float max) {
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
                throw new UnsupportedOperationException("Not supported yet.");
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
                throw new UnsupportedOperationException("Not supported yet.");
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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    
}
